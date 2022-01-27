package pers.zitianqiong.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.zitianqiong.domain.Customer;
import pers.zitianqiong.service.CustomerService;
import pers.zitianqiong.utils.RedisUtil;

/**
 * <p></p>
 *
 * @author 丛吉钰
 */
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomerController {

    private final CustomerService userService;

    private final RedisUtil redisUtil;

    private final KafkaTemplate kafkaTemplate;

    /**
     *
     * @param message .
     * @return String message
     **/
    @GetMapping("send")
    public String send(String message) {
        kafkaTemplate.send("topic.quick.demo", message);
        return message;
    }

    /**
     * @return List<User>
     **/
    @GetMapping("/user")
    public List<Customer> getUsers() {
        String series = "userList";
        List<Customer> userList;
        if (redisUtil.exists(series)) {
            userList = (List<Customer>) redisUtil.get(series);
        } else {
            userList = userService.list();
            redisUtil.set("userList", userList);
        }
        return userList;
    }

    /**
     *
     * @param id .
     * @return User 用户
     **/
    @PostMapping("/user")
    @Cacheable(value = "user", key = "#id", unless = "#result == null")
    public Customer postUser(int id) {
        return userService.getById(id);
    }

    /**
     *
     **/
    @GetMapping("/evict")
    @CacheEvict(value = "userList", key = "'deletedUser'+#id")
    public void evict() {
        log.info("Evicting");
    }

    /**
     * @return String
     **/
    @DeleteMapping("/user")
    public String deleteUser() {
        return "delete";
    }

    /**
     * @return String
     **/
    //nametest
    @PutMapping("/user")
    public String putUser() {
        return "put";
    }
}
