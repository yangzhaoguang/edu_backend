package com.atguigu.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/5 14:21
 * Description:
 */
@SpringBootApplication
@MapperScan("com.atguigu.demo.mapper")
@EnableDiscoveryClient
@EnableFeignClients
// 配置包扫描， 为了能扫描到 service_base 模块下的 SwaggerConfig 配置类
@ComponentScan(basePackages = {"com.atguigu"})
public class EduTeacherApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduTeacherApplication.class,args);
    }
}
