package pers.zitianqiong.config;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pers.zitianqiong.domain.Customer;
import pers.zitianqiong.filter.JwtAuthencationTokenFilter;
import pers.zitianqiong.service.CustomerService;

/**
 * <p>描述:</p>
 *
 * @author 丛吉钰
 */
@Slf4j
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Lazy
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    
    @Bean
    public PasswordEncoder getPwdEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * 用户身份认证自定义配置
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(getPwdEncoder());
    }
    
    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf验证
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(
                        "/login","/logout","/css/**","/js/**","/index.html","favicon.ico",
                        "/doc.html","/webjars/**","/v2/api-docs/**","/captcha","/swagger-ui/index.html").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .cors().configurationSource(configurationSource())
                .and()
                .logout().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers()
                .cacheControl();
//        添加jwt 登录授权过滤器
        http.addFilterBefore(jwtAuthencationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }
    
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Customer customer = customerService.getCustomer(username);
            if (customer != null) {
                customer.setRoles(customerService.getCustomerAuthority(username));
                return customer;
            } else {
                throw new UsernameNotFoundException("当前用户不存在");
            }
        };
    }
    
    //bean注解暴露出来
    @Bean
    public JwtAuthencationTokenFilter jwtAuthencationTokenFilter() {
        return new JwtAuthencationTokenFilter();
    }
    
    // cors配置
    CorsConfigurationSource configurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource url = new UrlBasedCorsConfigurationSource();
        url.registerCorsConfiguration("/**",corsConfiguration);
        return url;
    }
}
