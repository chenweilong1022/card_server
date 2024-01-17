package io.renren.modules.app.dto;

import io.renren.common.base.dto.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AppCdCardUpdateIccidDTO extends PageParam implements Serializable {


    /**
     * 设备id
     */
    @ApiModelProperty(required=false,value="设备id")
    private String deviceId;
    /**
     * 板子的下标
     */
    @ApiModelProperty(required=false,value="卡的下标")
    private Integer boardIndexed;
    /**
     * 卡的下标
     */
    @ApiModelProperty(required=false,value="数量")
    private Integer indexed;
    /**
     * iccid
     */
    @ApiModelProperty(required=false,value="iccid")
    private String iccid;
    /**
     * 手机号
     */
    @ApiModelProperty(required=false,value="手机号")
    private String phone;
    /**
     * 到期时间
     */
    @ApiModelProperty(required=false,value="到期时间")
    private String expireTimeStr;
}
