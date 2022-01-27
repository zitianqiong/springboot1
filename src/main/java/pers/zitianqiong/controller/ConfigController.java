package pers.zitianqiong.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>描述:</p>
 * @author 丛吉钰
 */
@RestController
@RequestMapping("config")
public class ConfigController {
    @NacosValue(value = "${useLocalCache}", autoRefreshed = true)
    private boolean useLocalCache;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public boolean get() {
        return useLocalCache;
    }

}
