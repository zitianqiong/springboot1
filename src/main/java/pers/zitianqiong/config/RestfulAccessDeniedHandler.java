package pers.zitianqiong.config;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import pers.zitianqiong.common.ErrorResult;
import pers.zitianqiong.common.ResultCode;

/**
 * 当访问接口没有权限时，自定义返回对象
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        PrintWriter out = httpServletResponse.getWriter();
        ErrorResult errorResult = ErrorResult.fail(ResultCode.LACK_OF_AUTHORITY.getCode(), "权限不足，请联系管理员");
        out.write(new ObjectMapper().writeValueAsString(errorResult));
        out.flush();
        out.close();
    }
}
