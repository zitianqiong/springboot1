package pers.zitianqiong.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.zitianqiong.domain.Stuts;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p>描述：</p>
 *
 * @author: 丛吉钰
 * @date: 2022/7/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptVO{
    
    private Integer deptId;
    @NotNull
    private String deptName;
    @DecimalMin("0.1")
    private BigDecimal deptAccont;
    /**
     * 状态
     */
    private Stuts stuts;
}
