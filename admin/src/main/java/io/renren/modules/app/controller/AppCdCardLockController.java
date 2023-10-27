package io.renren.modules.app.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.ltt.dto.CdCardLockDTO;
import io.renren.modules.ltt.service.CdCardLockService;
import io.renren.modules.ltt.vo.CdCardLockVO;
import io.renren.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 卡锁
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-19 02:22:45
 */
@RestController
@RequestMapping("app/cdcardlock")
public class AppCdCardLockController extends AbstractController {
    @Autowired
    private CdCardLockService cdCardLockService;


    /**
     * 获取手机号
     * @param cdCardLock
     * @return
     */
    @RequestMapping("/getMobile")
    @Login
    public R getMobile(@RequestBody CdCardLockDTO cdCardLock){
        return R.data(cdCardLockService.getMobile(cdCardLock,this.cdUserEntity));
    }

    /**
     * 获取手机号
     * @param cdCardLock
     * @return
     */
    @RequestMapping("/deviceTaskGet")
    public R deviceTaskGet(@RequestBody CdCardLockDTO cdCardLock){
        return R.data(cdCardLockService.deviceTaskGet(cdCardLock));
    }


    /**
     * 释放手机号
     * @param cdCardLock
     * @return
     */
    @RequestMapping("/releaseMobile")
    @Login
    public R releaseMobile(@RequestBody CdCardLockDTO cdCardLock){
        return R.data(cdCardLockService.releaseMobile(cdCardLock,this.cdUserEntity));
    }

    /**
     * 释放手机号
     * @param cdCardLock
     * @return
     */
    @RequestMapping("/releaseMobileAll")
    @Login
    public R releaseMobileAll(@RequestBody CdCardLockDTO cdCardLock){
        return R.data(cdCardLockService.releaseMobileAll(cdCardLock,this.cdUserEntity));
    }

    /**
     * 获取验证码
     * @param cdCardLock
     * @return
     */
    @RequestMapping("/getSms")
    @Login
    public R getSms(@RequestBody CdCardLockDTO cdCardLock){
        return R.data(cdCardLockService.getSms(cdCardLock,this.cdUserEntity));
    }

    /**
     * 上传验证码
     * @param cdCardLock
     * @return
     */
    @RequestMapping("/uploadSms")
    public R uploadSms(@RequestBody CdCardLockDTO cdCardLock){
        return R.data(cdCardLockService.uploadSms(cdCardLock,this.cdUserEntity));
    }


}
