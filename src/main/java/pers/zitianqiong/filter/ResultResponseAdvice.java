package pers.zitianqiong.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import pers.zitianqiong.common.ErrorResult;
import pers.zitianqiong.common.JsonResult;
import pers.zitianqiong.common.Result;
import pers.zitianqiong.common.SuccessResult;

/**
 * <p>描述：全局响应</p>
 *
 * @author 丛吉钰
 * @date 2022/10/31
 */
@RestControllerAdvice
public class ResultResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(final MethodParameter returnType, final Class<? extends HttpMessageConverter<?>> converterType) {
        return !(returnType.getGenericParameterType().equals(JsonResult.class) ||
                returnType.getGenericParameterType().equals(Result.class) ||
                returnType.getGenericParameterType().equals(ErrorResult.class));
    }

    @Override
    public Object beforeBodyWrite(final Object body, final MethodParameter returnType, final MediaType selectedContentType,
                                  final Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  final ServerHttpRequest request, final ServerHttpResponse response) {
        if (body == null || body instanceof JsonResult) {
            return body;
        }
        final SuccessResult<Object> result = new SuccessResult<>();
        result.setCode(200);
        result.setMsg("查询成功");
        result.setData(body);
        if (returnType.getGenericParameterType().equals(String.class)) {// 2
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(result);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("将 Response 对象序列化为 json 字符串时发生异常", e);
            }
        }
        return result;
    }
}
