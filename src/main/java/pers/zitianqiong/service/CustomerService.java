package pers.zitianqiong.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.zitianqiong.common.JsonResult;
import pers.zitianqiong.domain.Authority;
import pers.zitianqiong.domain.Customer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 */
public interface CustomerService extends IService<Customer> {

    Customer getCustomer(String username);

    List<Authority> getCustomerAuthority(String username);
    
    /**
     * 登录之后，返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    JsonResult<?> login(String username, String password, String code, HttpServletRequest request);
}
