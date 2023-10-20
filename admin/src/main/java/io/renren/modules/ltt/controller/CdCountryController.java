package io.renren.modules.ltt.controller;

import java.util.Arrays;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.ltt.dto.CdCountryDTO;
import io.renren.modules.ltt.vo.CdCountryVO;
import io.renren.modules.ltt.service.CdCountryService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 国家表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-14 23:45:07
 */
@RestController
@RequestMapping("ltt/cdcountry")
public class CdCountryController {
    @Autowired
    private CdCountryService cdCountryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ltt:cdcountry:list")
    public R list(CdCountryDTO cdCountry){
        PageUtils page = cdCountryService.queryPage(cdCountry);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ltt:cdcountry:info")
    public R info(@PathVariable("id") Integer id){
		CdCountryVO cdCountry = cdCountryService.getById(id);

        return R.ok().put("cdCountry", cdCountry);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ltt:cdcountry:save")
    public R save(@RequestBody CdCountryDTO cdCountry){
		cdCountryService.save(cdCountry);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ltt:cdcountry:update")
    public R update(@RequestBody CdCountryDTO cdCountry){
		cdCountryService.updateById(cdCountry);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ltt:cdcountry:delete")
    public R delete(@RequestBody Integer[] ids){
		cdCountryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
