package io.renren.modules.ltt.controller;

import java.util.Arrays;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.ltt.dto.CdBoardDTO;
import io.renren.modules.ltt.vo.CdBoardVO;
import io.renren.modules.ltt.service.CdBoardService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 卡的板子
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-05 23:22:42
 */
@RestController
@RequestMapping("ltt/cdboard")
public class CdBoardController {
    @Autowired
    private CdBoardService cdBoardService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ltt:cdboard:list")
    public R list(CdBoardDTO cdBoard){
        PageUtils page = cdBoardService.queryPage(cdBoard);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ltt:cdboard:info")
    public R info(@PathVariable("id") Integer id){
		CdBoardVO cdBoard = cdBoardService.getById(id);

        return R.ok().put("cdBoard", cdBoard);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ltt:cdboard:save")
    public R save(@RequestBody CdBoardDTO cdBoard){
		cdBoardService.save(cdBoard);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ltt:cdboard:update")
    public R update(@RequestBody CdBoardDTO cdBoard){
		cdBoardService.updateById(cdBoard);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ltt:cdboard:delete")
    public R delete(@RequestBody Integer[] ids){
		cdBoardService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
