package pers.zitianqiong.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pers.zitianqiong.utils.QrCodeUtils;

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
     */
    @RequestMapping(value = "/getQRCode")
    @ResponseBody
    public String getQRCode(@RequestParam("value") String value) throws IOException {
        String qrCodeValue = value == null ? "null" : value;
        return QrCodeUtils.crateQRCode(qrCodeValue, 200, 200);
    }
}
