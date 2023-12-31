package io.renren.modules.netty.dispatcher;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.renren.modules.netty.codec.Invocation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ChannelHandler.Sharable
public class MessageDispatcher extends SimpleChannelInboundHandler<TextWebSocketFrame>{

    @Autowired
    private MessageHandlerContainer messageHandlerContainer;

    private final ExecutorService executor =  Executors.newFixedThreadPool(200);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {

        String content = textWebSocketFrame.text();
        System.out.println(content);

        Invocation invocation = JSONUtil.toBean(content, Invocation.class);

        MessageHandler messageHandler = messageHandlerContainer.getMessageHandler(invocation.getType());

        Class<? extends Message> messageClass = MessageHandlerContainer.getMessageClass(messageHandler);
        // 解析消息
        Message message = JSON.parseObject(invocation.getMessage(), messageClass);
        // 执行逻辑
        executor.submit(new Runnable() {
            @Override
            public void run() {
                // noinspection unchecked
                messageHandler.execute(ctx.channel(), message);
            }
        });
    }

//    //    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, Invocation invocation) {
//        // 获得 type 对应的 MessageHandler 处理器
//        MessageHandler messageHandler = messageHandlerContainer.getMessageHandler(invocation.getType());
//        // 获得  MessageHandler 处理器 的消息类
//        Class<? extends Message> messageClass = MessageHandlerContainer.getMessageClass(messageHandler);
//        // 解析消息
//        Message message = JSON.parseObject(invocation.getMessage(), messageClass);
//        // 执行逻辑
//        executor.submit(new Runnable() {
//
//            @Override
//            public void run() {
//                // noinspection unchecked
//                messageHandler.execute(ctx.channel(), message);
//            }
//
//        });
//    }

}
