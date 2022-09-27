package com.atguigu.order.feignService;

import com.atguigu.commonutils.orderVo.UcenterOrderVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * Author: YZG
 * Date: 2022/9/8 16:07
 * Description: 
 */
@FeignClient("service-ucenter")
@Component
public interface UcenterFeignService {

    @GetMapping("ucenterService/ucenter/getUserInfoById/{userId}")
    @ApiOperation("根据用户id获取用户信息")
     UcenterOrderVo getUserInfoById(@PathVariable("userId") String userId);
}
