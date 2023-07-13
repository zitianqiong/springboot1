package pers.zitianqiong.vo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.zitianqiong.domain.status;

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
public class DeptVO {
    
    private Integer deptId;
    @NotNull
    private String deptName;
    @DecimalMin("0.1")
    private BigDecimal deptAccont;
    /**
     * 状态
     */
    private status stuts;
}
