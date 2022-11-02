package pers.zitianqiong.common;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private void setResultCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMsg();
    }

    /** 返回成功
     * @Description
     * @param
     */
    public static Result success() {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }
    /** 返回成功
     * @Description
     * @param
     */
    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /** 返回成功
     * @Description
     * @param
     */
    public static Result success(Integer count, Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setCount(count);
        result.setData(data);
        return result;
    }
    
    /** 返回成功
     * @Description
     * @param
     */
    public static Result success(Integer code, String msg, Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /** 返回失败
     * @Description
     * @param
     */
    public static Result fail(Integer code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /** 返回失败
     * @Description
     * @param
     */
    public static Result fail(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

}
