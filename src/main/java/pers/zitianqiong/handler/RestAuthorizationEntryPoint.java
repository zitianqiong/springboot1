package pers.zitianqiong.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import pers.zitianqiong.common.Result;
import pers.zitianqiong.common.ResultCode;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>描述：spring security 401未登录处理</p>
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
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter out = httpServletResponse.getWriter();
        Result result = Result.fail(ResultCode.NOT_LOGIN.getCode(), "尚未登陆，请登录");
        out.write(new ObjectMapper().writeValueAsString(result));
        out.flush();
        out.close();
    }
}
