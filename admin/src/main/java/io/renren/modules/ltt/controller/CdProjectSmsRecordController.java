package io.renren.modules.ltt.controller;

import java.util.Arrays;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.ltt.dto.CdProjectSmsRecordDTO;
import io.renren.modules.ltt.vo.CdProjectSmsRecordVO;
import io.renren.modules.ltt.service.CdProjectSmsRecordService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 项目短信记录
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-20 00:54:08
 */
@RestController
@RequestMapping("ltt/cdprojectsmsrecord")
public class CdProjectSmsRecordController {
    @Autowired
    private CdProjectSmsRecordService cdProjectSmsRecordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ltt:cdprojectsmsrecord:list")
    public R list(CdProjectSmsRecordDTO cdProjectSmsRecord){
        PageUtils page = cdProjectSmsRecordService.queryPage(cdProjectSmsRecord);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ltt:cdprojectsmsrecord:info")
    public R info(@PathVariable("id") Integer id){
		CdProjectSmsRecordVO cdProjectSmsRecord = cdProjectSmsRecordService.getById(id);

        return R.ok().put("cdProjectSmsRecord", cdProjectSmsRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ltt:cdprojectsmsrecord:save")
    public R save(@RequestBody CdProjectSmsRecordDTO cdProjectSmsRecord){
		cdProjectSmsRecordService.save(cdProjectSmsRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ltt:cdprojectsmsrecord:update")
    public R update(@RequestBody CdProjectSmsRecordDTO cdProjectSmsRecord){
		cdProjectSmsRecordService.updateById(cdProjectSmsRecord);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ltt:cdprojectsmsrecord:delete")
    public R delete(@RequestBody Integer[] ids){
		cdProjectSmsRecordService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
