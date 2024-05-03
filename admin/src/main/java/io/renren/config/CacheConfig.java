package io.renren.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.renren.modules.app.dto.TaskDto;
import io.renren.modules.ltt.entity.CdProjectEntity;
import io.renren.modules.ltt.vo.CdProjectVO;
import io.renren.modules.ltt.vo.UpdateAppVO;
import io.renren.modules.netty.message.heartbeat.HeartbeatRequest;
import io.renren.modules.sys.entity.ProjectWorkEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    @Bean(value = "caffeineCacheCode")
    public Cache<String, String> caffeineCacheCode() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后两个小时后过期
                .expireAfterWrite(7200, TimeUnit.DAYS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }

    @Bean(value = "booleanCache")
    public Cache<String, Boolean> booleanCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后两个小时后过期
                .expireAfterWrite(7200, TimeUnit.DAYS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }

    @Bean(value = "mapCache")
    public Cache<String, Map<String,String>> mapCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后两个小时后过期
                .expireAfterWrite(7200, TimeUnit.DAYS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }

    @Bean(value = "mapDateCache")
    public Cache<String, Map<String, Date>> mapDateCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后两个小时后过期
                .expireAfterWrite(7200, TimeUnit.DAYS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }

    @Bean(value = "stringHeartbeatRequestCache")
    public Cache<String, HeartbeatRequest> stringHeartbeatRequestCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后两个小时后过期
                .expireAfterWrite(7200, TimeUnit.DAYS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }

    @Bean(value = "caffeineCacheProjectWorkEntity")
    public Cache<String, ProjectWorkEntity> caffeineCacheProjectWorkEntity() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后两个小时后过期
                .expireAfterWrite(7200, TimeUnit.DAYS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }

    @Bean(value = "caffeineCacheCodeTaskDto")
    public Cache<String, TaskDto> caffeineCacheCodeTaskDto() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后两个小时后过期
                .expireAfterWrite(7200, TimeUnit.DAYS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }


    @Bean(value = "caffeineCacheSet")
    public Cache<String, HashSet<String>> caffeineCacheSet() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后两个小时后过期
                .expireAfterWrite(7200, TimeUnit.DAYS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }

    @Bean(value = "cdProjectListVOCache")
    public Cache<String, List<CdProjectEntity>> cdProjectListVOCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后两个小时后过期
                .expireAfterWrite(7200, TimeUnit.DAYS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }

    @Bean(value = "stringListCacheUpdateAppVO")
    public Cache<String, Queue<UpdateAppVO>> stringListCacheUpdateAppVO() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后两个小时后过期
                .expireAfterWrite(7200, TimeUnit.DAYS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }

//
//    @Bean(value = "caffeineCacheIntegerCode")
//    public Cache<String, Integer> caffeineCacheIntegerCode() {
//        return Caffeine.newBuilder()
//                // 设置最后一次写入或访问后两个小时后过期
//                .expireAfterWrite(7200, TimeUnit.DAYS)
//                // 初始的缓存空间大小
//                .initialCapacity(100)
//                // 缓存的最大条数
//                .maximumSize(1000)
//                .build();
//    }

}
