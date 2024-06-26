package io.renren.modules.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.google.common.collect.Lists;
import io.renren.common.utils.ConfigConstant;
import io.renren.modules.ltt.conver.CdCardLockConver;
import io.renren.modules.ltt.dto.CdCardLockDTO;
import io.renren.modules.ltt.entity.CdCardLockEntity;
import io.renren.modules.ltt.entity.CdDevicesEntity;
import io.renren.modules.ltt.entity.CdProjectEntity;
import io.renren.modules.ltt.entity.CdUserEntity;
import io.renren.modules.ltt.enums.CodeAcquisitionType;
import io.renren.modules.ltt.enums.Lock;
import io.renren.modules.ltt.enums.WorkType;
import io.renren.modules.ltt.firefox.GetWaitPhoneList;
import io.renren.modules.ltt.firefox.GetWaitPhoneListDaum;
import io.renren.modules.ltt.firefox.PhoneList;
import io.renren.modules.ltt.firefox.Root;
import io.renren.modules.ltt.service.CdCardLockService;
import io.renren.modules.ltt.service.CdDevicesService;
import io.renren.modules.ltt.service.CdProjectService;
import io.renren.modules.ltt.service.CdUserService;
import io.renren.modules.ltt.vo.CdCardLockVO;
import io.renren.modules.ltt.vo.CdProjectVO;
import io.renren.modules.ltt.vo.GetListByIdsVO;
import io.renren.modules.ltt.vo.UpdateAppVO;
import io.renren.modules.netty.codec.Invocation;
import io.renren.modules.netty.message.updateapp.UpdateappResponse;
import io.renren.modules.netty.server.NettyChannelManager;
import io.renren.modules.sys.entity.ProjectWorkEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author liuyuchan
 * @email liuyuchan286@gmail.com
 * @date 2023/11/21 18:45
 */
@Component
@Slf4j
@EnableAsync
@Profile({"prod"})
public class BlackListTask {

    @Autowired
    private CdCardLockService cdCardLockService;
    @Autowired
    private CdUserService cdUserService;
    @Resource(name = "caffeineCacheProjectWorkEntity")
    private Cache<String, ProjectWorkEntity> caffeineCacheProjectWorkEntity;

    @Autowired
    private CdProjectService cdProjectService;
    @Autowired
    private CdDevicesService cdDevicesService;

    static ReentrantLock task10Lock = new ReentrantLock();
    static ReentrantLock taskLockupdateApp = new ReentrantLock();


    @Resource(name = "stringListCacheUpdateAppVO")
    private Cache<String, Queue<UpdateAppVO>> stringListCacheUpdateAppVO;
    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Scheduled(fixedDelay = 5000)
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void updateApp() {
        boolean b = taskLockupdateApp.tryLock();
        if (!b) {
            return;
        }
        try {
            //是否有需要更新的项目
            Queue<UpdateAppVO> updateAppVO = stringListCacheUpdateAppVO.getIfPresent("stringListCacheUpdateAppVO");
            if (CollUtil.isEmpty(updateAppVO)) {
                log.info("updateAppVO isempty");
                return;
            }
            log.info("updateAppVO = {}",JSONUtil.toJsonStr(updateAppVO));
            UpdateAppVO peek = updateAppVO.peek();
            Integer id = peek.getId();
            Integer count = peek.getCount();
            String currentVersion = peek.getCurrentVersion();
            CdDevicesEntity devicesEntity = cdDevicesService.getById((Serializable) id);

            if (ObjectUtil.isNull(devicesEntity)) {
                return;
            }

            if (!currentVersion.equals(devicesEntity.getPackageVersion())) {
                updateAppVO.poll();
                stringListCacheUpdateAppVO.put("stringListCacheUpdateAppVO",updateAppVO);
                return;
            }

            log.info("device = {} update = {}",devicesEntity.getIccid(),"updateVersion");

            //更新app
            if (0 == count || (count % 5 == 0)) {
                UpdateappResponse taskDto = new UpdateappResponse();
                taskDto.setDeviceId(peek.getDeviceId());
                taskDto.setHttpUrl(peek.getHttpUrl());
                nettyChannelManager.send(peek.getDeviceId(),new Invocation(UpdateappResponse.TYPE, taskDto));
            }
            if (count > 3) {
                updateAppVO.poll();
                stringListCacheUpdateAppVO.put("stringListCacheUpdateAppVO",updateAppVO);
                return;
            }
            count++;
            peek.setCount(count);
            stringListCacheUpdateAppVO.put("stringListCacheUpdateAppVO",updateAppVO);
        }catch (Exception e) {
            log.error("e = {}",e.getLocalizedMessage());
            log.error("e = {}",e.getMessage());
            e.printStackTrace();
        }finally {
            taskLockupdateApp.unlock();
        }
    }


    @Scheduled(fixedDelay = 5000)
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void withBlack() {
        boolean b = task10Lock.tryLock();
        if (!b) {
            return;
        }
        try {

            List<GetListByIdsVO> getListByIdsVOS = cdCardLockService.getListByIds(null);

            Map<Integer, List<GetListByIdsVO>> integerListMap = getListByIdsVOS.stream().filter(x -> ObjectUtil.isNotNull(x.getGroupId())).collect(Collectors.groupingBy(GetListByIdsVO::getGroupId));

            for (Integer id : integerListMap.keySet()) {

                List<GetListByIdsVO> list = integerListMap.get(id);
                String cacheKey = String.format("%s_%s", ConfigConstant.PROJECT_WORK_KEY, id);
                ProjectWorkEntity projectWorkEntity = caffeineCacheProjectWorkEntity.getIfPresent(cacheKey);
                if (ObjectUtil.isNull(projectWorkEntity)) {
                    continue;
                }
                CdUserEntity cdUserEntity = cdUserService.getById((Serializable) projectWorkEntity.getUserId());
                // 挂机模式
                if (CodeAcquisitionType.CodeAcquisitionType2.getKey().equals(projectWorkEntity.getCodeAcquisitionType())) {
                    List<PhoneList> phoneLists = new ArrayList<PhoneList>();
                    List<PhoneList> phoneDelLists = new ArrayList<PhoneList>();
                    for (GetListByIdsVO cdCardLockEntity : list) {
                        //获取之前的手机号
                        String phone = cdCardLockEntity.getPhone();
                        if (ObjectUtil.isNull(cdCardLockEntity.getPhoneGetTime())) {
                            CdCardLockDTO cdCardLockDTO = CdCardLockConver.MAPPER.conver2(cdCardLockEntity);
                            //获取手机号码
                            CdCardLockVO mobile = cdCardLockService.getMobile2(cdCardLockDTO, cdUserEntity, cdCardLockDTO.getDeviceId(),projectWorkEntity);
                            if (ObjectUtil.isNotNull(mobile)) {
                                String replace = mobile.getPhone().replaceFirst(projectWorkEntity.getPhonePre(), "");
                                PhoneList phoneList = new PhoneList("tha",replace);
                                phoneLists.add(phoneList);
                            }
                            continue;
                        }
                        DateTime dateTime = DateUtil.offsetMinute(cdCardLockEntity.getPhoneGetTime(), 0);
                        DateTime now = DateUtil.date();
                        //如果超时了去删除之前的卡
                        if (now.toJdkDate().getTime()> dateTime.toJdkDate().getTime()) {
                            if (StrUtil.isNotEmpty(phone)) {
                                String replaceDel = phone.replaceFirst(projectWorkEntity.getPhonePre(), "");
                                PhoneList phoneDelList = new PhoneList("tha",replaceDel);
                                phoneDelLists.add(phoneDelList);

                                CdCardLockDTO cdCardLockDTO = CdCardLockConver.MAPPER.conver2(cdCardLockEntity);
                                //获取手机号码
                                CdCardLockVO mobile = cdCardLockService.getMobile2(cdCardLockDTO, cdUserEntity, cdCardLockDTO.getDeviceId(),projectWorkEntity);
                                if (ObjectUtil.isNotNull(mobile)) {
                                    String replace = mobile.getPhone().replaceFirst(projectWorkEntity.getPhonePre(), "");
                                    PhoneList phoneList = new PhoneList("tha",replace);
                                    phoneLists.add(phoneList);
                                }
                            }
                        }
                    }

                    if (CollUtil.isNotEmpty(phoneDelLists)) {
                        List<List<PhoneList>> partition = Lists.partition(phoneDelLists, 99);
                        for (List<PhoneList> lists : partition) {
                            cdCardLockService.extracted(lists,"PhoneDeleteBatch",projectWorkEntity.getCodeApiUrl());
                        }
                    }
                    if (CollUtil.isNotEmpty(phoneLists)) {
                        List<List<PhoneList>> partition = Lists.partition(phoneLists, 49);
                        for (List<PhoneList> lists : partition) {
                            cdCardLockService.extracted(lists,"",projectWorkEntity.getCodeApiUrl());
                        }
                    }
                    continue;
                }else if(CodeAcquisitionType.CodeAcquisitionType3.getKey().equals(projectWorkEntity.getCodeAcquisitionType())) {
                    continue;
                }
                // 获取ids
                List<Integer> ids = new ArrayList<>();
                for (GetListByIdsVO cdCardLockEntity : list) {
                    if (ObjectUtil.isNull(cdCardLockEntity.getPhoneGetTime())) {
                        DateTime dateTime = DateUtil.offsetMinute(cdCardLockEntity.getCreateTime(), 15);
                        DateTime now = DateUtil.date();
                        if (now.toJdkDate().getTime()> dateTime.toJdkDate().getTime()) {
                            ids.add(cdCardLockEntity.getId());
                        }
                        continue;
                    }
                    DateTime dateTime = DateUtil.offsetMinute(cdCardLockEntity.getPhoneGetTime(), 3);
                    DateTime now = DateUtil.date();
                    if (now.toJdkDate().getTime()> dateTime.toJdkDate().getTime()) {
                        ids.add(cdCardLockEntity.getId());
                    }
                }
                //自动拉黑
                cdDevicesService.withBlack(ArrayUtil.toArray(ids,Integer.class));

            }
        }catch (Exception e) {
            log.error("e = {}",e.getLocalizedMessage());
            log.error("e = {}",e.getMessage());
            e.printStackTrace();
        }finally {
            task10Lock.unlock();
        }
    }

    static ReentrantLock task11Lock = new ReentrantLock();

    @Scheduled(fixedDelay = 5000)
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void sayHello() {
        boolean task11LockFlag = task11Lock.tryLock();
        if (!task11LockFlag) {
            return;
        }
        try{
            List<GetListByIdsVO> getListByIdsVOS = cdCardLockService.getListByIds(null);
            Map<Integer, List<GetListByIdsVO>> integerListMap = getListByIdsVOS.stream().filter(x -> ObjectUtil.isNotNull(x.getGroupId())).collect(Collectors.groupingBy(GetListByIdsVO::getGroupId));
            for (Integer id : integerListMap.keySet()) {
                try {
                    String cacheKey = String.format("%s_%s", ConfigConstant.PROJECT_WORK_KEY, id);
                    ProjectWorkEntity projectWorkEntity = caffeineCacheProjectWorkEntity.getIfPresent(cacheKey);
                    if (ObjectUtil.isNull(projectWorkEntity)) {
                        //如果不存在跳出这个循环
                        continue;
                    }
                    String response = HttpUtil.get(projectWorkEntity.getCodeApiUrl() + "&act=GetWaitPhoneList");
                    log.info("response = {}",response);
                    ObjectMapper objectMapper = new ObjectMapper();
                    GetWaitPhoneList phoneDeleteAllResponse = objectMapper.readValue(response, GetWaitPhoneList.class);

                    List<GetWaitPhoneListDaum> data = phoneDeleteAllResponse.getData();
                    if (CollUtil.isEmpty(data)) {
                        continue;
                    }
                    //获取所有的手机
                    Map<String, GetWaitPhoneListDaum> stringGetWaitPhoneListDaumMap = data.stream().collect(Collectors.toMap(x -> projectWorkEntity.getPhonePre() + x.getPhoneNum() + "=" + x.getItemId(), y -> y));
                    log.info("stringGetWaitPhoneListDaumMap = {}", JSONUtil.toJsonStr(stringGetWaitPhoneListDaumMap));

                    //获取所有的手机号
                    List<CdCardLockEntity> list = cdCardLockService.list(new QueryWrapper<CdCardLockEntity>().lambda()
                            .in(CdCardLockEntity::getPhone,stringGetWaitPhoneListDaumMap.keySet().stream().map(x -> x.split("=")[0]).collect(Collectors.toList()))
                    );
                    for (CdCardLockEntity cdCardLockEntity : list) {
                        Integer projectId = cdCardLockEntity.getProjectId();
                        CdProjectVO cdProjectVO = cdProjectService.getById(projectId);
                        String key = cdCardLockEntity.getPhone() + "=" + cdProjectVO.getItemId();
                        log.info("key = {}", key);
                        GetWaitPhoneListDaum getWaitPhoneListDaum = stringGetWaitPhoneListDaumMap.get(key);
                        if (ObjectUtil.isNotNull(getWaitPhoneListDaum)) {
                            log.info("getWaitPhoneListDaum = {}", JSONUtil.toJsonStr(getWaitPhoneListDaum));
                        }
                        if (ObjectUtil.isNotNull(getWaitPhoneListDaum) && ObjectUtil.isNull(cdCardLockEntity.getPhoneGetTime())) {
                            String phoneGetTime = getWaitPhoneListDaum.getPhoneGetTime();
                            DateTime parse = DateUtil.parse(phoneGetTime.replace("T", " "));
                            cdCardLockEntity.setPhoneGetTime(parse);
                            cdCardLockEntity.setCreateTime(DateUtil.date());
                            boolean b = cdCardLockService.updateById(cdCardLockEntity);
                            log.info("GetWaitPhoneList = {} flag = {}",response,b);
                        }
                    }
                }catch (Exception e) {
                    log.error("error = {}",e.getLocalizedMessage());
                }
            }
        }catch (Exception e) {
            log.error("e = {}",e.getLocalizedMessage());
            log.error("e = {}",e.getMessage());
            e.printStackTrace();
        }finally {
            task11Lock.unlock();
        }

    }

}
