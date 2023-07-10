package pers.zitianqiong.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <p>描述：</p>
 *
 * @author 丛吉钰
 * @date 2023/1/9
 */
@Slf4j
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {
    
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> clazz;

    /*
     *  添加autotype白名单
     *  解决redis反序列化对象时报错 ：com.alibaba.fastjson.JSONException: autoType is not support
     */
    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        ParserConfig.getGlobalInstance().addAccept("pers.zitianqiong.domain.***");
    }
    
    public FastJson2JsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }
    
    /**
     * 序列化
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (null == t) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }
    
    /**
     * 反序列化
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException  {
        if (null == bytes || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return (T) JSON.parseObject(str, clazz);
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "'objectMapper' must not be null");
        this.objectMapper = objectMapper;
    }

    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }
}
