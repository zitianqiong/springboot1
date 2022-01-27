package pers.zitianqiong.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户表
 * @TableName customer
 */
@TableName(value = "customer")
@Data
public class Customer implements Serializable {
    /**
     * 用户id唯一
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 昵称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     *
     */
    private Integer valid;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 乐观锁版本
     */
    private Integer version;

    /**
     * 是否被删除0：正常，1：删除
     */
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
