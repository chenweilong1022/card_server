package io.renren.modules.app.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.ltt.dto.CdCardGroupDTO;
import io.renren.modules.ltt.dto.CdCardLockDTO;
import io.renren.modules.ltt.service.CdCardGroupService;
import io.renren.modules.ltt.vo.CdCardGroupVO;
import io.renren.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 *
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-12-17 13:42:55
 */
@RestController
@RequestMapping("app/cdcardgroup")
public class AppCdCardGroupController extends AbstractController {

    @Autowired
    private CdCardGroupService cdCardGroupService;

    @RequestMapping("/getDeviceIdByProjectId")
    @Login
    public R getDeviceIdByProjectId(@RequestBody CdCardLockDTO cdCardLockDTO) {
        return R.data(cdCardGroupService.getDeviceIdByProjectId(cdCardLockDTO,this.cdUserEntity));
    }

}
