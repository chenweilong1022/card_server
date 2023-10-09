package io.renren.modules.ltt.controller;

import java.util.Arrays;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.ltt.dto.CdCardDTO;
import io.renren.modules.ltt.vo.CdCardVO;
import io.renren.modules.ltt.service.CdCardService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 卡
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-05 23:22:42
 */
@RestController
@RequestMapping("ltt/cdcard")
public class CdCardController {
    @Autowired
    private CdCardService cdCardService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ltt:cdcard:list")
    public R list(CdCardDTO cdCard){
        PageUtils page = cdCardService.queryPage(cdCard);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ltt:cdcard:info")
    public R info(@PathVariable("id") Integer id){
		CdCardVO cdCard = cdCardService.getById(id);

        return R.ok().put("cdCard", cdCard);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ltt:cdcard:save")
    public R save(@RequestBody CdCardDTO cdCard){
		cdCardService.save(cdCard);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ltt:cdcard:update")
    public R update(@RequestBody CdCardDTO cdCard){
		cdCardService.updateById(cdCard);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ltt:cdcard:delete")
    public R delete(@RequestBody Integer[] ids){
		cdCardService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
