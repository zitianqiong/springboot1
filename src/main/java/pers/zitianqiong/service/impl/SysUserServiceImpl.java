package pers.zitianqiong.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pers.zitianqiong.common.deprecated.FailResult;
import pers.zitianqiong.common.deprecated.JsonResult;
import pers.zitianqiong.common.deprecated.SuccessResult;
import pers.zitianqiong.domain.SysRole;
import pers.zitianqiong.domain.SysRoleAuth;
import pers.zitianqiong.domain.SysUser;
import pers.zitianqiong.domain.SysUserRole;
import pers.zitianqiong.mapper.SysUserMapper;
import pers.zitianqiong.service.*;
import pers.zitianqiong.utils.JwtTokenUtil;
import pers.zitianqiong.utils.RedisUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
* @author cjy
* @description 表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2024-04-18
*/
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements ISysUserService{

    private final ISysUserRoleService userRoleService;
    private final ISysRoleService roleService;
    private final ISysRoleAuthService roleAuthService;
    private final ISysAuthorityService authorityService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisUtil redisUtil;

    //将配置文件中存的值取过来
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Override
    public SysUser getUser(String username) {
        SysUser sysUser = getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username)
                .eq(SysUser::isEnabled, true));
        if (sysUser != null) {
            sysUser.setRoles(getUserRole(sysUser));
        }
        return sysUser;
    }

    @Override
    public List<SysRole> getUserRole(SysUser user) {
        List<Integer> roleIds = userRoleService.listObjs(
                new LambdaQueryWrapper<SysUserRole>()
                        .eq(SysUserRole::getUserId, user.getId())
                        .select(SysUserRole::getRoleId),  s -> (Integer) s);
        List<SysRole> roles = roleService.listByIds(roleIds);
        for (SysRole role : roles) {
            List<Integer> authIds = roleAuthService.listObjs(
                    new LambdaQueryWrapper<SysRoleAuth>()
                            .eq(SysRoleAuth::getRoleId, role.getId())
                            .groupBy(SysRoleAuth::getAuthorityId)
                            .select(SysRoleAuth::getAuthorityId),  s -> (Integer) s);
            role.setAuthorities(authorityService.listByIds(authIds));
        }
        return roles;
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
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(captcha) || !captcha.equalsIgnoreCase(code)) {
            return new FailResult<>("验证码不正确，请重新输入");
        }
        request.getSession().removeAttribute("captcha");
        //security主要是通过：UserDetailsService里面的username来实现登录的
        //将浏览器传过来的username，放进去。 返回的是userDetails用户详细信息（账号、密码、权限等等）
        UserDetails userDetails = new SysUser();
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
        String token = jwtTokenUtil.generatorToken(userDetails);
        redisUtil.set("userToken:" + username, token, 604800L, TimeUnit.SECONDS);
        //有了token，就用map返回：将token返回去,头部信息也返回去前端，让他放在请求头里面
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return new SuccessResult<>("登陆成功", tokenMap);
    }
}




