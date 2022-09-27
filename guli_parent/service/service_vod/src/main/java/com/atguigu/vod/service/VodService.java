package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Handsome Man.
 *
 * Author: YZG
 * Date: 2022/8/26 17:57
 * Description: 
 */
public interface VodService {
    /**
     * TODO 上传视频
     * @date 2022/8/26 18:01
     * @param file
     * @return java.lang.String
     */
    String uploadVideo(MultipartFile file);
}
