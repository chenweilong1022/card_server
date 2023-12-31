package io.renren.modules.netty.message.heartbeat;


import io.renren.modules.netty.dispatcher.Message;
import lombok.Data;

/**
 * 消息 - 心跳请求
 */
@Data
public class HeartbeatRequest implements Message {

    /**
     * 类型 - 心跳请求
     */
    public static final String TYPE = "HEARTBEAT_REQUEST";

    /**
     * 设备id
     */
    private String deviceId;
    /**
     * 当前的ic
     */
    private String ic;
    private String fq;
    private String workFq;
    private String workNumber;

    @Override
    public String toString() {
        return "HeartbeatRequest{}";
    }

}
