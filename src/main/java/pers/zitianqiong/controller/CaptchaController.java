package pers.zitianqiong.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码
 */
@RestController
@Slf4j
public class CaptchaController {
    @Autowired
    private DefaultKaptcha defaultKaptch;
    
    /**
     * 验证码生成
     * @param request 请求
     * @param response 响应
     */
    @GetMapping(value = "/captcha", produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        //通过流的形式传播图片。
        //定义response输出类型为Imag/jpeg类型
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, mustrevalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        //-------------------生成验证码 begin --------------------------
        //业务逻辑：
        //获取验证码的文本内容
        String text = defaultKaptch.createText();
        //有了验证码，那么肯定要和用户输入的进行比较。
        //会把验证码文本内容放到session里面，从session中获取进行比较
        request.getSession().setAttribute("captcha", text);
        //得到图形验证码
        BufferedImage image = defaultKaptch.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            //输出流输出图片
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("验证码生成错误", e);
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("验证码生成错误", e);
                }
            }
        }
        //-------------------生成验证码 end --------------------------
    }
}
