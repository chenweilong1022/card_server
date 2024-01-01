package io.renren.modules.ltt.controller;

import java.util.Arrays;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.ltt.dto.CdProjectStatisticsDTO;
import io.renren.modules.ltt.vo.CdProjectStatisticsVO;
import io.renren.modules.ltt.service.CdProjectStatisticsService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 *
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2024-01-01 15:32:55
 */
@RestController
@RequestMapping("ltt/cdprojectstatistics")
public class CdProjectStatisticsController {

    @Autowired
    private CdProjectStatisticsService cdProjectStatisticsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ltt:cdprojectstatistics:list")
    public R list(CdProjectStatisticsDTO cdProjectStatistics){
        PageUtils page = cdProjectStatisticsService.queryPage(cdProjectStatistics);

        return R.ok().put("page", page);
    }

    /**
     * 统计
     */
    @RequestMapping("/statistics")
    @RequiresPermissions("ltt:cdprojectstatistics:list")
    public R statistics(CdProjectStatisticsDTO cdProjectStatistics){
        return R.data(cdProjectStatisticsService.statistics(cdProjectStatistics));
    }

    /**
     * generateStatistics
     */
    @RequestMapping("/generateStatistics")
    @RequiresPermissions("ltt:cdprojectstatistics:list")
    public R generateStatistics(@RequestBody Integer[] ids){
        return R.data(cdProjectStatisticsService.generateStatistics(ids));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ltt:cdprojectstatistics:info")
    public R info(@PathVariable("id") Integer id){
		CdProjectStatisticsVO cdProjectStatistics = cdProjectStatisticsService.getById(id);

        return R.ok().put("cdProjectStatistics", cdProjectStatistics);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ltt:cdprojectstatistics:save")
    public R save(@RequestBody CdProjectStatisticsDTO cdProjectStatistics){
		cdProjectStatisticsService.save(cdProjectStatistics);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ltt:cdprojectstatistics:update")
    public R update(@RequestBody CdProjectStatisticsDTO cdProjectStatistics){
		cdProjectStatisticsService.updateById(cdProjectStatistics);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ltt:cdprojectstatistics:delete")
    public R delete(@RequestBody Integer[] ids){
		cdProjectStatisticsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
