package io.renren.modules.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import io.renren.common.utils.DateThParse;
import io.renren.modules.ltt.entity.CdIccidPhoneEntity;
import io.renren.modules.ltt.service.CdIccidPhoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liuyuchan
 * @email liuyuchan286@gmail.com
 * @date 2023/11/21 18:45
 */
@Component
@Slf4j
@EnableAsync
@Profile({"prod"})
public class ExpireTimeTask {

    @Autowired
    private CdIccidPhoneService cdIccidPhoneService;



    @Scheduled(fixedDelay = 5000)
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void expireTime() {

        List<CdIccidPhoneEntity> list = cdIccidPhoneService.list(new QueryWrapper<CdIccidPhoneEntity>().lambda()
                .isNull(CdIccidPhoneEntity::getExpireTime)
                .last("AND ussd_msg != '' limit 100")
        );

        if (CollUtil.isEmpty(list)) {
            return;
        }

        for (CdIccidPhoneEntity cdIccidPhoneEntity : list) {
            if (StrUtil.isNotEmpty(cdIccidPhoneEntity.getUssdMsg())) {
                DateTime parse = DateThParse.parse(cdIccidPhoneEntity.getUssdMsg());
                cdIccidPhoneEntity.setExpireTime(parse);
                if (ObjectUtil.isNull(parse)) {
                    cdIccidPhoneEntity.setUssdMsg("");
                }
            }
        }
        cdIccidPhoneService.updateBatchById(list);
    }

}
