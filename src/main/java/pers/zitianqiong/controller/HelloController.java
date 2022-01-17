package pers.zitianqiong.controller;

import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zitianqiong
 */

@Controller
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
     *
     * @param model .
     * @return String
     **/
    @GetMapping("/toLoginPage")
    public String toLoginPage(Model model) {
        model.addAttribute("currentYear", Calendar.getInstance().get(Calendar.YEAR));
        return "login";
    }

}
