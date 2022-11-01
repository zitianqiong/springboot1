package pers.zitianqiong.common;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author 丛吉钰
 * @version 1.0
 * @className SuccessResult
 * @date 2021/12/3
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SuccessResult<T> extends JsonResult<T> {
    private static final long serialVersionUID = -2079502052403065930L;


    public SuccessResult() {
        super(SUCCESS, SUCCESS_MSG);
    }

    public SuccessResult(String msg) {
        super(SUCCESS, msg);
    }

    public SuccessResult(String msg, T data) {
        super(SUCCESS, msg, data);
    }

    public SuccessResult(T data) {
        super(SUCCESS, SUCCESS_MSG, data);
    }

    public SuccessResult(List<T> rows, Integer total) {
        super(SUCCESS, SUCCESS_MSG, total, rows);
    }
    public SuccessResult(List<T> rows, Integer total,String msg) {
        super(SUCCESS, msg, total, rows);
    }

}
