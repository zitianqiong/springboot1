package pers.zitianqiong.advice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pers.zitianqiong.common.ErrorResult;
import pers.zitianqiong.common.ResultCode;


/**
 * <p>描述：</p>
 *
 * @author: 丛吉钰
 * @date: 2022/7/7
 */
@RestControllerAdvice(basePackages = "pers.zitianqiong.controller")
@Slf4j
public class GlobalExceptionAdvice {
    
    /**
     * 用户未找到异常
     *
     * @param e   sql异常
     * @return 错误返回体
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorResult handleUsernameNotFoundException(SQLException e) {
        return ErrorResult.fail("用户不存在", e);
    }
    
    /**
     * sql异常
     *
     * @param req 请求
     * @param e   sql异常
     * @return 错误返回体
     */
    @ExceptionHandler(SQLException.class)
    public ErrorResult mySqlException(HttpServletRequest req, SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            log.error("URL:{} ,该数据有关联数据，操作失败, 异常名:", req.getRequestURI(), e);
            return ErrorResult.fail("该数据有关联数据，操作失败", e);
        }
        log.error("URL:{} ,数据库异常，操作失败！异常名:", req.getRequestURI(), e);
        return ErrorResult.fail("数据库异常，操作失败", e);
    }
    
    /**
     * 处理空指针的异常
     *
     * @param req 请求
     * @param e 空指针异常
     * @return 错误返回体
     */
    @ExceptionHandler(NullPointerException.class)
    public ErrorResult exceptionHandler(HttpServletRequest req, NullPointerException e) {
        ErrorResult error = ErrorResult.fail(ResultCode.NULL_POINTER_EXCEPTION, e);
        log.error("URL:{} ,发生空指针异常！异常名:", req.getRequestURI(), e);
        return error;
    }
    
    /**
     * 绑定异常
     *
     * @param req 请求
     * @param e 异常
     * @return 错误返回体
     */
    @ExceptionHandler(BindException.class)
    public ErrorResult exceptionHandler(HttpServletRequest req, BindException e) {
        String failMsg = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        ErrorResult error = ErrorResult.fail(ResultCode.SYSTEM_EXCEPTION, e, failMsg);
        log.error("URL:{} ,绑定异常:{} ", req.getRequestURI(), failMsg);
        return error;
    }
    
    /**
     * @param request 请求
     * @param e       异常
     * @return 响应
     */
    @ExceptionHandler(Exception.class)
    public ErrorResult exceptionHandler(HttpServletRequest request, Exception e) {
        ErrorResult error = ErrorResult.fail(ResultCode.SYSTEM_EXCEPTION, e);
        log.error("URL:{},发生异常！异常名:", request.getRequestURI(), e);
        return error;
    }
    
    /**
     * 系统异常处理
     *
     * @param request 请求
     * @param throwable 异常
     * @return 错误返回体
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult exception(HttpServletRequest request, Throwable throwable) {
        log.error("URL:{},系统异常", request.getRequestURI(), throwable);
        return ErrorResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统异常，请联系管理员！");
    }
    
}
