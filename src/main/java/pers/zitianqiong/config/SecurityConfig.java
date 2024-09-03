package pers.zitianqiong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pers.zitianqiong.filter.JwtAuthenticationTokenFilter;
import pers.zitianqiong.handler.RestAuthorizationEntryPoint;
import pers.zitianqiong.handler.RestfulAccessDeniedHandler;

/**
 * <p>描述：在这里，我们移除了 WebSecurityConfigurerAdapter，这样就不需要再覆写安全配置的方法了。
 * 相反，我们可以注册 Bean 来进行安全配置。我们可以注册WebSecurityCustomizer Bean 来配置 Web Security，
 * SecurityFilterChain Bean 来配置 HTTP Security，
 * InMemoryUserDetails Bean 来注册自定义用户等等。</p>
 *
 * @author 丛吉钰
 * @date 2022/12/23
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig{
    @Autowired
    private RestfulAccessDeniedHandler myAccessDeniedHandle;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;

    /**
     * 通过定义 WebSecurityCustomizer Bean 来排除静态资源
     * WebSecurityCustomizer 接口取代了 WebSecurityConfigurerAdapter 接口中的configure(Websecurity web) 方法。
     * @deprecated 新版本已弃用，建议使用HttpSecurity
     */
    // @Bean
    // WebSecurityCustomizer webSecurityCustomizer() {
    //     return (web) -> web.ignoring().requestMatchers("/js/**", "/css/**");
    // }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //关闭csrf验证
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(request -> request
                        .requestMatchers("/doc.html","/webjars/**","/v3/api-docs/**","/captcha","/swagger-ui/**").permitAll()
                        .anyRequest().permitAll()
                //.authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        //        添加jwt 登录授权过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(except ->
                    except.accessDeniedHandler(myAccessDeniedHandle).authenticationEntryPoint(restAuthorizationEntryPoint)
                );
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
