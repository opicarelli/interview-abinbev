package com.opicarelli.abinbev.challenge.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CaffeineCacheConfig {

    @Bean
    public CaffeineCache cacheProducts() {
        CaffeineCache cache = new CaffeineCache("products",
                Caffeine.newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .recordStats()
                .build());
        return cache;
    }

}