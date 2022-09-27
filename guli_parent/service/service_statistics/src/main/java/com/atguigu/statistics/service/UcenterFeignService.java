package com.atguigu.statistics.service;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * Author: YZG
 * Date: 2022/9/13 22:30
 * Description: 
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterFeignService {

    @GetMapping("ucenterService/ucenter/countRegister/{date}")
    R countRegister(@PathVariable String date);
}
