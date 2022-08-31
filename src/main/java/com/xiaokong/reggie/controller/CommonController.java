package com.xiaokong.reggie.controller;

import com.xiaokong.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Description 文件资源的上传与下载
 * Author Mrk
 * Date 2022/8/22 15:37
 * Version 1.0
 */
@RequestMapping("/common")
@RestController
@Slf4j
public class CommonController {
    @PostMapping("/upload")
    public R<String> fileUpload(MultipartFile file) {
        ApplicationHome h = new ApplicationHome(getClass());
        File dir = h.getSource();
        String picPath = dir.getAbsolutePath() + "\\pics\\" + file.getOriginalFilename();
        try {
            file.transferTo(new File(picPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(file.getOriginalFilename());
    }

    @GetMapping("/download")
    public void picDownload(String name, HttpServletResponse response) {
        ApplicationHome h = new ApplicationHome(getClass());
        File dir = h.getSource();
        String picPath = dir.getAbsolutePath() + "\\pics\\" + name;
        try (FileImageInputStream fis = new FileImageInputStream(
                new File(picPath)); ServletOutputStream outputStream = response.getOutputStream()) {
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fis.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
        } catch (IOException e) {
            System.out.println("有的图片啊,查不到");
        }
    }
}
