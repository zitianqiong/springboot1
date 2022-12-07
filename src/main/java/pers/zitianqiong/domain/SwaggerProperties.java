package pers.zitianqiong.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>描述：</p>
 *
 * @author 丛吉钰
 * @date 2022/11/24
 */
@Component
@ConfigurationProperties("swagger")
@Getter
@Setter
public class SwaggerProperties {
    
    /**
     * 项目应用名
     */
    private String applicationName;
    
    /**
     * 项目版本信息
     */
    private String applicationVersion;
    
    /**
     * 项目描述信息
     */
    private String applicationDescription;
    
    /**
     * 接口调试地址
     */
    private String tryHost;
}
