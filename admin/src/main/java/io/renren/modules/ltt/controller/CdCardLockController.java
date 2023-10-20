package io.renren.modules.ltt.controller;

import java.util.Arrays;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.ltt.dto.CdCardLockDTO;
import io.renren.modules.ltt.vo.CdCardLockVO;
import io.renren.modules.ltt.service.CdCardLockService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 卡锁
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-19 02:22:45
 */
@RestController
@RequestMapping("ltt/cdcardlock")
public class CdCardLockController {
    @Autowired
    private CdCardLockService cdCardLockService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ltt:cdcardlock:list")
    public R list(CdCardLockDTO cdCardLock){
        PageUtils page = cdCardLockService.queryPage(cdCardLock);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ltt:cdcardlock:info")
    public R info(@PathVariable("id") Integer id){
		CdCardLockVO cdCardLock = cdCardLockService.getById(id);

        return R.ok().put("cdCardLock", cdCardLock);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ltt:cdcardlock:save")
    public R save(@RequestBody CdCardLockDTO cdCardLock){
		cdCardLockService.save(cdCardLock);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ltt:cdcardlock:update")
    public R update(@RequestBody CdCardLockDTO cdCardLock){
		cdCardLockService.updateById(cdCardLock);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ltt:cdcardlock:delete")
    public R delete(@RequestBody Integer[] ids){
		cdCardLockService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
