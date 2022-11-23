package pers.zitianqiong.controller;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件管理控制类
 */
@Controller
@Slf4j
public class FileController {
    
    /**
     * 文件上传管理
     * @param fileUpload 文件列表
     * @param model 视图层
     * @return 上传页面
     */
    @PostMapping("/uploadFile")
    public String uploadFile(MultipartFile[] fileUpload, Model model) {
        // 默认文件上传成功，并返回状态信息
        model.addAttribute("uploadStatus", "上传成功！");
        for (MultipartFile file : fileUpload) {
            // 获取文件名以及后缀名
            String fileName = file.getOriginalFilename();
            // 重新生成文件名（根据具体情况生成对应文件名）
            fileName = UUID.randomUUID() + "_" + fileName;
            // 指定上传文件本地存储目录，不存在需要提前创建
            String dirPath = "./file";
            File filePath = new File(dirPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            try {
                file.transferTo(new File(dirPath + fileName));
            } catch (Exception e) {
                log.error("文件上传失败", e);
                // 上传失败，返回失败信息
                model.addAttribute("uploadStatus", "上传失败： " + e.getMessage());
            }
        }
        // 携带上传状态信息回调到文件上传页面
        return "upload";
    }
    
//    // 文件下载管理
//    @GetMapping("/download")
//    public ResponseEntity<byte[]> fileDownload(String filename){
//        // 指定要下载的文件根路径
//        String dirPath = "F:/file/";
//        // 创建该文件对象
//        File file = new File(dirPath + File.separator + filename);
//        // 设置响应头
//        HttpHeaders headers = new HttpHeaders();
//        // 通知浏览器以下载方式打开
//        headers.setContentDispositionFormData("attachment",filename);
//        // 定义以流的形式下载返回文件数据
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        try {
//            return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<byte[]>(e.getMessage().getBytes(),HttpStatus.EXPECTATION_FAILED);
//        }
//    }
    
    /**
     * 所有类型文件下载管理
     * @param request 请求
     * @param filename 文件名
     * @return 字节流
     * @throws Exception 异常
     */
    @GetMapping("/download")
    public ResponseEntity<byte[]> fileDownload(HttpServletRequest request,
                                               String filename) throws Exception {
        // 指定要下载的文件根路径
        String dirPath = "./file/";
        // 创建该文件对象
        File file = new File(dirPath + File.separator + filename);
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        // 通知浏览器以下载方式打开（下载前对文件名进行转码）
        filename = getFilename(request, filename);
        headers.setContentDispositionFormData("attachment", filename);
        // 定义以流的形式下载返回文件数据
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        try {
            return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("文件下载错误", e);
            return new ResponseEntity<>(e.getMessage().getBytes(), HttpStatus.EXPECTATION_FAILED);
        }
    }
    
    /**
     * 根据浏览器的不同进行编码设置，返回编码后的文件名
     * @param request 请求
     * @param filename 文件名
     * @return 文件名
     * @throws Exception 异常
     */
    private String getFilename(HttpServletRequest request, String filename)
            throws Exception {
        // IE不同版本User-Agent中出现的关键词
        String[] IEBrowserKeyWords = {"MSIE", "Trident", "Edge"};
        // 获取请求头代理信息
        String userAgent = request.getHeader("User-Agent");
        for (String keyWord : IEBrowserKeyWords) {
            if (userAgent.contains(keyWord)) {
                //IE内核浏览器，统一为UTF-8编码显示，并对转换的+进行更正
                return URLEncoder.encode(filename, "UTF-8").replace("+", " ");
            }
        }
        //火狐等其它浏览器统一为ISO-8859-1编码显示
        return new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
    }
    
}
