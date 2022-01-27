package pers.zitianqiong.config;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import pers.zitianqiong.service.impl.UserDetailsServiceImpl;

/**
 * <p>描述:</p>
 * @author 丛吉钰
 */
@Slf4j
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder getPwdEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 用户身份认证自定义配置
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf验证
        http.csrf().disable();
        http.authorizeRequests()
                .anyRequest().permitAll().and().logout().permitAll(); //配置不需要登录验证
        /*http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/detail/common/**").hasRole("common")
                .antMatchers("/detail/vip/**").hasRole("vip")
                .anyRequest().authenticated()
                .and().formLogin();*/
        // 自定义用户登录控制
        http.formLogin()
                .loginPage("/userLogin").permitAll()
                .usernameParameter("name").passwordParameter("pwd")
                .defaultSuccessUrl("/")
                .failureUrl("/userLogin?error");

        // 自定义用户退出控制
        http.logout()
                .logoutUrl("/mylogout")
                .logoutSuccessUrl("/");

        // 定制Remember-me记住我功能
        http.rememberMe()
                .rememberMeParameter("rememberme")
                .tokenValiditySeconds(200)//token有效200s
                // 对cookie信息进行持久化管理
                .tokenRepository(tokenRepository());
    }

    /**
     * 持久化Token存储
     * @return
     */
    @Bean
    public JdbcTokenRepositoryImpl tokenRepository() {
        JdbcTokenRepositoryImpl jr = new JdbcTokenRepositoryImpl();
        jr.setDataSource(dataSource);
        return jr;
    }
}
