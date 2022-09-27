package com.atguigu.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * Author: YZG
 * Date: 2022/9/13 21:44
 * Description: 
 */
@SpringBootApplication
@ComponentScan("com.atguigu")
@MapperScan("com.atguigu.statistics.mapper")
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling // 定时任务
public class StatisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class,args);
    }
}
