package pers.zitianqiong.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.zitianqiong.common.JsonResult;
import pers.zitianqiong.domain.Authority;
import pers.zitianqiong.domain.Customer;

/**
 * 用户服务层
 */
public interface CustomerService extends IService<Customer> {
    
    /**
     * 获得用户
     * @param username 用户名
     * @return 用户信息
     */
    Customer getCustomer(String username);
    
    /**
     * 用户权限
     * @param username 用户名
     * @return 用户权限
     */
    List<Authority> getCustomerAuthority(String username);
    
    /**
     * 登录之后，返回token
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param request 请求
     * @return 响应
     */
    JsonResult<?> login(String username, String password, String code, HttpServletRequest request);
}
