package io.renren.modules.netty.messagehandler.heartbeat;

import io.netty.channel.Channel;
import io.renren.modules.netty.codec.Invocation;
import io.renren.modules.netty.dispatcher.MessageHandler;
import io.renren.modules.netty.message.heartbeat.HeartbeatRequest;
import io.renren.modules.netty.message.heartbeat.HeartbeatResponse;
import io.renren.modules.netty.server.NettyChannelManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HeartbeatRequestHandler implements MessageHandler<HeartbeatRequest> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Override
    public void execute(Channel channel, HeartbeatRequest message) {
        logger.info("[execute][收到连接({}) 的心跳请求]", channel.id());
        nettyChannelManager.addUser(channel,message.getDeviceId());
        // 响应心跳
        HeartbeatResponse response = new HeartbeatResponse();
        channel.writeAndFlush(new Invocation(HeartbeatResponse.TYPE, response).response());
    }

    @Override
    public String getType() {
        return HeartbeatRequest.TYPE;
    }

}
