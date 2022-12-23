package pers.zitianqiong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pers.zitianqiong.filter.JwtAuthencationTokenFilter;

/**
 * <p>描述：</p>
 *
 * @author 丛吉钰
 * @date 2022/12/23
 */
@Configuration
public class SecurityConfig {
    
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers(
                "/doc.html","/webjars/**","/v3/api-docs/**","/captcha","/swagger-ui/**");
    }
    
    /**
     * 定义密码bean后security会自动使用该密码类
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder getPwdEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //关闭csrf验证
        http.csrf().disable();
        http.authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers()
                .cacheControl();
//        添加jwt 登录授权过滤器
        http.addFilterBefore(jwtAuthencationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    
    /**
     * bean注解暴露出来
     */
    @Bean
    public JwtAuthencationTokenFilter jwtAuthencationTokenFilter() {
        return new JwtAuthencationTokenFilter();
    }
}
