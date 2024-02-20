package pers.zitianqiong.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表
 *
 * @TableName customer
 */
@TableName(value = "customer")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Data
@JsonIgnoreProperties({"accountNonExpired", "accountNonLocked", "credentialsNonExpired", "authorities", "password",
        "enabled", "version", "deleted"})
@Schema(name = "Customer", description = "用户")
public class Customer implements Serializable, UserDetails, CredentialsContainer {
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
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 乐观锁版本
     */
    @Version
    private Integer version;

    //加上这个注解，就不会生成该字段的get，set方法
    @Getter(AccessLevel.NONE)
    private Boolean enabled;

    @TableField(exist = false)
    private List<Authority> roles;

    @TableField(exist = false)
    private List<SimpleGrantedAuthority> authorities;

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

    @Override
    public void eraseCredentials() {
        // 设置 password 为 null
        this.password = null;
    }
}
