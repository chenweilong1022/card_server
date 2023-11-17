package io.renren.modules.netty.message.reboot;


import io.renren.modules.netty.dispatcher.Message;
import lombok.Data;

/**
 * 消息 - 心跳请求
 */
@Data
public class RebootRequest implements Message {

    /**
     * 类型 - 心跳请求
     */
    public static final String TYPE = "REBOOT_REQUEST";

    /**
     * 设备id
     */
    private String deviceId;

    @Override
    public String toString() {
        return "RebootRequest{}";
    }

}
