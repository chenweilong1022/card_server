package io.renren.modules.app.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.ltt.conver.CdUserConver;
import io.renren.modules.ltt.dto.CdUserDTO;
import io.renren.modules.ltt.service.CdUserService;
import io.renren.modules.ltt.vo.CdUserVO;
import io.renren.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 卡池用户表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-18 23:26:34
 */
@RestController
@RequestMapping("app/cduser")
public class AppCdUserController extends AbstractController {

    @Autowired
    private CdUserService cdUserService;

    /**
     * 注册
     */
    @RequestMapping("/register")
    public R register(@RequestBody CdUserDTO cdUser){
        cdUserService.register(cdUser);
        return R.ok();
    }

    /**
     * 登录
     */
    @RequestMapping("/login")
    public R login(@RequestBody CdUserDTO cdUser){
        return R.data(cdUserService.login(cdUser));
    }


    /**
     * info
     */
    @RequestMapping("/info")
    public R info(CdUserDTO cdUser){
        return R.data(CdUserConver.MAPPER.conver1(cdUserEntity));
    }

}
