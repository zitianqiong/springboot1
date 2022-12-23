package pers.zitianqiong.config;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>redis 配置</p>
 * @author 丛吉钰
 */
@Configuration
@EnableCaching
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 配置API的序列化
     * @param factory redis工厂
     * @return RedisTemplate<Object>
     **/
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jacksonSeial = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jacksonSeial.setObjectMapper(om);
        template.setConnectionFactory(factory);
        //设置模板为jackson
        //template.setDefaultSerializer(jacksonSeial);
        //key序列化方式
        template.setKeySerializer(redisSerializer);
        //value序列化
        template.setValueSerializer(jacksonSeial);
        template.setHashKeySerializer(jacksonSeial);
        //value hashmap序列化
        template.setHashValueSerializer(jacksonSeial);
        return template;
    }

    /**
     * 想要注解使用自定义配置cache Manager
     * @param factory redis工厂
     * @return CacheManager
     **/
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jacksonSeial = new Jackson2JsonRedisSerializer<>(Object.class);
        //解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jacksonSeial.setObjectMapper(om);
        // 配置序列化（解决乱码的问题）,过期时间3600秒
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jacksonSeial))
                .computePrefixWith(name -> name + ":")
                .disableCachingNullValues();
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
        return cacheManager;
    }

    /**
     * redis数据操作异常处理。该方法处理逻辑：在日志中打印出错误信息，但是放行。
     * 保证redis服务器出现连接等问题的时候不影响程序的正常运行
     */
    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache,
                                            Object key, Object value) {
                handleRedisErrorException(exception, key);
            }

            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache,
                                            Object key) {
                handleRedisErrorException(exception, key);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache,
                                              Object key) {
                handleRedisErrorException(exception, key);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                handleRedisErrorException(exception, null);
            }
        };
    }

    /**
     * @param exception .
     * @param key       .
     **/
    protected void handleRedisErrorException(RuntimeException exception, Object key) {
        log.error("redis异常：key=[{}]", key, exception);
    }

}
