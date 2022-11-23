package pers.zitianqiong.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>描述: nacos动态配置</p>
 * @author 丛吉钰
 */
@RestController
@RequestMapping("config")
@Deprecated
public class ConfigController {
    /*@NacosValue(value = "${useLocalCache}", autoRefreshed = true)
    private boolean useLocalCache;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public boolean get() {
        return useLocalCache;
    }
*/
}
