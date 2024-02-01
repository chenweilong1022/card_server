package io.renren.modules.ltt.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.github.benmanes.caffeine.cache.Cache;
import io.renren.modules.ltt.entity.CdRechargedPhoneEntity;
import io.renren.modules.ltt.service.CdRechargedPhoneService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.ltt.dto.CdIccidPhoneDTO;
import io.renren.modules.ltt.vo.CdIccidPhoneVO;
import io.renren.modules.ltt.service.CdIccidPhoneService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


/**
 * iccid手机对接表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-30 00:10:56
 */
@RestController
@RequestMapping("ltt/cdiccidphone")
public class CdIccidPhoneController {
    @Autowired
    private CdIccidPhoneService cdIccidPhoneService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ltt:cdiccidphone:list")
    public R list(CdIccidPhoneDTO cdIccidPhone){
        PageUtils page = cdIccidPhoneService.queryPage(cdIccidPhone);

        return R.ok().put("page", page);
    }


    @Autowired
    private CdRechargedPhoneService cdRechargedPhoneService;
    @Resource(name = "caffeineCacheSet")
    private Cache<String, HashSet<String>> caffeineCacheSet;
    /**
     * 列表
     */
    @RequestMapping("/refreshPhone")
    @RequiresPermissions("ltt:cdiccidphone:list")
    public R refreshPhone(){
        List<CdRechargedPhoneEntity> list = cdRechargedPhoneService.list();
        HashSet<String> hashSet = new HashSet<>(60000);
        for (CdRechargedPhoneEntity cdRechargedPhoneEntity : list) {
            hashSet.add(cdRechargedPhoneEntity.getPhone());
        }
        caffeineCacheSet.put("caffeineCacheSet",hashSet);
        return R.ok();
    }


    /**
     * 导出txt
     */
    @RequestMapping("/exportTxt")
    @RequiresPermissions("ltt:cdiccidphone:list")
    public void export(CdIccidPhoneDTO cdIccidPhone, HttpServletResponse response) throws IOException {
        byte[] bytes = cdIccidPhoneService.export(cdIccidPhone);
        response.reset();
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s.txt\"",java.net.URLEncoder.encode("充值","UTF-8")));
        response.addHeader("Content-Length", "" + bytes.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(bytes, response.getOutputStream());
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ltt:cdiccidphone:info")
    public R info(@PathVariable("id") Integer id){
		CdIccidPhoneVO cdIccidPhone = cdIccidPhoneService.getById(id);

        return R.ok().put("cdIccidPhone", cdIccidPhone);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ltt:cdiccidphone:save")
    public R save(@RequestBody CdIccidPhoneDTO cdIccidPhone){
		cdIccidPhoneService.save(cdIccidPhone);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ltt:cdiccidphone:update")
    public R update(@RequestBody CdIccidPhoneDTO cdIccidPhone){
		cdIccidPhoneService.updateById(cdIccidPhone);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ltt:cdiccidphone:delete")
    public R delete(@RequestBody Integer[] ids){
		cdIccidPhoneService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
