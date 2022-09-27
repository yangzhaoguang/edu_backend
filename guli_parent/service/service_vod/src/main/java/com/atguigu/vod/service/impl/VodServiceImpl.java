package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * Author: YZG
 * Date: 2022/8/26 17:57
 * Description: 
 */
@Service
public class VodServiceImpl implements VodService {

    /**
     * TODO 视频上传
     * @date 2022/8/26 18:09
     * @param file
     * @return java.lang.String
     */
    @Override
    public String uploadVideo(MultipartFile file) {
        // 获取 AccessKey
        String accessKeyId = ConstantUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantUtil.ACCESS_KEY_SECRET;
        // 视频的初始名字
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        // 视频上传后的名字
        // 只保留视频名前缀
        String title = fileName.substring(0, fileName.lastIndexOf("."));
        // 获取视频输入流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            // 视频 ID
            String VideoId = null;
            if (response.isSuccess()) {
                VideoId = response.getVideoId();
            } else {
                //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                VideoId = response.getVideoId();
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
            return VideoId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
