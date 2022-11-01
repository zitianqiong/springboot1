package pers.zitianqiong.controller;

/**
 * <p>描述:</p>
 * @author 丛吉钰
// *//*
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
*/
