package pers.zitianqiong.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zitianqiong
 */

@Controller
@Slf4j
public class HelloController {

    /**
     * @return String
     **/
    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        return "hello spring boot";
    }
    
    /**
     * @return String
     **/
    @GetMapping("")
    public String index() {
        return "index";
    }
}
