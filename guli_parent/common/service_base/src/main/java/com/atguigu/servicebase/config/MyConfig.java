package com.atguigu.servicebase.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/5 20:08
 * Description:
 */
@Configuration
public class MyConfig {

    // 开启分页功能
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor (){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor() ;
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return  interceptor ;
    }
}
