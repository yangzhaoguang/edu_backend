package com.atguigu.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * Author: YZG
 * Date: 2022/8/29 0:13
 * Description: 
 */
@SpringBootApplication
@MapperScan("com.atguigu.cms.mapper")
@ComponentScan("com.atguigu")
public class CmsMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsMainApplication.class,args);
    }
}
