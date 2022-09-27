package com.atguigu.demo.service;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * Author: YZG
 * Date: 2022/9/13 20:25
 * Description: 
 */
@Component
@FeignClient("service-order")
public interface OrderFeignService {

    @ApiOperation("查询订单支付状态")
    @GetMapping("orderService/order/isBuy/{courseId}/{memberId}")
    boolean isBuy(@PathVariable("courseId") String courseId, @PathVariable(value = "memberId",required = false) String memberId);
}
