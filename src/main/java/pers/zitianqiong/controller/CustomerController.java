package pers.zitianqiong.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
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
import pers.zitianqiong.utils.RedisUtil;
import pers.zitianqiong.vo.DeptVO;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p></p>
 *
 * @author 丛吉钰
 */
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomerController {

    private final CustomerService userService;
    private final UserDetailsService userDetailsService;
    private final RedisUtil redisUtil;
    private final DeptService deptService;
    
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
            redisUtil.set("userList", userList, 100L, TimeUnit.SECONDS);
        }
        return userList;
    }
    
    /**
     * 当前用户
     * @return User 当前用户
     **/
    @GetMapping("/user/info")
    public UserDetails getUser() {
        UserDetails principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        Customer user;
        if (redisUtil.exists("user:"+username)) {
            user = redisUtil.get("user:"+username);
        } else {
            user = (Customer) userDetailsService.loadUserByUsername(username);
            user.setPassword(null);
            redisUtil.set("user:"+username, user, 100L, TimeUnit.SECONDS);
        }
        return user;
    }
    
    /**
     *
     * @param id .
     * @return User 用户
     **/
    @PostMapping("/user")
    @Cacheable(value = "user", key = "#id", unless = "#result == null")
    public Customer postUser(int id) {
        return userService.getById(id);
    }

    /**
     *
     **/
    @GetMapping("/evict")
    @CacheEvict(value = "userList", key = "'deletedUser'+#id")
    public void evict() {
        log.info("Evicting");
    }

    /**
     * @return String
     **/
    @DeleteMapping("/user")
    public String deleteUser() {
        return "delete";
    }

    /**
     * @return String
     **/
    //nametest
    @PutMapping("/user")
    public String putUser() {
        return "put";
    }
    
    @PostMapping("dept")
    public Result dept(@Validated @RequestBody DeptVO deptVO){
        deptVO.setStuts(Stuts.NORMAL);
        Dept dept = new Dept();
        BeanUtils.copyProperties(deptVO,dept);
        deptService.save(dept);
        return Result.success();
    }
    
}
