package pers.zitianqiong.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName authority
 */
@TableName(value = "authority")
@Data
public class Authority implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    /**
     * 权限
     */
    @TableField(value = "authority")
    private String authority;
    
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
