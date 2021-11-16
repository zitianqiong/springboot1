package pers.zitianqiong.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author zitianqiong
 */

//@ResponseBody
//@Controller

@RestController
public class HelloController {

	@GetMapping("/hello")
	public String hello(){

		return "hello spring boot";
	}

	@GetMapping("/user")
	public String getUser(){
		return "get";
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
