package pers.zitianqiong.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.zitianqiong.domain.Authority;
import pers.zitianqiong.domain.Customer;
import pers.zitianqiong.domain.CustomerAuthority;
import pers.zitianqiong.mapper.CustomerMapper;
import pers.zitianqiong.service.AuthorityService;
import pers.zitianqiong.service.CustomerAuthorityService;
import pers.zitianqiong.service.CustomerService;

/**
 *
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer>
        implements CustomerService {

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private CustomerAuthorityService customerAuthorityService;

    @Override
    public Customer getCustomer(String username) {
        return getOne(new LambdaQueryWrapper<Customer>().eq(Customer :: getUsername, username));
    }

    @Override
    public List<Authority> getCustomerAuthoritiy(String username) {
        Customer customer = getOne(new LambdaQueryWrapper<Customer>().eq(Customer :: getUsername, username));
        QueryWrapper<CustomerAuthority> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("authority_id",customer.getId());
        List<CustomerAuthority> customerAuthorities = customerAuthorityService.list(queryWrapper);
//                new LambdaQueryWrapper<CustomerAuthority>().eq(CustomerAuthority :: getCustomer_id, customer.getId()));
        List<Integer> authoritiesId =
                customerAuthorities.stream().map(CustomerAuthority :: getAuthorityId).collect(Collectors.toList());
        List<Authority> authorities = authorityService.listByIds(authoritiesId);
        authorities.forEach(System.out :: println);
        return authorities;
    }
}
