package io.renren.modules.netty.server;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.util.AttributeKey;
import io.renren.modules.ltt.entity.CdDevicesEntity;
import io.renren.modules.ltt.enums.Online;
import io.renren.modules.ltt.service.CdDevicesService;
import io.renren.modules.netty.codec.Invocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 客户端 Channel 管理器。提供两种功能：
 * 1. 客户端 Channel 的管理
 * 2. 向客户端 Channel 发送消息
 */
@Component
public class NettyChannelManager {

    @Autowired
    private CdDevicesService cdDevicesService;

    /**
     * {@link Channel#attr(AttributeKey)} 属性中，表示 Channel 对应的用户
     */
    private static final AttributeKey<String> CHANNEL_ATTR_KEY_USER = AttributeKey.newInstance("user");

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Channel 映射
     */
    private ConcurrentMap<ChannelId, Channel> channels = new ConcurrentHashMap<>();
    /**
     * 用户与 Channel 的映射。
     *
     * 通过它，可以获取用户对应的 Channel。这样，我们可以向指定用户发送消息。
     */
    private ConcurrentMap<String, Channel> userChannels = new ConcurrentHashMap<>();

    /**
     * 添加 Channel 到 {@link #channels} 中
     *
     * @param channel Channel
     */
    public void add(Channel channel) {
        channels.put(channel.id(), channel);
        logger.info("[add][一个连接({})加入]", channel.id());
    }

    /**
     * 添加指定用户到 {@link #userChannels} 中
     *
     * @param channel Channel
     * @param user 用户
     */
    public void addUser(Channel channel, String user) {
        Channel existChannel = channels.get(channel.id());
        if (existChannel == null) {
            logger.error("[addUser][连接({}) 不存在]", channel.id());
            return;
        }
        //是否包含用户
        boolean containsKey = userChannels.containsKey(user);
        if (!containsKey) {
            //将当前设备在线
            CdDevicesEntity cdDevicesEntity = new CdDevicesEntity();
            cdDevicesEntity.setOnline(Online.YES.getKey());
            cdDevicesService.update(cdDevicesEntity,new QueryWrapper<CdDevicesEntity>().lambda()
                    .eq(CdDevicesEntity::getIccid,user)
            );
        }
        // 设置属性
        channel.attr(CHANNEL_ATTR_KEY_USER).set(user);
        // 添加到 userChannels
        userChannels.put(user, channel);
    }

    /**
     * 将 Channel 从 {@link #channels} 和 {@link #userChannels} 中移除
     *
     * @param channel Channel
     */
    public void remove(Channel channel) {
        // 移除 channels
        channels.remove(channel.id());
        // 移除 userChannels
        if (channel.hasAttr(CHANNEL_ATTR_KEY_USER)) {
            String device = channel.attr(CHANNEL_ATTR_KEY_USER).get();
            userChannels.remove(device);
            //将当前设备离线
            CdDevicesEntity cdDevicesEntity = new CdDevicesEntity();
		    cdDevicesEntity.setOnline(Online.NO.getKey());
		    cdDevicesService.update(cdDevicesEntity,new QueryWrapper<CdDevicesEntity>().lambda()
                    .eq(CdDevicesEntity::getIccid,device)
            );
        }
        logger.info("[remove][一个连接({})离开]", channel.id());
    }

    /**
     * 向指定用户发送消息
     *
     * @param user 用户
     * @param invocation 消息体
     */
    public void send(String user, Invocation invocation) {
        logger.error(user + "=====" + JSONUtil.toJsonStr(userChannels));
        // 获得用户对应的 Channel
        Channel channel = userChannels.get(user);
        if (channel == null) {
            logger.error("[send][连接不存在]");
            return;
        }
        if (!channel.isActive()) {
            logger.error("[send][连接({})未激活]", channel.id());
            return;
        }
        logger.error("user = {} 开始 update",user);
        // 发送消息
        channel.writeAndFlush(invocation.response());
    }

    public Online onlineStatus(String user) {
        // 获得用户对应的 Channel
        Channel channel = userChannels.get(user);
        if (channel == null) {
            logger.error("[send][连接不存在]");
            return Online.NO;
        }
        return Online.YES;
    }

    /**
     * 向所有用户发送消息
     *
     * @param invocation 消息体
     */
    public void sendAll(Invocation invocation) {
        for (Channel channel : channels.values()) {
            if (!channel.isActive()) {
                logger.error("[send][连接({})未激活]", channel.id());
                return;
            }
            // 发送消息
            channel.writeAndFlush(invocation.response());
        }
    }

}
