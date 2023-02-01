package pers.zitianqiong.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        log.warn("403");
        //1.设置状态码403
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        //3.相应的请求头
        response.setHeader("Content-Type","application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        ErrorResult errorResult = ErrorResult.fail(ResultCode.LACK_OF_AUTHORITY.getCode(), "权限不足，请联系管理员");
        out.write(new ObjectMapper().writeValueAsString(errorResult));
        out.flush();
        out.close();
    }
}
