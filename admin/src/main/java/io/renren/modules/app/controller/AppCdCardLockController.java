package io.renren.modules.app.controller;

import cn.hutool.core.util.StrUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.ltt.dto.CdCardLockDTO;
import io.renren.modules.ltt.service.CdCardLockService;
import io.renren.modules.ltt.vo.CdCardLockVO;
import io.renren.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 卡锁
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-19 02:22:45
 */
@RestController
@RequestMapping({"app/cdcardlock","ltt/cdcardlock"})
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
        return R.data(cdCardLockService.getMobile(cdCardLock,this.cdUserEntity,null,null));
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
     * @param token
     * @param projectId
     * @param iccid
     * @return
     */
    @GetMapping("/releaseMobile/get")
    @Login
    public R releaseMobileGet(@RequestParam(name = "token",required = false)String token, @RequestParam(name = "projectId",required = false)Integer projectId, @RequestParam(name = "iccid",required = false)String iccid){
        CdCardLockDTO cdCardLock = new CdCardLockDTO();
        cdCardLock.setProjectId(projectId);
        cdCardLock.setIccid(iccid);
        return R.data(cdCardLockService.releaseMobile(cdCardLock,this.cdUserEntity));
    }

    /**
     * 拉黑手机
     * @param cdCardLock
     * @return
     */
    @RequestMapping("/withBlackMobile")
    @Login
    public R withBlackMobile(@RequestBody CdCardLockDTO cdCardLock){
        cdCardLockService.withBlackMobile(cdCardLock,this.cdUserEntity);
        return R.data(true);
    }


    /**
     * 拉黑手机s
     * @param token
     * @param projectId
     * @param iccid
     * @return
     */
    @GetMapping("/withBlackMobile/get")
    @Login
    public R withBlackMobileGet(@RequestParam(name = "token",required = false)String token, @RequestParam(name = "projectId",required = false)Integer projectId, @RequestParam(name = "iccid",required = false)String iccid){
        CdCardLockDTO cdCardLock = new CdCardLockDTO();
        cdCardLock.setProjectId(projectId);
        cdCardLock.setIccid(iccid);
        cdCardLockService.withBlackMobile(cdCardLock,this.cdUserEntity);
        return R.data(true);
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
        String sms = cdCardLockService.getSms(cdCardLock, this.cdUserEntity);
        if (StrUtil.isEmpty(sms)) {
            return R.error("等待验证码");
        }
        return R.data(extractVerificationCode(sms));
    }

    /**
     * 获取验证码
     * @param token
     * @param projectId
     * @param iccid
     * @return
     */
    @GetMapping("/getSms/get")
    @Login
    public R getSmsGet(@RequestParam(name = "token",required = false)String token, @RequestParam(name = "projectId",required = false)Integer projectId, @RequestParam(name = "iccid",required = false)String iccid){
        CdCardLockDTO cdCardLock = new CdCardLockDTO();
        cdCardLock.setProjectId(projectId);
        cdCardLock.setIccid(iccid);
        String sms = cdCardLockService.getSms(cdCardLock, this.cdUserEntity);
        if (StrUtil.isEmpty(sms)) {
            return R.error("等待验证码");
        }
        return R.data(extractVerificationCode(sms));
    }

    public String extractVerificationCode(String smsText) {
        // 使用正则表达式匹配短信内容中的验证码
        Pattern pattern = Pattern.compile("\\d{6}"); // 此处使用六位数字作为验证码的示例
        Matcher matcher = pattern.matcher(smsText);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
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
