package io.renren.modules.netty.messagehandler.chat;

import io.netty.channel.Channel;
import io.renren.modules.netty.codec.Invocation;
import io.renren.modules.netty.dispatcher.MessageHandler;
import io.renren.modules.netty.message.chat.ChatRedirectToUserRequest;
import io.renren.modules.netty.message.chat.ChatSendResponse;
import io.renren.modules.netty.message.chat.ChatSendToAllRequest;
import io.renren.modules.netty.server.NettyChannelManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 消息 - 广播
 */
@Slf4j
@Component
@Order(100)
public class ChatSendToAllRequestHandler implements MessageHandler<ChatSendToAllRequest> {

    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Override
    public String toString() {
        return "ChatSendToAllRequest{}";
    }

    @Override
    public void execute(Channel channel, ChatSendToAllRequest message) {
        log.info("[execute][收到连接({}) 的广播]", channel.id());
        // 这里，假装直接成功
        ChatSendResponse sendResponse = new ChatSendResponse().setMsgId(message.getMsgId()).setCode(0);
        channel.writeAndFlush(new Invocation(ChatSendResponse.TYPE, sendResponse).response());

        // 创建转发的消息，并广播发送
        ChatRedirectToUserRequest sendToUserRequest = new ChatRedirectToUserRequest().setMsgId(message.getMsgId())
                .setContent(message.getContent());
        nettyChannelManager.sendAll(new Invocation(ChatRedirectToUserRequest.TYPE, sendToUserRequest));

    }

    @Override
    public String getType() {
        return ChatSendToAllRequest.TYPE;
    }
}
