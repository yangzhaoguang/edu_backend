package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/13 18:07
 * Description:
 */
public interface FileService {

    String uploadFileAvatar(MultipartFile file);
}
