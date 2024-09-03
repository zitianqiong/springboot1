package pers.zitianqiong.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import pers.zitianqiong.common.deprecated.JsonResult;
import pers.zitianqiong.domain.SysRole;
import pers.zitianqiong.domain.SysUser;

import java.util.List;

/**
* @author cjy
* @description 表【sys_user(用户表)】的数据库操作Service
* @createDate 2024-04-18
*/
public interface ISysUserService extends IService<SysUser> {

    /**
     * 获得用户
     * @param username 用户名
     * @return 用户信息
     */
    SysUser getUser(String username);

    /**
     * 用户权限
     * @param user 用户
     * @return 用户权限
     */
    List<SysRole> getUserRole(SysUser user);

    /**
     * 登录之后，返回token
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param request 请求
     * @return 响应
     */
    JsonResult<?> login(String username, String password, String code, HttpServletRequest request);
}
