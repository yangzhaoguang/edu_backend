package com.atguigu.ucenter;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * Author: YZG
 * Date: 2022/9/4 11:37
 * Description: 
 */
@SpringBootApplication
@ComponentScan("com.atguigu")
@MapperScan("com.atguigu.ucenter.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class,args);
    }
}
