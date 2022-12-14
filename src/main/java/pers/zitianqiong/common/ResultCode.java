package pers.zitianqiong.common;

import lombok.AllArgsConstructor;

/**
 * 状态代码
 */
@AllArgsConstructor
public enum ResultCode implements BaseEnum{
    
    /**
     * 成功
     **/
    SUCCESS(200, "成功"),
    /**
     * 失败
     **/
    FAILURE(500, "失败"),
    
    EXCEPTION(201, "未知异常"),
    RUNTIME_EXCEPTION(202, "运行时异常"),
    NULL_POINTER_EXCEPTION(203, "空指针异常"),
    CLASS_CAST_EXCEPTION(204, "类型转换异常"),
    IO_EXCEPTION(205, "IO异常"),
    SYSTEM_EXCEPTION(210, "系统异常"),
    NOT_LOGIN(401, "未登录"),
    LACK_OF_AUTHORITY(403, "无权限"),
    NOT_FOUND(404, "Not Found"),
    
    /**
     * 1000～1999 区间表示参数错误
     */
    PARAMS_IS_INVALID(1001, "参数无效"),
    PARAMS_IS_BANK(1002, "参数为空"),
    PARAMS_TYPE_BIND_ERROR(1003, "参数类型错误"),
    PARAMS_NOT_COMPLETE(1004, "参数缺失"),
    
    /**
     * 2000～2999 区间表示用户错误
     */
    USER_NOT_LOGGED_IN(2001, "用户未登录，访问路径需要验证"),
    USER_NOT_LOGIN_ERROR(2002, "用户不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(2003, "用户被禁用"),
    USER_NOT_EXIST(2004, "用户不存在"),
    USER_HAS_EXISTED(2005, "用户已存在"),
    USER_IS_EXPIRED(2006, "用户账号已过期"),
    USER_FIRST_LANDING(2007, "首次登录"),
    USER_TOKEN_EXPIRED(2008, "Token过期"),
    USER_TOKEN_GENERTATION_FAIL(2009, "生成Token失败"),
    USER_SIGN_VERIFI_NOT_COMPLIANT(2010, "签名校验不合规"),
    USER_PASSWORD_RESET_FAILED(2011, "重置密码失败"),
    USER_UNKONWN_INDENTITY(2012, "未知身份"),
    MANY_USER_LOGINS(2111, "多用户在线"),
    TOO_MANY_PASSWD_ENTER(2112, "密码输入次数过多"),
    VERIFICATION_CODE_INCORECT(2202, "图形验证码不正确"),
    VERIFICATION_CODE_FAIL(2203, "图形验证码生产失败"),
    
    /**
     * 3000～3999 区间表示接口异常
     */
    API_EXCEPTION(3000, "接口异常"),
    API_NOT_FOUND_EXCEPTION(3002, "接口不存在"),
    API_REQ_MORE_THAN_SET(3003, "接口访问过于频繁，请稍后再试"),
    API_IDEMPOTENT_EXCEPTION(3004, "接口不可以重复提交，请稍后再试"),
    API_PARAM_EXCEPTION(3005, "参数异常"),
    API_PARAM_MISSING_EXCEPTION(3006, "缺少参数"),
    API_METHOD_NOT_SUPPORTED_EXCEPTION(3007, "不支持的Method类型"),
    API_METHOD_PARAM_TYPE_EXCEPTIION(3008, "参数类型不匹配"),
    
    ARRAY_EXCEPTION(11001, "数组异常"),
    ARRAY_OUT_OF_BOUNDS_EXCEPTION(11002, "数组越界异常"),
    
    JSON_SERIALIZE_EXCEPTION(30000, "序列化数据异常"),
    JSON_DESERIALIZE_EXCEPTION(30001, "反序列化数据异常"),
    
    READ_RESOURSE_EXCEPTION(31002, "读取资源异常"),
    READ_RESOURSE_NOT_FOUND_EXCEPTION(31003, "资源不存在异常"),
    
    DATA_EXCEPTION(32004, "数据异常"),
    DATA_NOT_FOUND_EXCEPTION(32005, "未找到符合条件的数据异常"),
    DATA_CALCULATION_EXCEPTION(32006, "数据计算异常"),
    DATA_COMPRESS_EXCEPTION(32007, "数据压缩异常"),
    DATA_DE_COMPRESS_EXCEPTION(32008, "数据解压缩异常"),
    DATA_PARSE_EXCEPTION(32009, "数据转换异常"),
    
    ENCODING_EXCEPTION(33006, "编码异常"),
    ENCODING_UNSUPPORTED_EXCEPTION(33006, "编码不支持异常"),
    
    DATE_PARSE_EXCEPTION(34001, "日期转换异常"),
    
    MAILE_SEND_EXCEPTION(35001, "邮件发送异常");
    
    private final int code;
    private final String msg;
    
    @Override
    public Integer getCode() {
        return code;
    }
    
    @Override
    public String getName() {
        return msg;
    }
    
    public String getMsg(){
        return msg;
    }
}
