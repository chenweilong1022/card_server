package io.renren.modules.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import io.renren.common.utils.ConfigConstant;
import io.renren.modules.ltt.dto.CdCardLockDTO;
import io.renren.modules.ltt.entity.CdCardLockEntity;
import io.renren.modules.ltt.entity.CdUserEntity;
import io.renren.modules.ltt.enums.Lock;
import io.renren.modules.ltt.firefox.GetWaitPhoneList;
import io.renren.modules.ltt.firefox.GetWaitPhoneListDaum;
import io.renren.modules.ltt.service.CdCardLockService;
import io.renren.modules.ltt.service.CdUserService;
import io.renren.modules.sys.entity.ProjectWorkEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liuyuchan
 * @email liuyuchan286@gmail.com
 * @date 2023/11/21 18:45
 */
@Component
@Slf4j
public class BlackListTask {



    private GetWaitPhoneList prePhoneDeleteAllResponse;
    private GetWaitPhoneList currentPhoneDeleteAllResponse;

    @Autowired
    private CdCardLockService cdCardLockService;
    @Autowired
    private CdUserService cdUserService;
    @Resource(name = "caffeineCacheProjectWorkEntity")
    private Cache<String, ProjectWorkEntity> caffeineCacheProjectWorkEntity;

//    @Scheduled(fixedDelay = 5000)
    public void sayHello() {

        ProjectWorkEntity projectWorkEntity = caffeineCacheProjectWorkEntity.getIfPresent(ConfigConstant.PROJECT_WORK_KEY);

        Integer userId = projectWorkEntity.getUserId();

        try {
            String response = HttpUtil.get("https://www.firefox.fun/ksapi.ashx?key=76082377BDE44F99&act=GetWaitPhoneList");
            ObjectMapper objectMapper = new ObjectMapper();
            GetWaitPhoneList phoneDeleteAllResponse = objectMapper.readValue(response, GetWaitPhoneList.class);

            log.info("GetWaitPhoneList result code 1");
            prePhoneDeleteAllResponse = currentPhoneDeleteAllResponse;
            currentPhoneDeleteAllResponse = phoneDeleteAllResponse;
            if (ObjectUtil.isNull(currentPhoneDeleteAllResponse) || ObjectUtil.isNull(prePhoneDeleteAllResponse)) {
                log.info("currentPhoneDeleteAllResponse & prePhoneDeleteAllResponse is Null");
                return;
            }
            //
            List<GetWaitPhoneListDaum> currentList = currentPhoneDeleteAllResponse.getData();
            List<GetWaitPhoneListDaum> preList = prePhoneDeleteAllResponse.getData();
            if (CollUtil.isEmpty(preList)) {
                log.info("preList is Null");
                return;
            }

            Map<String, GetWaitPhoneListDaum> preMap = preList.stream().collect(Collectors.toMap(GetWaitPhoneListDaum::getPhoneNum, x -> x));
            Map<String, GetWaitPhoneListDaum> currentMap = currentList.stream().collect(Collectors.toMap(GetWaitPhoneListDaum::getPhoneNum, x -> x));
            //当前剩下的就是 被拉黑的 或者已使用的或者被释放的
            for (String s : preMap.keySet()) {
                GetWaitPhoneListDaum getWaitPhoneListDaum = currentMap.get(s);
                if (ObjectUtil.isNotNull(getWaitPhoneListDaum)) {
                    preMap.remove(s);
                }
            }

            if (CollUtil.isEmpty(preMap)) {
                log.info("preMap is Null");
                return;
            }

            List<CdCardLockEntity> list = cdCardLockService.list(new QueryWrapper<CdCardLockEntity>().lambda()
                    .eq(CdCardLockEntity::getLock, Lock.YES.getKey())
            );


            List<CdCardLockEntity> changeLock = new ArrayList<>();

            for (CdCardLockEntity cdCardLockEntity : list) {
                GetWaitPhoneListDaum getWaitPhoneListDaum = preMap.get(cdCardLockEntity.getPhone().replace("+855", ""));
                if (ObjectUtil.isNotNull(getWaitPhoneListDaum)) {
                    changeLock.add(cdCardLockEntity);
                }
            }

            CdUserEntity userEntity = cdUserService.getById((Serializable) userId);
            for (CdCardLockEntity cdCardLockEntity : changeLock) {
                log.info("拉黑 ===> {}",cdCardLockEntity.getDeviceId());
                CdCardLockDTO cdCardLock = new CdCardLockDTO();
                cdCardLock.setCode("拉黑");
                cdCardLock.setDeviceId(cdCardLockEntity.getDeviceId());
                boolean b = cdCardLockService.uploadSms(cdCardLock, userEntity);
            }
        }catch (Exception e) {
            log.error("error = {}",e.getMessage());
        }


    }

}
