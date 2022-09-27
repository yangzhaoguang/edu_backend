package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.FileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/13 18:05
 * Description:
 */
@RestController
@RequestMapping("/oss/file")
//@CrossOrigin // 解决跨域问题
public class OssController {

    @Autowired
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("upload")
    private R uploadFile(@RequestParam("file") MultipartFile file) {
        // 返回一个 头像的地址
        String url = fileService.uploadFileAvatar(file);
        return R.ok().data("url", url);
    }

}
