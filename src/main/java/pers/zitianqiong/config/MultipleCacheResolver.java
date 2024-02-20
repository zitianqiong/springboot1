package pers.zitianqiong.config;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import jakarta.validation.constraints.NotNull;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.data.redis.cache.RedisCacheManager;
import pers.zitianqiong.domain.CaffeineRedisCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MultipleCacheResolver implements CacheResolver {

    private final RedisCacheManager redisCacheManager;
    private final CaffeineCacheManager caffeineCacheManager;

    public MultipleCacheResolver(CaffeineCacheManager caffeineCacheManager, RedisCacheManager redisCacheManager) {
        this.caffeineCacheManager = caffeineCacheManager;
        this.redisCacheManager = redisCacheManager;
    }

    @Override
    @NotNull
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
         Collection<String> cacheNames = context.getOperation().getCacheNames();
        if (CollectionUtils.isEmpty(cacheNames)) {
            return Collections.emptyList();
        }

        Collection<Cache> result = new ArrayList<>(cacheNames.size());
        for (String cacheName : cacheNames) {

            CaffeineRedisCache caffeineRedisCache = new CaffeineRedisCache(false);
            Cache caffeineCache = caffeineCacheManager.getCache(cacheName);
            if (caffeineCache == null) {
                throw new IllegalArgumentException("Cannot find cache named '" +
                        cacheName + "' for " + context.getOperation());
            }
            caffeineRedisCache.setFirstCache(caffeineCache);

            // 需要创建redis二级缓存
            Cache redisCache = redisCacheManager.getCache(cacheName);
            if (redisCache == null) {
                throw new IllegalArgumentException("Cannot find cache named '" +
                        cacheName + "' for " + context.getOperation());
            }
            caffeineRedisCache.setSecondCache(redisCache);

            result.add(caffeineRedisCache);
        }
        return result;
    }
}