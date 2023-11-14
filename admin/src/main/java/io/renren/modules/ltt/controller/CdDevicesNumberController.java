package io.renren.modules.ltt.controller;

import java.util.Arrays;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.ltt.dto.CdDevicesNumberDTO;
import io.renren.modules.ltt.vo.CdDevicesNumberVO;
import io.renren.modules.ltt.service.CdDevicesNumberService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 设备编号对应表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-11-14 21:40:08
 */
@RestController
@RequestMapping("ltt/cddevicesnumber")
public class CdDevicesNumberController {
    @Autowired
    private CdDevicesNumberService cdDevicesNumberService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ltt:cddevicesnumber:list")
    public R list(CdDevicesNumberDTO cdDevicesNumber){
        PageUtils page = cdDevicesNumberService.queryPage(cdDevicesNumber);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ltt:cddevicesnumber:info")
    public R info(@PathVariable("id") Integer id){
		CdDevicesNumberVO cdDevicesNumber = cdDevicesNumberService.getById(id);

        return R.ok().put("cdDevicesNumber", cdDevicesNumber);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ltt:cddevicesnumber:save")
    public R save(@RequestBody CdDevicesNumberDTO cdDevicesNumber){
		cdDevicesNumberService.save(cdDevicesNumber);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ltt:cddevicesnumber:update")
    public R update(@RequestBody CdDevicesNumberDTO cdDevicesNumber){
		cdDevicesNumberService.updateById(cdDevicesNumber);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ltt:cddevicesnumber:delete")
    public R delete(@RequestBody Integer[] ids){
		cdDevicesNumberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
