package pers.zitianqiong.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.MapUtils;
import pers.zitianqiong.utils.StringUtils;

/**
 * <p>描述：枚举命名空间</p>
 *
 * @author 丛吉钰
 * @date 2022/12/5
 */
public class EnumContext {
    
    /**
     * 枚举集合映射
     */
    private static final Map<String, Map<String, BaseEnum>> ENUM_MAP = new ConcurrentHashMap<>();
    
    /**
     * 初始化枚举map
     */
    private synchronized static <T extends BaseEnum> void initEnumMap(Class<T> enumClass) {
        if (enumClass != null && enumClass.isEnum()) {
            String key = enumClass.getName();
            if (!ENUM_MAP.containsKey(key)) {
                Map<String, BaseEnum> map = new ConcurrentHashMap<>(16);
                BaseEnum[] baseEnums = enumClass.getEnumConstants();
                if (baseEnums != null) {
                    for (final BaseEnum e : baseEnums) {
                        map.put(String.valueOf(e.getCode()), e);
                    }
                }
                ENUM_MAP.put(key, map);
            }
        }
    }
    
    /**
     * 通过代码获取枚举（时间复杂度O(1)）
     *
     * @param code      代码
     * @param enumClass 枚举类
     * @return 枚举
     */
    public static <T extends BaseEnum> T getEnumByCode(String code, Class<T> enumClass) {
        if (StringUtils.isNotEmpty(code) && enumClass != null) {
            String key = enumClass.getName();
            if (!ENUM_MAP.containsKey(key)) {
                //如果不存在=>初始化
                initEnumMap(enumClass);
            }
            //如果已存在=>判断
            Map<String, BaseEnum> map = ENUM_MAP.get(key);
            if (MapUtils.isNotEmpty(map)) {
                return (T) map.get(code);
            }
        }
        return null;
    }
    
    /**
     * 通过名称获取枚举（时间复杂度O(n)）
     *
     * @param name      代码
     * @param enumClass 枚举类
     * @return 代码
     */
    public static <T extends BaseEnum> T getEnumCodeByName(String name, Class<T> enumClass) {
        if (StringUtils.isNotEmpty(name) && enumClass != null) {
            String key = enumClass.getName();
            if (!ENUM_MAP.containsKey(key)) {
                //如果不存在=>初始化
                initEnumMap(enumClass);
            }
            //如果已存在=>判断
            Map<String, BaseEnum> map = ENUM_MAP.get(key);
            if (MapUtils.isNotEmpty(map)) {
                for (Map.Entry<String, BaseEnum> entry : map.entrySet()) {
                    if (entry.getValue() != null && name.equals(entry.getValue().getName())) {
                        return (T) entry.getValue();
                    }
                }
            }
        }
        return null;
    }
}
