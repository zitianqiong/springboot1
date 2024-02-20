package pers.zitianqiong.config;

import com.alibaba.fastjson2.support.spring6.data.redis.FastJsonRedisSerializer;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * <p>redis 配置</p>
 * @author 丛吉钰
 */
@Configuration
@EnableCaching
@Slf4j
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class CacheConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean
    @Override
    public CacheResolver cacheResolver() {
        // 通过caffeine实现的自定义堆内存缓存管理器
        // 优先读取堆内存缓存
        CaffeineCacheManager caffeineCacheManager = caffeineCacheManager();
        // 堆内存缓存读取不到该key时再读取redis缓存
        RedisCacheManager redisCacheManager = redisCacheManager(connectionFactory);
        return new MultipleCacheResolver(caffeineCacheManager, redisCacheManager);
    }

    @Bean
    public CaffeineCacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .initialCapacity(64)
                        .maximumSize(256)
                        .expireAfterAccess(600, TimeUnit.SECONDS)
                        .recordStats());
        cacheManager.setAllowNullValues(false);
        return cacheManager;
    }

    /**
     * 想要注解使用自定义配置cache Manager
     * @return CacheManager
     **/
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);

        // 配置序列化（解决乱码的问题）,过期时间3600秒
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer))
                .computePrefixWith(name -> name + ":")
                .disableCachingNullValues();
        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }

    /**
     * 配置API的序列化
     * @return RedisTemplate<Object>
     **/
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        template.setConnectionFactory(factory);

        //key序列化
        template.setKeySerializer(redisSerializer);
        template.setHashKeySerializer(redisSerializer);
        //value序列化
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * redis数据操作异常处理。该方法处理逻辑：在日志中打印出错误信息，但是放行。
     * 保证redis服务器出现连接等问题的时候不影响程序的正常运行
     */
    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                handleRedisErrorException(exception, key, "cachePutError");
            }

            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                handleRedisErrorException(exception, key, "cacheGetError");
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                handleRedisErrorException(exception, key, "cacheEvictError");
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                handleRedisErrorException(exception, null, "cacheClearError");
            }
        };
    }

    /**
     * @param exception .
     * @param key       .
     **/
    protected void handleRedisErrorException(RuntimeException exception, Object key, String reason) {
        log.error("redis异常：{},key=[{}]", reason, key, exception);
    }

}
