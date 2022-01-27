package pers.zitianqiong.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.cjy.server.service.TicketService;

/**
 * <p>描述:</p>
 * @author 丛吉钰
// */
@RestController
@Slf4j
public class DubboController {

    @DubboReference
    private TicketService ticketService;

    @RequestMapping("say")
    public String doSayHello(String name) {
        return ticketService.sayHello(name);
    }
}
