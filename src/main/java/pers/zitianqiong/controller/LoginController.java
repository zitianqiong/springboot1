package pers.zitianqiong.controller;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.zitianqiong.common.JsonResult;
import pers.zitianqiong.domain.Customer;
import pers.zitianqiong.domain.LoginParam;
import pers.zitianqiong.service.CustomerService;

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
    private final CustomerService customerService;
    private final UserDetailsService userDetailsService;
    
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
        return customerService.login(loginParam.getUsername(),
                loginParam.getPassword(),
                loginParam.getCode(),
                request);
    }
    
    /**
     * 用户信息
     * @param principal 当前用户
     * @return 响应
     */
    @GetMapping("/customer/info")
    @ResponseBody
    public Customer getAdminInfo(Principal principal) {
        String username = principal.getName();
        Customer customer = (Customer) userDetailsService.loadUserByUsername(username);
        customer.setPassword(null);
        return customer;
    }
    
}
