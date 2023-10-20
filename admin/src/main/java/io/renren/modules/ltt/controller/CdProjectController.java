package io.renren.modules.ltt.controller;

import java.util.Arrays;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.ltt.dto.CdProjectDTO;
import io.renren.modules.ltt.vo.CdProjectVO;
import io.renren.modules.ltt.service.CdProjectService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 项目表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-14 23:45:07
 */
@RestController
@RequestMapping("ltt/cdproject")
public class CdProjectController {
    @Autowired
    private CdProjectService cdProjectService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ltt:cdproject:list")
    public R list(CdProjectDTO cdProject){
        PageUtils page = cdProjectService.queryPage(cdProject);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ltt:cdproject:info")
    public R info(@PathVariable("id") Integer id){
		CdProjectVO cdProject = cdProjectService.getById(id);

        return R.ok().put("cdProject", cdProject);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ltt:cdproject:save")
    public R save(@RequestBody CdProjectDTO cdProject){
		cdProjectService.save(cdProject);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ltt:cdproject:update")
    public R update(@RequestBody CdProjectDTO cdProject){
		cdProjectService.updateById(cdProject);

        return R.ok();
    }



    /**
     * 审核
     */
    @RequestMapping("/audit")
    @RequiresPermissions("ltt:cdproject:audit")
    public R audit(@RequestBody CdProjectDTO cdProject){
        cdProjectService.audit(cdProject);
        return R.ok();
    }

    /**
     * 审核
     */
    @RequestMapping("/auditAll")
    @RequiresPermissions("ltt:cdproject:auditAll")
    public R auditAll(@RequestBody CdProjectDTO cdProject){
        cdProjectService.auditAll(cdProject);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ltt:cdproject:delete")
    public R delete(@RequestBody Integer[] ids){
		cdProjectService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
