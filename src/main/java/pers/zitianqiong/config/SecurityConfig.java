package pers.zitianqiong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pers.zitianqiong.filter.JwtAuthenticationTokenFilter;
import pers.zitianqiong.handler.RestAuthorizationEntryPoint;
import pers.zitianqiong.handler.RestfulAccessDeniedHandler;

/**
 * <p>描述：</p>
 *
 * @author 丛吉钰
 * @date 2022/12/23
 */
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig{
    @Autowired
    private RestfulAccessDeniedHandler myAccessDeniedHandle;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //关闭csrf验证
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/doc.html","/webjars/**","/v3/api-docs/**","/captcha","/swagger-ui/**").permitAll()
                        .anyRequest().permitAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers()
                .cacheControl();
        //        添加jwt 登录授权过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandle)
                .authenticationEntryPoint(restAuthorizationEntryPoint);
        return http.build();
    }

    /**
     * 定义密码bean后security会自动使用该密码类
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder getPwdEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * bean注解暴露出来
     */
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }
}
