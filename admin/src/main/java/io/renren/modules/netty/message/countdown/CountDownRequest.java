package io.renren.modules.netty.message.countdown;


import io.renren.modules.netty.dispatcher.Message;

/**
 * 消息 - 倒计时
 */
public class CountDownRequest implements Message {

    /**
     * 类型 - 倒计时
     */
    public static final String TYPE = "COUNTDOWN_REQUEST";

    @Override
    public String toString() {
        return "CountDownRequest{}";
    }

}
