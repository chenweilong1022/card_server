package io.renren.modules.netty.message.initcard;


import io.renren.modules.netty.dispatcher.Message;
import lombok.Data;

/**
 * 消息 - 心跳请求
 */
@Data
public class InitCardRequest implements Message {

    /**
     * 类型 - 心跳请求
     */
    public static final String TYPE = "INITCARD_REQUEST";

    /**
     * 设备id
     */
    private String deviceId;

    @Override
    public String toString() {
        return "InitCardRequest{}";
    }

}
