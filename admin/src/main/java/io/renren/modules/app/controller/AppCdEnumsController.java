package io.renren.modules.app.controller;

import io.renren.common.utils.EnumUtil;
import io.renren.common.utils.R;
import io.renren.modules.ltt.dto.CdDevicesDTO;
import io.renren.modules.ltt.enums.AuditStatus;
import io.renren.modules.ltt.service.CdDevicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 设备表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-05 23:22:42
 */
@RestController
@RequestMapping("app/enums")
public class AppCdEnumsController {

    @Autowired
    private CdDevicesService cdDevicesService;

    /**
     * 保存
     */
    @RequestMapping("/auditStatus")
    public R auditStatus(){
        return R.data(EnumUtil.enumToVo(AuditStatus.values()));
    }

}
