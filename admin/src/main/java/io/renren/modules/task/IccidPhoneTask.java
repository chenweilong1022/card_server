package io.renren.modules.task;

import com.github.benmanes.caffeine.cache.Cache;
import io.renren.modules.ltt.entity.CdIccidPhoneEntity;
import io.renren.modules.ltt.service.CdIccidPhoneService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liuyuchan
 * @email liuyuchan286@gmail.com
 * @date 2024/5/2 15:13
 */
@Component
@Slf4j
@EnableAsync
@Profile({"prod"})
public class IccidPhoneTask {

    @Resource(name = "mapCache")
    private Cache<String, Map<String,String>> mapCache;
    @Resource(name = "mapDateCache")
    private Cache<String, Map<String, Date>> mapDateCache;
    @Resource(name = "booleanCache")
    private Cache<String, Boolean> booleanCache;
    @Autowired
    private CdIccidPhoneService cdIccidPhoneService;

    @Scheduled(fixedDelay = 5000)
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void task1() {
        Boolean run = booleanCache.getIfPresent("run");
        if (Boolean.TRUE.equals(run)) {
            List<CdIccidPhoneEntity> list = cdIccidPhoneService.list();
            Map<String, String> stringStringMap = list.stream().collect(Collectors.toMap(CdIccidPhoneEntity::getIccid, CdIccidPhoneEntity::getPhone,(a,b) -> a));
            mapCache.put("mapCache",stringStringMap);

            Map<String, Date> collect = list.stream().collect(Collectors.toMap(CdIccidPhoneEntity::getIccid, CdIccidPhoneEntity::getExpireTime,(a,b) -> a));
            mapDateCache.put("mapDateCache",collect);

            booleanCache.put("run",Boolean.FALSE);
        }
    }

}
