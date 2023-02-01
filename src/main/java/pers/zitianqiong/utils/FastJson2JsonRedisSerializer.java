package pers.zitianqiong.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * <p>描述：</p>
 *
 * @author 丛吉钰
 * @date 2023/1/9
 */
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {
    
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    
    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }
    
    private final Class<T> clazz;
    
    public FastJson2JsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }
    
    /**
     * 序列化
     */
    @Override
    public byte[] serialize(T t) {
        if (null == t) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }
    
    /**
     * 反序列化
     */
    @Override
    public T deserialize(byte[] bytes) {
        if (null == bytes || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return (T) JSON.parseObject(str, clazz);
    }
}
