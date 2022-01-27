package pers.zitianqiong.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName customer_authority
 */
@TableName(value = "customer_authority")
@Data
public class CustomerAuthority implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    @TableField(value = "customer_id")
    private Integer customerId;

    /**
     *
     */
    @TableField(value = "authority_id")
    private Integer authorityId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
