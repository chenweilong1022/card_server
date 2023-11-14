package io.renren.modules.app.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.ltt.dto.CdDevicesNumberDTO;
import io.renren.modules.ltt.entity.CdDevicesNumberEntity;
import io.renren.modules.ltt.service.CdDevicesNumberService;
import io.renren.modules.ltt.vo.CdDevicesNumberVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 设备编号对应表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-11-14 21:40:08
 */
@RestController
@RequestMapping("app/cddevicesnumber")
public class AppCdDevicesNumberController {
    @Autowired
    private CdDevicesNumberService cdDevicesNumberService;

    /**
     * 信息
     */
    @RequestMapping("/info")
    public R info(@RequestBody CdDevicesNumberDTO cdDevicesNumber){
        CdDevicesNumberEntity one = cdDevicesNumberService.getOne(new QueryWrapper<CdDevicesNumberEntity>().lambda()
                .eq(CdDevicesNumberEntity::getDeviceId,cdDevicesNumber.getDeviceId())
        );
        return R.data(one);
    }

    @RequestMapping("/save")
    public R save(@RequestBody CdDevicesNumberDTO cdDevicesNumber){
        //根据设备查询编号
        CdDevicesNumberEntity one = cdDevicesNumberService.getOne(new QueryWrapper<CdDevicesNumberEntity>().lambda()
                .eq(CdDevicesNumberEntity::getDeviceId,cdDevicesNumber.getDeviceId())
        );
        CdDevicesNumberEntity update = new CdDevicesNumberEntity();
        update.setDeviceId(cdDevicesNumber.getDeviceId());
        update.setNumber(cdDevicesNumber.getNumber());
        if (ObjectUtil.isNotNull(one)) {
            update.setId(one.getId());
            cdDevicesNumberService.updateById(update);
        }else {
            cdDevicesNumberService.save(update);
        }
        return R.data(update);
    }

}
