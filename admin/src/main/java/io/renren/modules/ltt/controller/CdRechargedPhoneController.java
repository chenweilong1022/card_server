package io.renren.modules.ltt.controller;

import java.util.Arrays;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.ltt.dto.CdRechargedPhoneDTO;
import io.renren.modules.ltt.vo.CdRechargedPhoneVO;
import io.renren.modules.ltt.service.CdRechargedPhoneService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 已充值手机
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2024-01-22 21:38:09
 */
@RestController
@RequestMapping("ltt/cdrechargedphone")
public class CdRechargedPhoneController {
    @Autowired
    private CdRechargedPhoneService cdRechargedPhoneService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ltt:cdrechargedphone:list")
    public R list(CdRechargedPhoneDTO cdRechargedPhone){
        PageUtils page = cdRechargedPhoneService.queryPage(cdRechargedPhone);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ltt:cdrechargedphone:info")
    public R info(@PathVariable("id") Integer id){
		CdRechargedPhoneVO cdRechargedPhone = cdRechargedPhoneService.getById(id);

        return R.ok().put("cdRechargedPhone", cdRechargedPhone);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ltt:cdrechargedphone:save")
    public R save(@RequestBody CdRechargedPhoneDTO cdRechargedPhone){
		cdRechargedPhoneService.save(cdRechargedPhone);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ltt:cdrechargedphone:update")
    public R update(@RequestBody CdRechargedPhoneDTO cdRechargedPhone){
		cdRechargedPhoneService.updateById(cdRechargedPhone);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ltt:cdrechargedphone:delete")
    public R delete(@RequestBody Integer[] ids){
		cdRechargedPhoneService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
