package io.renren.modules.netty.messagehandler.countdown;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import io.netty.channel.Channel;
import io.renren.modules.netty.codec.Invocation;
import io.renren.modules.netty.dispatcher.MessageHandler;
import io.renren.modules.netty.message.countdown.CountDownRequest;
import io.renren.modules.netty.message.countdown.CountDownResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CountDownRequestHandler implements MessageHandler<CountDownRequest> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(Channel channel, CountDownRequest message) {
        logger.info("[execute][收到连接({}) 的倒计时请求]", channel.id());


        DateTime date = DateUtil.date();
        int seconds = date.getSeconds();

        // 响应倒计时
        CountDownResponse response = new CountDownResponse();
        response.setCountDown(60 - seconds);
        channel.writeAndFlush(new Invocation(CountDownResponse.TYPE, response).response());
    }

    @Override
    public String getType() {
        return CountDownRequest.TYPE;
    }


}
