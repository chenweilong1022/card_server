package io.renren.modules.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import io.renren.common.utils.ConfigConstant;
import io.renren.modules.ltt.dto.CdCardLockDTO;
import io.renren.modules.ltt.entity.CdCardLockEntity;
import io.renren.modules.ltt.entity.CdProjectEntity;
import io.renren.modules.ltt.entity.CdUserEntity;
import io.renren.modules.ltt.enums.Lock;
import io.renren.modules.ltt.firefox.GetWaitPhoneList;
import io.renren.modules.ltt.firefox.GetWaitPhoneListDaum;
import io.renren.modules.ltt.service.CdCardLockService;
import io.renren.modules.ltt.service.CdDevicesService;
import io.renren.modules.ltt.service.CdProjectService;
import io.renren.modules.ltt.service.CdUserService;
import io.renren.modules.ltt.vo.CdProjectVO;
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
    @Scheduled(fixedDelay = 60000)
    @Transactional(rollbackFor = Exception.class)
    public void withBlack() {
        List<Integer> ids = new ArrayList<>();
        List<CdCardLockEntity> list = cdCardLockService.list();
        for (CdCardLockEntity cdCardLockEntity : list) {
            if (ObjectUtil.isNull(cdCardLockEntity.getPhoneGetTime())) {
                continue;
            }
            DateTime dateTime = DateUtil.offsetMinute(cdCardLockEntity.getPhoneGetTime(), 5);
            DateTime now = DateUtil.date();
            if (now.toJdkDate().getTime()> dateTime.toJdkDate().getTime()) {
                ids.add(cdCardLockEntity.getId());
            }
        }
        //自动拉黑
        cdDevicesService.withBlack(ArrayUtil.toArray(ids,Integer.class));
        //自动切号
        cdDevicesService.initCard3(ArrayUtil.toArray(ids,Integer.class));
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional(rollbackFor = Exception.class)
    public void sayHello() {
        ProjectWorkEntity projectWorkEntity = caffeineCacheProjectWorkEntity.getIfPresent(ConfigConstant.PROJECT_WORK_KEY);
        try {
            String response = HttpUtil.get("https://www.firefox.fun/ksapi.ashx?key=76082377BDE44F99&act=GetWaitPhoneList");
            ObjectMapper objectMapper = new ObjectMapper();
            GetWaitPhoneList phoneDeleteAllResponse = objectMapper.readValue(response, GetWaitPhoneList.class);

            List<GetWaitPhoneListDaum> data = phoneDeleteAllResponse.getData();
            if (CollUtil.isEmpty(data)) {
                return;
            }
            //获取所有的手机
            Map<String, GetWaitPhoneListDaum> stringGetWaitPhoneListDaumMap = data.stream().collect(Collectors.toMap(x -> projectWorkEntity.getPhonePre() + x.getPhoneNum() + "=" + x.getItemId(), y -> y));
            //获取所有的手机号
            List<CdCardLockEntity> list = cdCardLockService.list(new QueryWrapper<CdCardLockEntity>().lambda()
                    .in(CdCardLockEntity::getPhone,stringGetWaitPhoneListDaumMap.keySet().stream().map(x -> x.split("=")[0]).collect(Collectors.toList()))
            );
            for (CdCardLockEntity cdCardLockEntity : list) {
                Integer projectId = cdCardLockEntity.getProjectId();
                CdProjectVO cdProjectVO = cdProjectService.getById(projectId);
                GetWaitPhoneListDaum getWaitPhoneListDaum = stringGetWaitPhoneListDaumMap.get(cdCardLockEntity.getPhone() + "=" + cdProjectVO.getItemId());
                if (ObjectUtil.isNotNull(getWaitPhoneListDaum) || ObjectUtil.isNull(cdCardLockEntity.getPhoneGetTime())) {
                    String phoneGetTime = getWaitPhoneListDaum.getPhoneGetTime();
                    DateTime parse = DateUtil.parse(phoneGetTime.replace("T", " "));
                    cdCardLockEntity.setPhoneGetTime(parse);
                    cdCardLockEntity.setCreateTime(DateUtil.date());
                    cdCardLockService.updateById(cdCardLockEntity);
                }
            }
        }catch (Exception e) {
            log.error("error = {}",e.getMessage());
        }
    }

}
