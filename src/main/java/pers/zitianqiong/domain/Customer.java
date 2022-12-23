package pers.zitianqiong.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户表
 *
 * @TableName customer
 */
@TableName(value = "customer")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Data
@JsonIgnoreProperties({"accountNonExpired", "accountNonLocked", "credentialsNonExpired", "authorities"})
@Schema(name = "Customer", description = "用户")
public class Customer implements Serializable, UserDetails {
    /**
     * 用户id唯一
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 昵称
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 年龄
     */
    private Integer age;
    
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    
    /**
     * 乐观锁版本
     */
    @Version
    private Integer version;
    
    @Getter(AccessLevel.NONE)//加上这个注解，就不会生成该字段的get，set方法
    private Boolean enabled;
    
    @TableField(exist = false)//告诉mybatis plus 数据库中没有这个自定义的字段
    private List<Authority> roles;
    
    @TableField(exist = false)
    List<SimpleGrantedAuthority> authorities;
    
    /**
     * 是否被删除0：正常，1：删除
     */
    @TableLogic
    private Integer deleted;
    
    @TableField(exist = false)
    private static final long serialVersionUID = -116846212116874336L;
    
    /**
     * 已经是SpringSecurity框架了
     * 真正登录的方法就是UserDetails的Username
     * 登陆成功就是details
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        if (roles != null) {
            authorities = roles.stream()
                    //将获得的权限名字通过 SimpleGrantedAuthority 转换成授权的 url
                    .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                    .collect(Collectors.toList());
            return authorities;
        }
        return null;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
