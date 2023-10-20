package io.renren.modules.ltt.controller;

import java.util.Arrays;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.ltt.dto.CdUserDTO;
import io.renren.modules.ltt.vo.CdUserVO;
import io.renren.modules.ltt.service.CdUserService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 卡池用户表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-18 23:26:34
 */
@RestController
@RequestMapping("ltt/cduser")
public class CdUserController {
    @Autowired
    private CdUserService cdUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ltt:cduser:list")
    public R list(CdUserDTO cdUser){
        PageUtils page = cdUserService.queryPage(cdUser);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ltt:cduser:info")
    public R info(@PathVariable("id") Integer id){
		CdUserVO cdUser = cdUserService.getById(id);

        return R.ok().put("cdUser", cdUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ltt:cduser:save")
    public R save(@RequestBody CdUserDTO cdUser){
		cdUserService.save(cdUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ltt:cduser:update")
    public R update(@RequestBody CdUserDTO cdUser){
		cdUserService.updateById(cdUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ltt:cduser:delete")
    public R delete(@RequestBody Integer[] ids){
		cdUserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
