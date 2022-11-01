package pers.zitianqiong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>描述:</p>
 * @author 丛吉钰
 */
@Controller
public class FilmeController {
    //  影片详情页
    @GetMapping("/detail/{type}/{path}")
    public String toDetail(@PathVariable("type") String type, @PathVariable("path") String path) {
        return "detail/" + type + "/" + path;
    }

    // 向用户登录页面跳转
    @GetMapping("/userLogin")
    public String toLoginPage(String param) {
        
        if ("error".equals(param)){
            return "login/login";
        }
        return "/login";
    }
    
}
