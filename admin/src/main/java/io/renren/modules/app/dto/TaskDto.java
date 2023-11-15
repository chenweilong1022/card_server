package io.renren.modules.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskDto {
    private String type;
    private String httpUrl;
    private String deviceId;
    /**
     * 板子
     */
    @ApiModelProperty(required=false,value="板子")
    private Integer boardIndexed;
    /**
     * 卡下标
     */
    @ApiModelProperty(required=false,value="卡下标")
    private Integer indexed;
}
