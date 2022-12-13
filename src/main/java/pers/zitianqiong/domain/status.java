package pers.zitianqiong.domain;

import lombok.Getter;

/**
 * 数据状态枚举类
 * @author zitianqiong
 */
@Getter
public enum status {
    /**
     * 正常
     */
    NORMAL(1),
    /**
     * 停用
     */
    DEACTIVATE(2);
    
    private final Integer value;
    
    status(Integer value) {
        this.value = value;
    }
}
