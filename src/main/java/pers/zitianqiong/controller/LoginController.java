package pers.zitianqiong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.zitianqiong.common.Result;
import pers.zitianqiong.domain.AdminLoginParam;
import pers.zitianqiong.domain.Customer;
import pers.zitianqiong.service.CustomerService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Calendar;

/**
 * <p>描述：</p>
 *
 * @author 丛吉钰
 * @date 2022/11/2
 */
@Controller
public class LoginController {
    //注入service
    @Autowired
    private CustomerService customerService;
    
    /**
     * @param model .
     * @return String
     **/
    @GetMapping("/toLoginPage")
    public String toLoginPage(String param, Model model) {
        if ("error".equals(param)){
            return "error";
        }
        model.addAttribute("currentYear", Calendar.getInstance().get(Calendar.YEAR));
        return "login";
    }
    
    /**
     * 登录之后返回token
     */
    @PostMapping("/login")
    public String login(AdminLoginParam adminLoginParam, HttpServletRequest request){
        //service层login登录方法
        return customerService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),request);
    }
    
    @GetMapping("/customer/info")
    @ResponseBody
    public Customer getAdminInfo(Principal principal) {
        if (null == principal) {
            return null;
        }
        String username = principal.getName();
        Customer customer = customerService.getCustomer(username);
        customer.setPassword(null);
        return customer;
    }
    
    @PostMapping("/mylogout")
    @ResponseBody
    public Result logout() {
        return Result.success();
    }
}
