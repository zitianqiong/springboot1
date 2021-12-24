package pers.zitianqiong.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RedisUtil redisUtil;

	@GetMapping("/user")
	public List<User> getUsers(){
		if(redisUtil.exists("userList")){
			List<User> userList = (List<User>) redisUtil.get("userList");
			System.out.println(userList);
		}else {
			List<User> userList = userService.list();
		}

		return userService.list();
	}

	@PostMapping("/user")
	public String postUser(){
		return "post";
	}

	@DeleteMapping("/user")
	public String deleteUser(){
		return "delete";
	}

	@PutMapping("/user")
	public String putUser(){
		return "put";
	}
}
