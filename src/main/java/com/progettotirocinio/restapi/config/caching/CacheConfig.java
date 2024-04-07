package com.progettotirocinio.restapi.config.caching;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig
{
    @Bean
    public CacheManager cacheManager() {
        return generateCacheManager();
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return generateTaskScheduler();
    }

    public static CacheManager generateCacheManager() {
        return new ConcurrentMapCacheManager();
    }

    public  static TaskScheduler generateTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }


}
