package pers.zitianqiong.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author zitianqiong
 */

@RestController
public class HelloController {

	@GetMapping("/hello")
	public String hello(){

		return "hello spring boot";
	}

}
