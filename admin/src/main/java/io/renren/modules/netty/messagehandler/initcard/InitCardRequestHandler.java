package io.renren.modules.netty.messagehandler.initcard;

import io.netty.channel.Channel;
import io.renren.modules.netty.codec.Invocation;
import io.renren.modules.netty.dispatcher.MessageHandler;
import io.renren.modules.netty.message.heartbeat.HeartbeatRequest;
import io.renren.modules.netty.message.heartbeat.HeartbeatResponse;
import io.renren.modules.netty.message.initcard.InitCardRequest;
import io.renren.modules.netty.message.initcard.InitCardResponse;
import io.renren.modules.netty.server.NettyChannelManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(100)
public class InitCardRequestHandler implements MessageHandler<InitCardRequest> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Override
    public void execute(Channel channel, InitCardRequest message) {
        logger.info("[execute][收到连接({}) 的心跳请求]", channel.id());
        nettyChannelManager.addUser(channel,message.getDeviceId());
        InitCardResponse response = new InitCardResponse();
        channel.writeAndFlush(new Invocation(InitCardResponse.TYPE, response).response());
    }

    @Override
    public String getType() {
        return InitCardRequest.TYPE;
    }

}
