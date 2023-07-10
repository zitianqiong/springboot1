package pers.zitianqiong.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

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

    /**
     * 状态
     */
    private LocalDate datestate;
    
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
