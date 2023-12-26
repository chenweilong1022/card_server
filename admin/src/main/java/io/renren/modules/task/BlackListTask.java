package io.renren.modules.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import io.renren.common.utils.ConfigConstant;
import io.renren.modules.ltt.conver.CdCardLockConver;
import io.renren.modules.ltt.dto.CdCardLockDTO;
import io.renren.modules.ltt.entity.CdCardLockEntity;
import io.renren.modules.ltt.entity.CdProjectEntity;
import io.renren.modules.ltt.entity.CdUserEntity;
import io.renren.modules.ltt.enums.CodeAcquisitionType;
import io.renren.modules.ltt.enums.Lock;
import io.renren.modules.ltt.firefox.GetWaitPhoneList;
import io.renren.modules.ltt.firefox.GetWaitPhoneListDaum;
import io.renren.modules.ltt.service.CdCardLockService;
import io.renren.modules.ltt.service.CdDevicesService;
import io.renren.modules.ltt.service.CdProjectService;
import io.renren.modules.ltt.service.CdUserService;
import io.renren.modules.ltt.vo.CdCardLockVO;
import io.renren.modules.ltt.vo.CdProjectVO;
import io.renren.modules.ltt.vo.GetListByIdsVO;
import io.renren.modules.sys.entity.ProjectWorkEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @Scheduled(fixedDelay = 5000)
//    @Transactional(rollbackFor = Exception.class)
    public void withBlack() {
        boolean b = task10Lock.tryLock();
        if (!b) {
            return;
        }

        try {

            List<GetListByIdsVO> getListByIdsVOS = cdCardLockService.getListByIds(null);
            Map<Integer, List<GetListByIdsVO>> integerListMap = getListByIdsVOS.stream().collect(Collectors.groupingBy(GetListByIdsVO::getGroupId));

            for (Integer id : integerListMap.keySet()) {

                List<GetListByIdsVO> list = integerListMap.get(id);
                String cacheKey = String.format("%s_%s", ConfigConstant.PROJECT_WORK_KEY, id);
                ProjectWorkEntity projectWorkEntity = caffeineCacheProjectWorkEntity.getIfPresent(cacheKey);
                if (ObjectUtil.isNull(projectWorkEntity)) {
                    return;
                }

                CdUserEntity cdUserEntity = cdUserService.getById((Serializable) projectWorkEntity.getUserId());
                // 挂机模式
                if (CodeAcquisitionType.CodeAcquisitionType2.getKey().equals(projectWorkEntity.getCodeAcquisitionType())) {

                    for (GetListByIdsVO cdCardLockEntity : list) {
                        if (ObjectUtil.isNull(cdCardLockEntity.getPhoneGetTime())) {
                            CdCardLockDTO cdCardLockDTO = CdCardLockConver.MAPPER.conver2(cdCardLockEntity);
                            //获取手机号码
                            CdCardLockVO cdCardLockVO = cdCardLockService.getMobile2(cdCardLockDTO, cdUserEntity, cdCardLockDTO.getDeviceId());
                            continue;
                        }
                        DateTime dateTime = DateUtil.offsetMinute(cdCardLockEntity.getPhoneGetTime(), 0);
                        DateTime now = DateUtil.date();
                        if (now.toJdkDate().getTime()> dateTime.toJdkDate().getTime()) {
                            CdCardLockDTO cdCardLockDTO = CdCardLockConver.MAPPER.conver2(cdCardLockEntity);
                            //获取手机号码
                            CdCardLockVO cdCardLockVO = cdCardLockService.getMobile2(cdCardLockDTO, cdUserEntity, cdCardLockDTO.getDeviceId());
                        }
                    }
                    return;
                }
                // 获取ids
                List<Integer> ids = new ArrayList<>();
                for (GetListByIdsVO cdCardLockEntity : list) {
                    if (ObjectUtil.isNull(cdCardLockEntity.getPhoneGetTime())) {
                        continue;
                    }
                    DateTime dateTime = DateUtil.offsetMinute(cdCardLockEntity.getPhoneGetTime(), 4);
                    DateTime now = DateUtil.date();
                    if (now.toJdkDate().getTime()> dateTime.toJdkDate().getTime()) {
                        ids.add(cdCardLockEntity.getId());
                    }
                }
                //自动拉黑
                cdDevicesService.withBlack(ArrayUtil.toArray(ids,Integer.class));

            }
        }finally {
            task10Lock.unlock();
        }
    }


    public static void main(String[] args) {
        String response = HttpUtil.get("https://www.firefox.fun/ksapi.ashx?key=76082377BDE44F99" + "&act=GetWaitPhoneList");
        System.out.println(response);
    }

    static ReentrantLock task11Lock = new ReentrantLock();

//    @Scheduled(fixedDelay = 5000)
//    @Transactional(rollbackFor = Exception.class)
    public void sayHello() {
        boolean task11LockFlag = task11Lock.tryLock();
        if (!task11LockFlag) {
            return;
        }
        try{
            List<GetListByIdsVO> getListByIdsVOS = cdCardLockService.getListByIds(null);
            Map<Integer, List<GetListByIdsVO>> integerListMap = getListByIdsVOS.stream().collect(Collectors.groupingBy(GetListByIdsVO::getGroupId));
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
                        return;
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
        }finally {
            task11Lock.unlock();
        }

    }

}
