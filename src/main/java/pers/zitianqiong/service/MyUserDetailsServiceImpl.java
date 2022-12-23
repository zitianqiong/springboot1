package pers.zitianqiong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pers.zitianqiong.domain.Customer;

/**
 * <p>描述：</p>
 *
 * @author 丛吉钰
 * @date 2022/12/23
 */
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Lazy
    @Autowired
    private CustomerService customerService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerService.getCustomer(username);
        if (customer != null) {
            customer.setRoles(customerService.getCustomerAuthority(username));
            return customer;
        } else {
            throw new UsernameNotFoundException("当前用户不存在");
        }
    };
}
