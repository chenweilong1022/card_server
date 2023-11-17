package io.renren.modules.netty.config;


import io.renren.modules.netty.dispatcher.MessageDispatcher;
import io.renren.modules.netty.dispatcher.MessageHandlerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class NettyServerConfig {

    @Bean
    @Order(300)
    public MessageDispatcher messageDispatcher() {
        return new MessageDispatcher();
    }

    @Bean
    @Order(200)
    public MessageHandlerContainer messageHandlerContainer() {
        return new MessageHandlerContainer();
    }

}
