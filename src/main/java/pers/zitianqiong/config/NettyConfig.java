package pers.zitianqiong.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>描述：</p>
 *
 * @author 丛吉钰
 * @date 2022/12/23
 */
@Component
@ConfigurationProperties(prefix = "netty")
@Data
public class NettyConfig {
    //netty监听的端口
    private int port;
    //websocket访问路径
    private String path;
}
