package io.renren.modules.app.controller;

import cn.hutool.json.JSONUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.ltt.dto.CdCardGroupDTO;
import io.renren.modules.ltt.dto.CdCardLockDTO;
import io.renren.modules.ltt.service.CdCardGroupService;
import io.renren.modules.ltt.vo.CdCardGroupVO;
import io.renren.modules.sys.controller.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

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
@Slf4j
public class AppCdCardGroupController extends AbstractController {

    @Autowired
    private CdCardGroupService cdCardGroupService;

    @RequestMapping("/getDeviceIdByProjectId")
    @Login
    public R getDeviceIdByProjectId(@RequestBody CdCardLockDTO cdCardLockDTO) {
        log.info("getDeviceIdByProjectId = {}", JSONUtil.toJsonStr(cdCardLockDTO));
        return R.data(cdCardGroupService.getDeviceIdByProjectId(cdCardLockDTO,this.cdUserEntity));
    }

    @GetMapping("/getDeviceIdByProjectId/get")
    @Login
    public R getDeviceIdByProjectIdGet(@RequestParam(name = "token",required = false)String token,@RequestParam(name = "projectId",required = false)Integer projectId) {
        CdCardLockDTO cdCardLockDTO = new CdCardLockDTO();
        cdCardLockDTO.setProjectId(projectId);
        log.info("getDeviceIdByProjectId = {}", JSONUtil.toJsonStr(cdCardLockDTO));
        return R.data(cdCardGroupService.getDeviceIdByProjectId(cdCardLockDTO,this.cdUserEntity));
    }

}
