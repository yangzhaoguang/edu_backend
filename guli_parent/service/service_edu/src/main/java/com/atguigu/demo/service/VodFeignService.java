package com.atguigu.demo.service;

import com.atguigu.commonutils.R;
import com.atguigu.demo.service.impl.VodFeignServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Handsome Man.
 *
 * Author: YZG
 * Date: 2022/8/27 17:54
 * Description: 
 */
@Component
// 服务提供者的服务名称
@FeignClient(value = "service-vod",fallback = VodFeignServiceImpl.class)
public interface VodFeignService {

    // 向服务提供端发送请求
    @DeleteMapping("/vodservice/vod/deleteALiYunVideo/{id}")
     R deleteVideo(@PathVariable("id") String id);


    // 删除多个视频
    @DeleteMapping("/vodservice/vod/deleteBatch")
     R deleteBatch(@RequestParam("videoIds") List<String> videoIds) ;
}
