package pers.zitianqiong.common;

/**
 * @author 丛吉钰
 * @version 1.0
 * @className FailResult
 * @date 2021/12/3
 */

public class FailResult<T> extends JsonResult<T> {
    private static final long serialVersionUID = -2945469778500843721L;

    public FailResult() {
        super(ERROR,FAIL_MSG);
    }

    public FailResult(String msg) {
        super(ERROR, msg);
    }
    public FailResult(String msg, Integer code){super(code, msg);}
}
