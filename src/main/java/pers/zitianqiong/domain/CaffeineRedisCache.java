package pers.zitianqiong.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractValueAdaptingCache;

import java.util.concurrent.Callable;

@Getter
@Setter
@Slf4j
public class CaffeineRedisCache extends AbstractValueAdaptingCache {

    /**
     * 一级缓存，若启用一级缓存则设置一个非空实例，反之则设置为null
     */
    protected Cache firstCache;

    /**
     * 二级缓存
     */
    protected Cache secondCache;

    /**
     * Create an {@code AbstractValueAdaptingCache} with the given setting.
     *
     * @param allowNullValues whether to allow for {@code null} values
     */
    public CaffeineRedisCache(boolean allowNullValues) {
        super(allowNullValues);
    }

    @Override
    protected Object lookup(Object key) {
        ValueWrapper valueWrapper = null;
        if (firstCache != null) {
            // 若启用了本地缓存则先从本地缓存查询
            log.info("从本地缓存获取：{}", key);
            valueWrapper = firstCache.get(key);
        }
        if (valueWrapper == null) {
            log.info("本地缓存未获取到数据");
            if (secondCache == null) {
                return null;
            }
            log.info("从二级缓存获取：{}", key);
            valueWrapper = secondCache.get(key);
            // 若二级缓存存在，但一级缓存被启用并且不存在key，则put到一级缓存
            if (valueWrapper != null) {
                if (firstCache != null) {
                    log.info("添加到一级缓存");
                    firstCache.put(key, valueWrapper.get());
                }
            } else {
                log.info("二级缓存未获取数据");
                return null;
            }
        }
        return valueWrapper.get();

    }

    @Override
    public String getName() {
        if (firstCache != null) {
            return firstCache.getName();
        }
        if (secondCache != null) {
            return secondCache.getName();
        }
        throw new RuntimeException("请提供有效的缓存.");
    }

    @Override
    public Object getNativeCache() {
        if (firstCache != null) {
            return firstCache.getNativeCache();
        }
        if (secondCache != null) {
            return secondCache.getNativeCache();
        }
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        if (firstCache != null) {
            T t = firstCache.get(key, valueLoader);
            if (t != null) {
                return t;
            }
        }
        if (secondCache != null) {
            return secondCache.get(key, valueLoader);
        }
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        if (firstCache != null) {
            firstCache.put(key, value);
        }
        if (secondCache != null) {
            secondCache.put(key, value);
        }
    }

    @Override
    public void evict(Object key) {
        if (firstCache != null) {
            firstCache.evict(key);
        }
        if (secondCache != null) {
            secondCache.evict(key);
        }
    }

    @Override
    public void clear() {
        if (firstCache != null) {
            firstCache.clear();
        }
        if (secondCache != null) {
            secondCache.clear();
        }
    }
}