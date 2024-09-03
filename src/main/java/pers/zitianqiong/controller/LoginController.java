package pers.zitianqiong.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.zitianqiong.common.deprecated.JsonResult;
import pers.zitianqiong.domain.param.LoginParam;
import pers.zitianqiong.service.ISysUserService;

/**
 * <p>描述：</p>
 *
 * @author 丛吉钰
 * @date 2022/11/2
 */
@Controller
@AllArgsConstructor
public class LoginController {
    //注入service
    private final ISysUserService userService;

    /**
     * 登录之后返回token
     * @param loginParam 参数
     * @param request 请求
     * @return 响应
     */
    @PostMapping("/login")
    @ResponseBody
    public JsonResult<?> login(@RequestBody LoginParam loginParam,
                               HttpServletRequest request) {
        //service层login登录方法
        return userService.login(loginParam.getUsername(),
                loginParam.getPassword(),
                loginParam.getCode(),
                request);
    }

}
