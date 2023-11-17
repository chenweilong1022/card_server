package io.renren.modules.netty.message.initcard;


import io.renren.modules.app.dto.TaskDto;
import io.renren.modules.netty.dispatcher.Message;
import lombok.Data;

/**
 * 消息 - 心跳响应
 */
@Data
public class InitCardResponse implements Message {

    /**
     * 类型 - 心跳响应
     */
    public static final String TYPE = "INITCARD_RESPONSE";

    private TaskDto taskDto;

    @Override
    public String toString() {
        return "InitCardResponse{}";
    }
}
