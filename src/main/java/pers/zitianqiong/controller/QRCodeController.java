package pers.zitianqiong.controller;

import org.springframework.web.bind.annotation.*;
import pers.zitianqiong.annontation.IgnoreGlobalResponse;
import pers.zitianqiong.utils.QrCodeUtil;

import java.io.IOException;

/**
 * <p>描述：</p>
 *
 * @author 丛吉钰
 * @date 2022/11/1
 */
@RestController
@RequestMapping("/qrcode")
public class QRCodeController {
    
    /**
     * 传入二维码要生成的内容 返回base64图片
     * @param value 二维码值
     * @return 字节流
     * @throws IOException 异常
     */
    @GetMapping(value = "/getQRCode")
    @ResponseBody
    @IgnoreGlobalResponse
    public String getQRCode(@RequestParam("value") String value) throws IOException {
        String qrCodeValue = value == null ? "null" : value;
        int HEIGHT = 200;
        int WIDTH = 200;
        return QrCodeUtil.crateQRCode(qrCodeValue, WIDTH, HEIGHT);
    }
}
