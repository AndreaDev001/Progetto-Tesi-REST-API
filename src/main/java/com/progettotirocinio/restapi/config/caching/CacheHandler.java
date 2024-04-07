package com.progettotirocinio.restapi.config.caching;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheHandler
{
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH::mm:ss");
    public final Map<Class<?>,String> allCaches = new HashMap<>();
    private final Map<Class<?>,String> searchCaches = new HashMap<>();
    private final CacheManager cacheManager;
    private final TaskScheduler taskScheduler;

    public void addAllCache(Class<?> requiredClass) {
        RequiresCaching requiresCaching = requiredClass.getAnnotation(RequiresCaching.class);
        if(requiresCaching.allCachingRequired()) {
            String name = !requiresCaching.allCacheName().isEmpty() ? requiresCaching.allCacheName() : "ALL_" + requiredClass.getSimpleName();
            allCaches.put(requiredClass,name);
            addCacheToManager(name,requiresCaching.requiredInterval());
        }
    }
    public void addSearchCache(Class<?> requiredClass) {
        RequiresCaching requiresCaching = requiredClass.getAnnotation(RequiresCaching.class);
        if(requiresCaching.searchCachingRequired()) {
            String name = !requiresCaching.allSearchName().isEmpty() ? requiresCaching.allSearchName() : "SEARCH_" + requiredClass.getSimpleName();
            searchCaches.put(requiredClass,name);
            addCacheToManager(name,requiresCaching.requiredInterval());
        }
    }
    private void handleCacheEvict(String cacheName) {
        cacheManager.getCache(cacheName).clear();
        log.info(String.format("Cache [%s] has been flushed at [%s]",cacheName,dateTimeFormatter.format(LocalDateTime.now())));
    }

    private void addCacheToManager(String cacheName,Integer delay) {
        ConcurrentMapCacheManager concurrentMapCacheManager = (ConcurrentMapCacheManager)cacheManager;
        if(concurrentMapCacheManager != null) {
            Collection<String> cacheNames = concurrentMapCacheManager.getCacheNames();
            List<String> values = new ArrayList<>(List.of(cacheName));
            values.addAll(cacheNames);
            concurrentMapCacheManager.setCacheNames(values);
            taskScheduler.scheduleWithFixedDelay(() -> handleCacheEvict(cacheName), Duration.ofSeconds(delay));
        }
    }
    public String getAllCacheName(Class<?> requiredClass) {
        return allCaches.get(requiredClass);
    }

    public String getSearchCacheName(Class<?> requiredClass) {
        return searchCaches.get(requiredClass);
    }
}
