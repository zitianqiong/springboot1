package pers.zitianqiong.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

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
import pers.zitianqiong.utils.RedisUtil;

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
    @Autowired
    private RedisUtil redisUtil;
    
    //将配置文件中存的值取过来
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    
    
    @Override
    public Customer getCustomer(String username) {
        return getOne(new LambdaQueryWrapper<Customer>().eq(Customer::getUsername, username)
                .eq(Customer::isEnabled, true));
    }
    
    @Override
    public List<Authority> getCustomerAuthority(String username) {
        Customer customer = getOne(new LambdaQueryWrapper<Customer>().eq(Customer::getUsername, username));
        QueryWrapper<CustomerAuthority> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("authority_id", customer.getId());
        List<CustomerAuthority> customerAuthorities = customerAuthorityService.list(queryWrapper);
        List<Integer> authoritiesId =
                customerAuthorities.stream().map(CustomerAuthority::getAuthorityId).collect(Collectors.toList());
        List<Authority> authorities = authorityService.listByIds(authoritiesId);
        authorities.forEach(System.out::println);
        return authorities;
    }
    
    /**
     * 登录之后返回token
     *
     * @param username 用户名
     * @param password 密码
     * @param request  请求
     * @return 响应
     */
    @Override
    public JsonResult<?> login(String username, String password, String code, HttpServletRequest request) {
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)) {
            return new FailResult<>("验证码不正确，请重新输入");
        }
        //security主要是通过：UserDetailsService里面的username来实现登录的
        //将浏览器传过来的username，放进去。 返回的是userDetails用户详细信息（账号、密码、权限等等）
        UserDetails userDetails = new Customer();
        String errorMsg = null;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            errorMsg = "当前用户不存在";
        }
        //判断传过来的username是否为空 或者 （浏览器输入的和数据库密码不一致） 则密码或者用户名是错的
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            errorMsg = "用户名或密码不正确";
        } else { //判断是否禁用
            if (!userDetails.isEnabled()) {
                errorMsg = "账号被禁用.请联系管理员";
            }
        }
        if (StringUtils.isNotEmpty(errorMsg)) {
            return new FailResult<>(errorMsg);
        }
        /*
         * 更新security登录用户对象
         * 参数：userDetails,凭证密码null,权限列表
         * security的全局里面
         */
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        //上下文持有人
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        /*
         * 根据用户信息生成令牌,生成token返回给前端
         * 如果以上都没有进入判断，说明用户和密码是正确的：就可以拿到jwt令牌了
         */
        String token = jwtTokenUtil.generateToken(userDetails);
        redisUtil.set("userToken:" + username, token, 604800L, TimeUnit.SECONDS);
        //有了token，就用map返回：将token返回去,头部信息也返回去前端，让他放在请求头里面
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return new SuccessResult<>("登陆成功", tokenMap);
    }
}
