package pers.zitianqiong.common;

import java.io.Serializable;

import lombok.Data;

/**
 * 系统错误响应体
 */
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
    
    /**
     * 失败
     * @return 响应
     */
    public static ErrorResult fail() {
        ErrorResult errorResult = new ErrorResult();
        errorResult.setCode(ResultCode.FAILURE.getCode());
        errorResult.setMsg(ResultCode.FAILURE.getMsg());
        return errorResult;
    }
    
    /**
     * 失败
     * @param resultCode 响应代码
     * @param e 异常
     * @param message 消息
     * @return 响应
     */
    public static ErrorResult fail(ResultCode resultCode, Throwable e, String message) {
        ErrorResult errorResult = ErrorResult.fail(resultCode, e);
        errorResult.setMsg(message);
        return errorResult;
    }
    
    /**
     * 失败
     * @param resultCode 响应代码
     * @param e 异常
     * @return 响应
     */
    public static ErrorResult fail(ResultCode resultCode, Throwable e) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.setCode(resultCode.getCode());
        errorResult.setMsg(resultCode.getMsg());
        errorResult.setException(e.getClass().getName());
        return errorResult;
    }
    
    /**
     * 失败
     * @param code 响应代码
     * @param message 消息
     * @return 响应
     */
    public static ErrorResult fail(Integer code, String message) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.setCode(code);
        errorResult.setMsg(message);
        return errorResult;
    }
    
    /**
     * 失败
     * @param e 异常
     * @param message 消息
     * @return 响应
     */
    public static ErrorResult fail(String message, Throwable e) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.setCode(ResultCode.FAILURE.getCode());
        errorResult.setMsg(message);
        errorResult.setException(e.getClass().getName());
        return errorResult;
    }
    
    /**
     * 失败
     * @param message 消息
     * @return 响应
     */
    public static ErrorResult fail(String message) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.setCode(ResultCode.FAILURE.getCode());
        errorResult.setMsg(message);
        return errorResult;
    }
    
}
