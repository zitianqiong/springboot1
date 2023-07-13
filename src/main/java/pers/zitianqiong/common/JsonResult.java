package pers.zitianqiong.common;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 丛吉钰
 * @version 1.0
 * @className JsonResult
 * @date 2021/12/3
 * @param <T> 类型
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JsonResult<T> implements Serializable {
    private static final long serialVersionUID = -6960041611138493749L;
    protected static final int SUCCESS = 200;
    protected static final int ERROR = 500;
    protected static final String SUCCESS_MSG = "success";
    protected static final String FAIL_MSG = "error!";
    /**
     * 状态码
     */
    @JSONField(ordinal = 1)
    protected int code;
    
    @JSONField(ordinal = 2)
    protected String msg;
    
    @JSONField(ordinal = 3)
    protected Integer total;
    
    @JSONField(ordinal = 4)
    protected List<T> rows;
    
    @JSONField(ordinal = 4)
    protected T data;
    
    public JsonResult() {
    }
    
    public JsonResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    
    public JsonResult(int code, String msg, Integer total, List<T> rows) {
        this.code = code;
        this.msg = msg;
        this.total = total;
        this.rows = rows;
    }
    
    public JsonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public JsonResult(boolean flag) {
        this.msg = flag ? SUCCESS_MSG : FAIL_MSG;
        this.code = flag ? SUCCESS : ERROR;
    }
    
}
