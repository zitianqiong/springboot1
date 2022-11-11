package pers.zitianqiong.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pers.zitianqiong.common.FailResult;
import pers.zitianqiong.common.JsonResult;
import pers.zitianqiong.common.SuccessResult;
import pers.zitianqiong.domain.Authority;
import pers.zitianqiong.domain.Customer;
import pers.zitianqiong.domain.CustomerAuthority;
import pers.zitianqiong.mapper.CustomerMapper;
import pers.zitianqiong.service.AuthorityService;
import pers.zitianqiong.service.CustomerAuthorityService;
import pers.zitianqiong.service.CustomerService;
import pers.zitianqiong.utils.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    //将配置文件中存的值取过来
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    
    
    @Override
    public Customer getCustomer(String username) {
        return getOne(new LambdaQueryWrapper<Customer>().eq(Customer :: getUsername, username)
                .eq(Customer::isEnabled,true));
    }

    @Override
    public List<Authority> getCustomerAuthority(String username) {
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
    
    /**
     * 登录之后返回token
     *
     * @param username 用户名
     * @param password 密码
     * @param request 请求
     * @return 响应
     */
    @Override
    public JsonResult<?> login(String username, String password, String code, HttpServletRequest request) {
        String captcha = (String) request.getSession().getAttribute("captcha");
        if(StringUtils.isEmpty(code)||!captcha.equalsIgnoreCase(code)){
            return new FailResult<>("验证码不正确，请重新输入");
        }
        //security主要是通过：UserDetailsService里面的username来实现登录的
        //将浏览器传过来的username，放进去。 返回的是userDetails用户详细信息（账号、密码、权限等等）
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        }catch (UsernameNotFoundException e){
            return new FailResult<>("当前用户不存在");
        }
        //判断传过来的username是否为空 或者 （浏览器输入的和数据库密码不一致） 则密码或者用户名是错的
        if(userDetails==null || !passwordEncoder.matches(password,userDetails.getPassword())){
            return new FailResult<>("用户名或密码不正确");
        }
        //判断是否禁用
        if(!userDetails.isEnabled()){
            return new FailResult<>("账号被禁用.请联系管理员");
        }
        /*
         * 更新security登录用户对象
         * 参数：userDetails,凭证密码null,权限列表
         *
         * security的全局里面
         */
        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken
                (userDetails,null,userDetails.getAuthorities());
        //上下文持有人
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        
        /*
         * 生成token返回给前端
         * 如果以上都没有进入判断，说明用户和密码是正确的：就可以拿到jwt令牌了：
         * 根据用户信息生成令牌
         */
        
        String token = jwtTokenUtil.generateToken(userDetails);
        //有了token，就用map返回：
        Map<String,String> tokenMap=new HashMap<>();
        //将token返回去
        tokenMap.put("token",token);
        //头部信息也返回去前端，让他放在请求头里面
        tokenMap.put("tokenHead",tokenHead);
        return new SuccessResult<>( "登陆成功", tokenMap);
    }
}
