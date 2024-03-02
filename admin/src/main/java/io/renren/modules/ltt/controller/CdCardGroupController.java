package io.renren.modules.ltt.controller;

import java.io.IOException;
import java.util.Arrays;

import io.renren.modules.ltt.dto.CdCardGroupExportPhoneTxtDTO;
import io.renren.modules.ltt.dto.CdIccidPhoneDTO;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.ltt.dto.CdCardGroupDTO;
import io.renren.modules.ltt.vo.CdCardGroupVO;
import io.renren.modules.ltt.service.CdCardGroupService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

import javax.servlet.http.HttpServletResponse;


/**
 *
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-12-17 13:42:55
 */
@RestController
@RequestMapping("ltt/cdcardgroup")
public class CdCardGroupController {
    @Autowired
    private CdCardGroupService cdCardGroupService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ltt:cdcardgroup:list")
    public R list(CdCardGroupDTO cdCardGroup){
        PageUtils page = cdCardGroupService.queryPage(cdCardGroup);
        return R.ok().put("page", page);
    }

    /**
     * 导出txt
     */
    @RequestMapping("/exportPhoneTxt")
    @RequiresPermissions("ltt:cdcardgroup:list")
    public void export(CdCardGroupExportPhoneTxtDTO exportPhoneTxtDTO, HttpServletResponse response) throws IOException {
        byte[] bytes = cdCardGroupService.export(exportPhoneTxtDTO);
        response.reset();
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s.txt\"",java.net.URLEncoder.encode("导出手机号","UTF-8")));
        response.addHeader("Content-Length", "" + bytes.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(bytes, response.getOutputStream());
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ltt:cdcardgroup:info")
    public R info(@PathVariable("id") Integer id){
		CdCardGroupVO cdCardGroup = cdCardGroupService.getById(id);

        return R.ok().put("cdCardGroup", cdCardGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ltt:cdcardgroup:save")
    public R save(@RequestBody CdCardGroupDTO cdCardGroup){
		cdCardGroupService.save(cdCardGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ltt:cdcardgroup:update")
    public R update(@RequestBody CdCardGroupDTO cdCardGroup){
		cdCardGroupService.updateById(cdCardGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ltt:cdcardgroup:delete")
    public R delete(@RequestBody Integer[] ids){
		cdCardGroupService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
