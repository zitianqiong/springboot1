package pers.zitianqiong.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorResult implements Serializable {

    private static final long serialVersionUID = -4505655308965878999L;

    /**
     * 错误编码
     **/
    private Integer code;
    /**
     * 消息描述
     **/
    private String msg;
    /**
     * 错误
     **/
    private String exception;

    public static ErrorResult fail(ResultCode resultCode, Throwable e, String message) {
        ErrorResult errorResult = ErrorResult.fail(resultCode, e);
        errorResult.setMsg(message);
        return errorResult;
    }

    public static ErrorResult fail(ResultCode resultCode, Throwable e) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.setCode(resultCode.getCode());
        errorResult.setMsg(resultCode.getMsg());
        errorResult.setException(e.getClass().getName());
        return errorResult;
    }

    public static ErrorResult fail(Integer code, String message) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.setCode(code);
        errorResult.setMsg(message);
        return errorResult;
    }

}
