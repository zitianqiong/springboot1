package pers.zitianqiong.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.zitianqiong.common.ErrorResult;
import pers.zitianqiong.common.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * <p>描述：</p>
 *
 * @author: 丛吉钰
 * @date: 2022/7/7
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {
    
    /**
     * 处理空指针的异常
     */
    @ExceptionHandler(NullPointerException.class)
    public ErrorResult exceptionHandler(HttpServletRequest req, NullPointerException e) {
        ErrorResult error = ErrorResult.fail(ResultCode.SYSTEM_EXCEPTION, e);
        log.error("URL:{} ,发生空指针异常！原因是:", req.getRequestURI(), e);
        return error;
    }
    
    @ExceptionHandler(BindException.class)
    public ErrorResult exceptionHandler(BindException e, HttpServletRequest request) {
        String failMsg = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        ErrorResult error = ErrorResult.fail(ResultCode.SYSTEM_EXCEPTION, e, failMsg);
        log.error("URL:{} ,绑定异常:{} ", request.getRequestURI(), failMsg);
        return error;
    }
    
    /**
     *
     * @param request 请求
     * @param e       异常
     * @return 响应
     */
    @ExceptionHandler(Exception.class)
    public ErrorResult exceptionHandler(HttpServletRequest request, Exception e) {
        ErrorResult error = ErrorResult.fail(ResultCode.SYSTEM_EXCEPTION, e);
        log.error("URL:{},发生异常！异常类型:", request.getRequestURI(), e);
        return error;
    }
    
    /**
     * 系统异常处理
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult exception(HttpServletRequest request, Throwable throwable) {
        log.error("URL:{},系统异常", request.getRequestURI(), throwable);
        return ErrorResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统异常，请联系管理员！");
    }
    
}
