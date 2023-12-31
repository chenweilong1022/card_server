package io.renren.modules.netty.messagehandler.heartbeat;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.benmanes.caffeine.cache.Cache;
import io.netty.channel.Channel;
import io.renren.modules.ltt.entity.CdDevicesEntity;
import io.renren.modules.ltt.enums.Online;
import io.renren.modules.ltt.service.CdDevicesService;
import io.renren.modules.netty.codec.Invocation;
import io.renren.modules.netty.dispatcher.MessageHandler;
import io.renren.modules.netty.message.heartbeat.HeartbeatRequest;
import io.renren.modules.netty.message.heartbeat.HeartbeatResponse;
import io.renren.modules.netty.server.NettyChannelManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Order(100)
public class HeartbeatRequestHandler implements MessageHandler<HeartbeatRequest> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private NettyChannelManager nettyChannelManager;
    @Autowired
    private CdDevicesService cdDevicesService;
    @Resource(name = "stringHeartbeatRequestCache")
    private Cache<String, HeartbeatRequest> stringHeartbeatRequestCache;

    @Override
    public void execute(Channel channel, HeartbeatRequest message) {
        logger.info("[execute][收到连接({}) 的心跳请求 {}", channel.id(), JSONUtil.toJsonStr(message));
        nettyChannelManager.addUser(channel,message.getDeviceId());
        //设置当前板子前台的信息
        stringHeartbeatRequestCache.put(message.getDeviceId(),message);
        // 响应心跳
        HeartbeatResponse response = new HeartbeatResponse();
        channel.writeAndFlush(new Invocation(HeartbeatResponse.TYPE, response).response());
    }

    @Override
    public String getType() {
        return HeartbeatRequest.TYPE;
    }

}
