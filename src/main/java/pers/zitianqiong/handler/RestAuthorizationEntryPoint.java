package pers.zitianqiong.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import pers.zitianqiong.common.ErrorResult;
import pers.zitianqiong.common.ResultCode;

/**
 * <p>描述：</p>
 *
 * @author 丛吉钰
 * @date 2023/1/9
 */
@Component
@Slf4j
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {
    
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)
            throws IOException{
        log.warn("401");
        //设置编码格式：ut-f8
        httpServletResponse.setCharacterEncoding("UTF-8");
        //Json格式
        httpServletResponse.setContentType("application/json");
        PrintWriter out = httpServletResponse.getWriter();
        ErrorResult result = ErrorResult.fail(ResultCode.NOT_LOGIN.getCode(), "尚未登陆，请登录");
        out.write(new ObjectMapper().writeValueAsString(result));
        out.flush();
        out.close();
    }
}
