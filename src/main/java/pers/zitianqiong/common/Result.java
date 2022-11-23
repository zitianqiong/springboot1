package pers.zitianqiong.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;

/**
 * 数据成功响应
 */
@Data
public class Result implements Serializable {
    private static final long serialVersionUID = -4505655308965878999L;
    
    private Integer code;
    
    private String message;
    
    private String date = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    
    private Integer count;
    
    private Object data;
    
    private Result() {
    }
    
    public Result(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMsg();
        this.data = data;
    }
    
    /**
     * 设置返回代码
     * @param resultCode 代码
     */
    private void setResultCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMsg();
    }
    
    /**
     * 返回成功
     * @return 响应
     */
    public static Result success() {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }
    
    /**
     * 返回成功
     * @param data 响应数据
     * @return 响应
     */
    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }
    
    /**
     * 返回分页数据
     * @param count 总数
     * @param data 数据列表
     * @return 响应
     */
    public static Result success(Integer count, Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setCount(count);
        result.setData(data);
        return result;
    }
    
    /**
     * 返回成功
     * @param code 总数
     * @param msg 消息
     * @param data 数据列表
     * @return 响应
     */
    public static Result success(Integer code, String msg, Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }
    
    /**
     * 返回失败
     * @param code 总数
     * @param message 消息
     * @return 响应
     */
    public static Result fail(Integer code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    
    /**
     * 返回失败
     * @param resultCode 状态代码
     * @return 响应
     */
    public static Result fail(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }
    
}
