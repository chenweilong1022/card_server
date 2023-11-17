package io.renren.modules.netty.messagehandler.updateapp;

import io.netty.channel.Channel;
import io.renren.modules.netty.codec.Invocation;
import io.renren.modules.netty.dispatcher.MessageHandler;
import io.renren.modules.netty.message.changecard.ChangeCardRequest;
import io.renren.modules.netty.message.initcard.InitCardResponse;
import io.renren.modules.netty.message.updateapp.UpdateappRequest;
import io.renren.modules.netty.server.NettyChannelManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateappRequestHandler implements MessageHandler<UpdateappRequest> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Override
    public void execute(Channel channel, UpdateappRequest message) {
        logger.info("[execute][收到连接({}) 的心跳请求]", channel.id());
    }

    @Override
    public String getType() {
        return UpdateappRequest.TYPE;
    }

}
