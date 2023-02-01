package pers.zitianqiong.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>描述：</p>
 *
 * @author 丛吉钰
 * @date 2022/12/16
 */
@Configuration
public class SpringDocConfig implements WebMvcConfigurer {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    
    @Bean
    public OpenAPI springDocOpenApi() {
        return new OpenAPI()
                .info(info())
/*添加对JWT对token的支持(本步骤可选) 在添加OpenApiConfig类上添加Components信息：然后在OpenApi中注册Components:*/
                .components(components());
    }
    
    private Info info() {
        return new Info()
                .title("springDoc Open API文档")
                .description("springDoc学习文档")
                .version("v1.0")
                .contact(new Contact().url("http://www.huamenghuan.top").name("springboot"))
                .termsOfService("http://doc.xiaominfo.com")
                .license(license());
    }
    
    private License license() {
        return new License()
                .name("Apache 2.0")
                .url("http://www.apache.org/licenses/LICENSE-2.0");
    }
    
    /* 添加对JWT对token的支持(本步骤可选)
     在添加OpenApiConfig类上添加Components信息：*/
    private Components components() {
        return new Components()
                .addSecuritySchemes(tokenHeader,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                                .description("请求头"));
    }

//    /*在需要使用Authorization的接口上添加：*/
//    @Operation(security = { @SecurityRequirement(name = "bearer-key") })

}
