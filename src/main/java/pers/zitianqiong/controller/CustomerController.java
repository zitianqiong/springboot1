package pers.zitianqiong.controller;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.zitianqiong.common.Result;
import pers.zitianqiong.domain.Customer;
import pers.zitianqiong.domain.Dept;
import pers.zitianqiong.domain.Stuts;
import pers.zitianqiong.service.CustomerService;
import pers.zitianqiong.service.DeptService;
import pers.zitianqiong.utils.JwtTokenUtil;
import pers.zitianqiong.utils.RedisUtil;
import pers.zitianqiong.vo.DeptVO;

/**
 * <p></p>
 *
 * @author 丛吉钰
 */
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class       CustomerController {
    
    private final CustomerService userService;
    private final UserDetailsService userDetailsService;
    private final RedisUtil redisUtil;
    private final DeptService deptService;
    private final JwtTokenUtil jwtTokenUtil;
    
    private final long EXPIRE_TIME = 100L;
    
    /**
     * @return List<User>
     **/
    @GetMapping("/user")
    public List<Customer> getUsers() {
        String series = "userList";
        List<Customer> userList;
        if (redisUtil.exists(series)) {
            userList = redisUtil.get(series);
        } else {
            userList = userService.list();
            userList.forEach(user -> {
                user.setPassword(null);
            });
            redisUtil.set("userList", userList, EXPIRE_TIME, TimeUnit.SECONDS);
        }
        return userList;
    }
    
    /**
     * 当前用户
     * @param principal 用户信息
     * @return User 当前用户
     **/
    @GetMapping("/user/info")
    public UserDetails getUser(Principal principal) {
//        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getName();
        Customer user;
        if (redisUtil.exists("user:" + username)) {
            user = redisUtil.get("user:" + username);
        } else {
            user = (Customer) userDetailsService.loadUserByUsername(username);
            user.setPassword(null);
            redisUtil.set("user:" + username, user, EXPIRE_TIME, TimeUnit.SECONDS);
        }
        return user;
    }
    
    /**
     * @param id .
     * @return User 用户
     **/
    @PostMapping("/user")
    @Cacheable(value = "user", key = "#id", unless = "#result == null")
    public Customer postUser(int id) {
        return userService.getById(id);
    }
    
    /**
     * 删除缓存
     **/
    @GetMapping("/evict")
    @CacheEvict(value = "userList", key = "'deletedUser'+#id")
    public void evict() {
        log.info("Evicting");
    }
    
    /**
     *
     * @param deptVO 部门
     * @return 响应
     */
    @PostMapping("dept")
    public Result dept(@Validated @RequestBody DeptVO deptVO) {
        deptVO.setStuts(Stuts.NORMAL);
        Dept dept = new Dept();
        BeanUtils.copyProperties(deptVO, dept);
        deptService.save(dept);
        return Result.success();
    }
    
    /**
     * 用户退出
     * @return 响应
     */
    @GetMapping("/user/logout")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public Result logout(Principal principal) {
        if (principal == null){
            return Result.success("用户已退出");
        }
        redisUtil.remove("username:"+principal.getName());
        log.info("{}用户退出", principal.getName());
        return Result.success();
    }
}
