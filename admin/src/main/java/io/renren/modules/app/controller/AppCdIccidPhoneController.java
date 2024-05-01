package io.renren.modules.app.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.Cache;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.ltt.dto.CdIccidPhoneDTO;
import io.renren.modules.ltt.entity.CdIccidPhoneEntity;
import io.renren.modules.ltt.entity.CdRechargedPhoneEntity;
import io.renren.modules.ltt.service.CdIccidPhoneService;
import io.renren.modules.ltt.service.CdRechargedPhoneService;
import io.renren.modules.ltt.vo.CdIccidPhoneVO;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Struct;
import java.util.*;
import java.util.stream.Collectors;


/**
 * iccid手机对接表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-30 00:10:56
 */
@RestController
@RequestMapping("app/cdiccidphone")
public class AppCdIccidPhoneController {
    @Autowired
    private CdIccidPhoneService cdIccidPhoneService;

    @Resource(name = "mapCache")
    private Cache<String, Map<String,String>> mapCache;
    @Resource(name = "mapDateCache")
    private Cache<String, Map<String,Date>> mapDateCache;

    /**
     * 根据iccid获取
     */
    @RequestMapping("/getPhoneByIccid")
    public R getPhoneByIccid(CdIccidPhoneDTO cdIccidPhone){
        Map<String, String> mapCache1 = mapCache.getIfPresent("mapCache");
        if (CollUtil.isEmpty(mapCache1)) {
            List<CdIccidPhoneEntity> list = cdIccidPhoneService.list();
            mapCache1 = list.stream().collect(Collectors.toMap(CdIccidPhoneEntity::getIccid, CdIccidPhoneEntity::getPhone));
            mapCache.put("mapCache",mapCache1);
        }
        String phone = mapCache1.get(cdIccidPhone.getIccid());
        if (StrUtil.isEmpty(phone)) {
            return R.error();
        }
        return R.data(phone);
    }

    /**
     * 是否获取过期时间
     */
    @RequestMapping("/getExpireTime")
    public R getExpireTime(CdIccidPhoneDTO cdIccidPhone){
        Map<String, Date> mapCache1 = mapDateCache.getIfPresent("mapDateCache");
        if (CollUtil.isEmpty(mapCache1)) {
            List<CdIccidPhoneEntity> list = cdIccidPhoneService.list();
            mapCache1 = list.stream().collect(Collectors.toMap(CdIccidPhoneEntity::getIccid, CdIccidPhoneEntity::getExpireTime));
            mapDateCache.put("mapDateCache",mapCache1);
        }
        Date date = mapCache1.get(cdIccidPhone.getIccid());
        if (ObjectUtil.isNull(date)) {
            return R.error();
        }
        //当前卡一个月后是否过期
        DateTime dateTime = DateUtil.offsetDay(DateUtil.date(), 30);
        //如果为true
        boolean after = dateTime.after(date);
        return R.data(after);
    }

}
