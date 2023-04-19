package com.atguigu.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/13 17:46
 * Description:
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {

//     aliyun.oss.file.endpoint=oss-cn-beijing.aliyuncs.com
//     aliyun.oss.file.keyid=LTAI5tGjb9ShtDqouVfYRbQD
//     aliyun.oss.file.keysecret=N6Ee5EDar2YgQqTmegGWth7Tp3qmU7
// #bucket可以在控制台创建，也可以使用java代码创建
//     aliyun.oss.file.bucketname=edu-1010-headphoto

    //使用 Spring 中的 @Value 注解读取配置文件中的内容
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId ;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret ;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName ;

    //定义常量，因为上面的变量都是 private 访问不到
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    // 该方法是在 上面哪些属性 赋值之后，才会执行
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
