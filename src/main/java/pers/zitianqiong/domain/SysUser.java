package pers.zitianqiong.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonIgnoreProperties({"accountNonExpired", "accountNonLocked", "credentialsNonExpired", "authorities", "password",
        "enabled", "version", "deleted"})
@Schema(name = "User", description = "用户")
public class SysUser implements Serializable, UserDetails, CredentialsContainer {
    /**
     * 用户id唯一
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    /**
     * 登陆账号
     */
    @TableField(value = "name")
    private String name;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 是否启用 1启用 2休眠
     */
    @TableField(value = "enabled")
    //加上这个注解，就不会生成该字段的get，set方法
    @Getter(AccessLevel.NONE)
    private Boolean enabled;

    /**
     * 年龄
     */
    @TableField(value = "birthday")
    private LocalDate birthday;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 是否被删除0：正常，1：删除
     */
    @TableField(value = "deleted")
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = -116846212116874336L;

    @TableField(exist = false)
    private List<SysRole> roles;

    @TableField(exist = false)
    private List<SimpleGrantedAuthority> authorities;

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
                    .map(role -> new SimpleGrantedAuthority(role.getRoleCode()))
                    .collect(Collectors.toList());
            List<String> authCodeList = new ArrayList<>();
            for (SysRole role : roles) {
                List<SysAuthority> roleAuthorities = role.getAuthorities();
                authCodeList.addAll(roleAuthorities.stream()
                        //将获得的权限名字通过 SimpleGrantedAuthority 转换成授权的 url
                        .map(SysAuthority::getAuthority)
                        .collect(Collectors.toList()));
            }
            authorities.addAll(authCodeList.stream().distinct().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
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
        return enabled;
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