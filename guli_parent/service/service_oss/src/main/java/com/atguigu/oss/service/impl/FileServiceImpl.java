package com.atguigu.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectRequest;
import com.atguigu.oss.service.FileService;
import com.atguigu.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/13 18:07
 * Description:
 */
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // 地域节点
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 秘钥 ID
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        // 秘钥密码
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        // 存储桶名称
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 获取文件名，使用 uuid 拼接以下，防止文件名重复
        String fileName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        if (fileName != null) {
            String[] strings = fileName.split("\\.");
            fileName = strings[0] + "-" + uuid + "." + strings[1];
        }

        // 根据日期进行分类
        // joda-time 依赖提供的工具
        String timePath = new DateTime().toString("yyyy/MM/dd");
        fileName = timePath + "/" + fileName;

        try {
            // 获取文件输入流
            InputStream inputStream = file.getInputStream();
            // 创建PutObject请求。
            // 第二个参数： 文件上传的路径，比如： /a/b/1.png 如果存储桶中没有 a、b 文件夹会自动创建
            ossClient.putObject(bucketName, fileName, inputStream);
            // 返回文件的 url
            // https://edu-1010-headpicture.oss-cn-hangzhou.aliyuncs.com/1.png
            return "https://" + bucketName + "." + endpoint + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ossClient != null) {
                // 关闭连接
                ossClient.shutdown();
            }
        }

    }
}

