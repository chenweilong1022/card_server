package io.renren.modules.ltt.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import io.renren.common.utils.ConfigConstant;
import io.renren.common.validator.Assert;
import io.renren.modules.ltt.dto.CdCardLockDTO;
import io.renren.modules.ltt.dto.CdDevicesInitDTO;
import io.renren.modules.ltt.dto.CdDevicesUpdateAppDTO;
import io.renren.modules.ltt.entity.CdCardLockEntity;
import io.renren.modules.ltt.entity.CdUserEntity;
import io.renren.modules.ltt.firefox.PhoneAddBatch;
import io.renren.modules.ltt.firefox.PhoneList;
import io.renren.modules.ltt.firefox.Root;
import io.renren.modules.ltt.service.CdCardLockService;
import io.renren.modules.ltt.service.CdUserService;
import io.renren.modules.sys.entity.ProjectWorkEntity;
import io.renren.modules.sys.entity.SysConfigEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.ltt.dto.CdDevicesDTO;
import io.renren.modules.ltt.vo.CdDevicesVO;
import io.renren.modules.ltt.service.CdDevicesService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

import javax.annotation.Resource;


/**
 * 设备表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-05 23:22:42
 */
@RestController
@RequestMapping("ltt/cddevices")
public class CdDevicesController {
    @Autowired
    private CdDevicesService cdDevicesService;
    @Autowired
    private CdCardLockService cdCardLockService;

    @Autowired
    private CdUserService cdUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ltt:cddevices:list")
    public R list(CdDevicesDTO cdDevices){
        PageUtils page = cdDevicesService.queryPage(cdDevices);

        return R.ok().put("page", page);
    }

    /**
     * 切换卡
     */
    @RequestMapping("/changeCard")
    @RequiresPermissions("ltt:cddevices:list")
    public R changeCard(@RequestBody CdDevicesDTO cdDevices){
        return R.data(cdDevicesService.changeCard(cdDevices));
    }


    /**
     * 切换分组
     */
    @RequestMapping("/changeGroup")
    @RequiresPermissions("ltt:cddevices:list")
    public R changeGroup(@RequestBody CdDevicesDTO cdDevices){
        return R.data(cdDevicesService.changeGroup(cdDevices));
    }

    /**
     * 切换卡
     */
    @RequestMapping("/reboot")
    @RequiresPermissions("ltt:cddevices:list")
    public R reboot(@RequestBody Integer[] ids){
        return R.data(cdDevicesService.reboot(ids));
    }


    /**
     * 初始化
     */
    @RequestMapping("/initCard")
    @RequiresPermissions("ltt:cddevices:list")
    public R initCard(@RequestBody CdDevicesInitDTO initDTO){
        return R.data(cdDevicesService.initCard(initDTO));
    }

    /**
     * 初始化
     */
    @RequestMapping("/initCard3")
    @RequiresPermissions("ltt:cddevices:list")
    public R initCard3(@RequestBody Integer[] ids){
        return R.data(cdDevicesService.initCard3(ids));
    }


    /**
     * 拉黑
     */
    @RequestMapping("/withBlack")
    @RequiresPermissions("ltt:cddevices:list")
    public R withBlack(@RequestBody Integer[] ids){
        return R.data(cdDevicesService.withBlack(ids));
    }

    /**
     * 手机号删除
     */
    @RequestMapping("/phoneDeleteAll")
    @RequiresPermissions("ltt:cddevices:list")
    public R phoneDeleteAll(@RequestBody Integer[] ids){
        cdDevicesService.phoneDeleteAll(ids);
        return R.data(true);
    }

    @Resource(name = "caffeineCacheProjectWorkEntity")
    private Cache<String, ProjectWorkEntity> caffeineCacheProjectWorkEntity;

    /**
     * 火狐狸清空
     */
    @RequestMapping("/phoneDeleteAll2")
    @RequiresPermissions("ltt:cddevices:list")
    public R phoneDeleteAll2(@RequestBody SysConfigEntity config){
        //查询config
        String cacheKey = String.format("%s_%s", ConfigConstant.PROJECT_WORK_KEY, config.getId());
        ProjectWorkEntity projectWorkEntity = caffeineCacheProjectWorkEntity.getIfPresent(cacheKey);
        Root phoneAddBatch = new Root("PhoneDeleteAll");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(phoneAddBatch);
        } catch (JsonProcessingException e) {
            Assert.isTrue(true,e.getMessage());
        }
        String response = HttpUtil.post(projectWorkEntity.getCodeApiUrl(), json);
        return R.data(true);
    }

//    private void extracted() {
//        ProjectWorkEntity projectWorkEntity = caffeineCacheProjectWorkEntity.getIfPresent(ConfigConstant.PROJECT_WORK_KEY);
//        Root phoneAddBatch = new Root("PhoneDeleteAll");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = null;
//        try {
//            json = objectMapper.writeValueAsString(phoneAddBatch);
//        } catch (JsonProcessingException e) {
//
//        }
//        String response = HttpUtil.post(projectWorkEntity.getCodeApiUrl(), json);
//    }

    /**
     * 接码
     */
    @RequestMapping("/getCode")
    @RequiresPermissions("ltt:cddevices:list")
    public R getCode(@RequestBody Integer[] ids){
        //分组释放号码
        cdDevicesService.phoneDeleteAll(ids);
        //初始化号码
        cdDevicesService.initCard3(ids);
        return R.data(true);
    }


//    /**
//     * 初始化
//     */
//    @RequestMapping("/initCard2")
//    @RequiresPermissions("ltt:cddevices:list")
//    public R initCard2(@RequestBody Integer[] ids){
//        return R.data(cdDevicesService.initCard2(ids));
//    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ltt:cddevices:info")
    public R info(@PathVariable("id") Integer id){
		CdDevicesVO cdDevices = cdDevicesService.getById(id);

        return R.ok().put("cdDevices", cdDevices);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ltt:cddevices:save")
    public R save(@RequestBody CdDevicesDTO cdDevices){
		cdDevicesService.save(cdDevices);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ltt:cddevices:update")
    public R update(@RequestBody CdDevicesDTO cdDevices){
		cdDevicesService.updateById(cdDevices);

        return R.ok();
    }

    /**
     * 批量修改
     */
    @RequestMapping("/updateBatch")
    @RequiresPermissions("ltt:cddevices:update")
    public R updateBatch(@RequestBody CdDevicesDTO cdDevices){
        cdDevicesService.updateBatch(cdDevices);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ltt:cddevices:delete")
    public R delete(@RequestBody Integer[] ids){
		cdDevicesService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/updateApp")
    @RequiresPermissions("ltt:cddevices:delete")
    public R updateApp(@RequestBody CdDevicesUpdateAppDTO updateAppDTO){
        return R.data(cdDevicesService.updateApp(updateAppDTO));
    }

}
