package pers.zitianqiong.config;

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

//当未登录或token失效时访问接口，自定义的返回结果

@Component
@Slf4j
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {
        log.warn("401");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        PrintWriter out = httpServletResponse.getWriter();
        ErrorResult result = ErrorResult.fail(ResultCode.NOT_LOGIN.getCode(), "尚未登陆，请登录");
        result.setCode(401);
        out.write(new ObjectMapper().writeValueAsString(result));
        out.flush();
        out.close();
    }
}
