package pers.zitianqiong.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.zitianqiong.service.WebSocketServer;

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
    
    @RequestMapping("/index")
    public String index2(){
        return "index";
    }
    
    @GetMapping("/detail/{type}/{path}")
    public String toDetail(@PathVariable("type") String type, @PathVariable("path") String path) {
        return "detail/" + type + "/" + path;
    }
    
    @GetMapping("sendMsg")
    public void sendMsg() {
        WebSocketServer.sendInfo("test");
    }
}
