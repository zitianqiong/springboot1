package pers.zitianqiong.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import pers.zitianqiong.common.Result;

import java.io.IOException;

/**
 * <p>描述：</p>
 *
 * @author 丛吉钰
 * @date 2022/7/12
 */
@Slf4j
@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    //在application配置文件中配置登陆的类型是JSON数据响应还是做页面响应
    @Value("${spring.security.loginType}")
    private String loginType;

    private  static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        if ("JSON".equalsIgnoreCase(loginType)) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            if (exception instanceof BadCredentialsException){
                response.getWriter().write(
                        objectMapper.writeValueAsString(
                                Result.fail("用户名或密码错误")));
            }else {
                response.getWriter().write(
                        objectMapper.writeValueAsString(
                                Result.fail("登陆失败")));
            }
        } else {
            /* 默认：执行重定向或转发到defaultfailureurl(如果设置)，Otherw返回401错误代码 */
            response.setContentType(MediaType.TEXT_HTML_VALUE);
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
