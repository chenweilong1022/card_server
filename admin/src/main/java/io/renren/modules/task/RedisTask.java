//package io.renren.modules.task;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * @author liuyuchan
// * @email liuyuchan286@gmail.com
// * @date 2023/11/21 18:45
// */
//@Component
//@Slf4j
//@EnableAsync
//public class RedisTask {
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Scheduled(fixedDelay = 5000)
//    @Async
//    @Transactional(rollbackFor = Exception.class)
//    public void updateApp() {
//        System.out.println("update");
//        String s = stringRedisTemplate.opsForValue().get("aaa");
//        System.out.println(s);
//    }
//
//}
