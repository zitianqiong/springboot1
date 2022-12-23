package pers.zitianqiong.config;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * <p>描述:</p>
 *
 * @author 丛吉钰
 */
@Deprecated
public class OldSecurityConfig extends WebSecurityConfigurerAdapter {
    
    /**
     * 用户身份认证自定义配置
     *
     * @param auth
     * @throws Exception
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService()).passwordEncoder(getPwdEncoder());
//    }

//    @SuppressWarnings("checkstyle:MagicNumber")
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //关闭csrf验证
//        http.csrf().disable();
//        http.authorizeRequests()
//                .antMatchers(
//                        "/login","/logout","/css/**","/js/**","/index.html","favicon.ico",
//                        "/doc.html","/webjars/**","/v2/api-docs/**","/captcha","/swagger-ui/index.html").permitAll()
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .anyRequest().permitAll()
//                .and()
//                .cors().configurationSource(configurationSource())
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .headers()
//                .cacheControl();
////        添加jwt 登录授权过滤器
//        http.addFilterBefore(jwtAuthencationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//    }

//    @Override
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> {
//            Customer customer = customerService.getCustomer(username);
//            if (customer != null) {
//                customer.setRoles(customerService.getCustomerAuthority(username));
//                return customer;
//            } else {
//                throw new UsernameNotFoundException("当前用户不存在");
//            }
//        };
//    }

//    // cors配置
//    CorsConfigurationSource configurationSource(){
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        UrlBasedCorsConfigurationSource url = new UrlBasedCorsConfigurationSource();
//        url.registerCorsConfiguration("/**",corsConfiguration);
//        return url;
//    }
}
