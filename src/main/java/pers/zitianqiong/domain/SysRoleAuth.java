package pers.zitianqiong.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @TableName sys_role_auth
 */
@TableName(value ="sys_role_auth")
@Data
public class SysRoleAuth implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    @TableField(value = "role_id")
    private Integer roleId;

    /**
     *
     */
    @TableField(value = "authority_id")
    private Integer authorityId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}