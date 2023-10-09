package io.renren.modules.ltt.controller;

import java.util.Arrays;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.ltt.dto.CdDevicesDTO;
import io.renren.modules.ltt.vo.CdDevicesVO;
import io.renren.modules.ltt.service.CdDevicesService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 设备表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-05 23:22:42
 */
@RestController
@RequestMapping("ltt/cddevices")
public class CdDevicesController {
    @Autowired
    private CdDevicesService cdDevicesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ltt:cddevices:list")
    public R list(CdDevicesDTO cdDevices){
        PageUtils page = cdDevicesService.queryPage(cdDevices);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ltt:cddevices:info")
    public R info(@PathVariable("id") Integer id){
		CdDevicesVO cdDevices = cdDevicesService.getById(id);

        return R.ok().put("cdDevices", cdDevices);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ltt:cddevices:save")
    public R save(@RequestBody CdDevicesDTO cdDevices){
		cdDevicesService.save(cdDevices);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ltt:cddevices:update")
    public R update(@RequestBody CdDevicesDTO cdDevices){
		cdDevicesService.updateById(cdDevices);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ltt:cddevices:delete")
    public R delete(@RequestBody Integer[] ids){
		cdDevicesService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
