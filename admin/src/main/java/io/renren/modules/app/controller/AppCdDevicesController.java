package io.renren.modules.app.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.ltt.dto.CdDevicesDTO;
import io.renren.modules.ltt.service.CdDevicesService;
import io.renren.modules.ltt.vo.CdDevicesVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 设备表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-05 23:22:42
 */
@RestController
@RequestMapping("app/cddevices")
public class AppCdDevicesController {

    @Autowired
    private CdDevicesService cdDevicesService;

    /**
     * 保存
     */
    @RequestMapping("/uploadDevice")
    public R uploadDevice(@RequestBody CdDevicesDTO cdDevices){
        return R.data(cdDevicesService.uploadDevice(cdDevices));
    }

}
