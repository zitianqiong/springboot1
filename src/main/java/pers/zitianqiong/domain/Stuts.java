package pers.zitianqiong.domain;

import lombok.Getter;

/**
 * 数据状态枚举类
 */
@Getter
public enum Stuts {
    /**
     * 正常
     */
    NORMAL(1),
    /**
     * 停用
     */
    DEACTIVATE(2);
    
    private final Integer value;
    
    Stuts(Integer value) {
        this.value = value;
    }
}
