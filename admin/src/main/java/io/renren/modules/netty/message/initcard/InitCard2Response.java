package io.renren.modules.netty.message.initcard;


import io.renren.modules.netty.dispatcher.Message;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 消息 - 心跳响应
 */
@Data
public class InitCard2Response implements Message {

    /**
     * 类型 - 心跳响应
     */
    public static final String TYPE = "INITCARD2_RESPONSE";

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

    @Override
    public String toString() {
        return "InitCard2Response{}";
    }
}
