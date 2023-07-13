package pers.zitianqiong.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("sendMsg")
    @ResponseBody
    public void sendMsg() {
        WebSocketServer.sendInfo("test");
    }
}
