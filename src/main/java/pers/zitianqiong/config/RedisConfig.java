package pers.zitianqiong.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import pers.zitianqiong.utils.FastJson2JsonRedisSerializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>redis 配置</p>
 * @author 丛吉钰
 */
@Configuration
@EnableCaching
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Override
    public CacheResolver cacheResolver() {
        // 通过caffeine实现的自定义堆内存缓存管理器
        CacheManager caffeineCacheManager = caffeineCacheManager();
        CacheManager redisCacheManager = redisCacheManager(connectionFactory);
        List<CacheManager> list = new ArrayList<>();
        // 优先读取堆内存缓存
        list.add(caffeineCacheManager);
//         堆内存缓存读取不到该key时再读取redis缓存
        list.add(redisCacheManager);
        return new CustomCacheResolver(list);
    }

    @Bean
    public CacheManager caffeineCacheManager() {
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
     * 配置API的序列化
     * @return RedisTemplate<Object>
     **/
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        FastJson2JsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJson2JsonRedisSerializer<>(Object.class);
        template.setConnectionFactory(factory);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        fastJsonRedisSerializer.setObjectMapper(mapper);

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
     * 想要注解使用自定义配置cache Manager
     * @return CacheManager
     **/
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        FastJson2JsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJson2JsonRedisSerializer<>(Object.class);

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
