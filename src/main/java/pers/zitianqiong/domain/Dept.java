package pers.zitianqiong.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部门
 *
 * @TableName dept
 */
@TableName(value = "dept")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dept implements Serializable {
    /**
     * 部门id
     */
    @TableId(type = IdType.AUTO)
    private Integer deptId;
    
    /**
     * 部门名称
     */
    private String deptName;
    
    /**
     * 部门资金
     */
    private BigDecimal deptAccont;
    
    /**
     * 状态
     */
    private status stuts;
    
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
