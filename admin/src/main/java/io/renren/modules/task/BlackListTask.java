package io.renren.modules.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
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
import io.renren.modules.ltt.service.CdProjectService;
import io.renren.modules.ltt.service.CdUserService;
import io.renren.modules.ltt.vo.CdProjectVO;
import io.renren.modules.sys.entity.ProjectWorkEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    @Scheduled(fixedDelay = 5000)
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
                if (ObjectUtil.isNotNull(getWaitPhoneListDaum)) {
                    String phoneGetTime = getWaitPhoneListDaum.getPhoneGetTime();
                    Instant instant = Instant.parse(phoneGetTime + "Z");
                    ZonedDateTime zonedDateTime = instant.atZone(ZoneOffset.UTC);
                    Date date = Date.from(zonedDateTime.toInstant());
                    cdCardLockEntity.setPhoneGetTime(date);
                    cdCardLockEntity.setCreateTime(DateUtil.date());
                    cdCardLockService.updateById(cdCardLockEntity);
                }
            }
        }catch (Exception e) {
            log.error("error = {}",e.getMessage());
        }
    }

}
