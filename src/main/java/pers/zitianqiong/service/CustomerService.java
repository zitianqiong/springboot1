package pers.zitianqiong.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.zitianqiong.domain.Authority;
import pers.zitianqiong.domain.Customer;

/**
 *
 */
public interface CustomerService extends IService<Customer> {

    Customer getCustomer(String username);

    List<Authority> getCustomerAuthoritiy(String username);
}
