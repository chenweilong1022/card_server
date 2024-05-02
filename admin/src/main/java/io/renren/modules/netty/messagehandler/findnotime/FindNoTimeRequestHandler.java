package io.renren.modules.netty.messagehandler.findnotime;

import io.netty.channel.Channel;
import io.renren.modules.netty.dispatcher.MessageHandler;
import io.renren.modules.netty.message.findnotime.FindNoTimeRequest;
import io.renren.modules.netty.message.updateapp.UpdateappRequest;
import io.renren.modules.netty.server.NettyChannelManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(100)
public class FindNoTimeRequestHandler implements MessageHandler<FindNoTimeRequest> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Override
    public void execute(Channel channel, FindNoTimeRequest message) {
        logger.info("[execute][收到连接({}) 的心跳请求]", channel.id());
    }

    @Override
    public String getType() {
        return FindNoTimeRequest.TYPE;
    }

}
