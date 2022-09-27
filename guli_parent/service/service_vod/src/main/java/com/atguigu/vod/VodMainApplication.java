package com.atguigu.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;

/**
 *
 * Author: YZG
 * Date: 2022/8/25 20:12
 * Description: 
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.atguigu")
@EnableDiscoveryClient
public class VodMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodMainApplication.class,args);
    }
}
