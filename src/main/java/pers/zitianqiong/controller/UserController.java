package pers.zitianqiong.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import pers.zitianqiong.domain.User;
import pers.zitianqiong.service.UserService;
import pers.zitianqiong.utils.RedisUtil;

import java.util.List;

/**
 * <p></p>
 *
 * @author 丛吉钰
 */
@RestController
@Slf4j
public class UserController {

	private final UserService userService;

	private final RedisUtil redisUtil;

	@Autowired
	public UserController(UserService userService,RedisUtil redisUtil){
		this.userService = userService;
		this.redisUtil= redisUtil;
	}

	@GetMapping("/user")
	public List<User> getUsers(){
		String series = "userList";
		List<User> userList;
		if(redisUtil.exists(series)){
			userList = (List<User>) redisUtil.get(series);
			System.out.println(userList);
		}else {
			userList = userService.list();
			redisUtil.set("userList",userList);
		}
		return userList;
	}


	@PostMapping("/user")
	@Cacheable(value = "user",key = "#id+1",unless="#result == null")
	public User postUser(int id){
		User user = userService.getById(id);
		return user;
	}

	@GetMapping("/find")
	@Cacheable(value = "find",unless="#result == null")
	public List<User> findDeleted(){
		List<User> userList =  userService.findDeleted();
		System.out.println(userList.get(0).getName());
		return userList;
	}
	@GetMapping("/evict")
	@CacheEvict(key = "'deletedUser'+#id")
	public void evict(){
		log.info("Evicting");
	}

	@DeleteMapping("/user")
	public String deleteUser(){
		return "delete";
	}

    //nametest
	@PutMapping("/user")
	public String putUser(){
		return "put";
	}
}
