package com.atguigu.order.controller;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.orderVo.OrderQuery;
import com.atguigu.commonutils.orderVo.PayLogQuery;
import com.atguigu.order.entity.Order;
import com.atguigu.order.entity.PayLog;
import com.atguigu.order.service.PayLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *
 * Author: YZG
 * Date: 2022/9/8 12:53
 * Description: 
 */
@RequestMapping("orderService/payLog")
//@CrossOrigin
@RestController
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @GetMapping("createNative/{orderNo}")
    @ApiOperation("生成微信支付二维码")
    private R createNative(@PathVariable String orderNo) {
        // 生成支付二维码，并将信息保存到 map 集合中
        Map map = payLogService.createNative(orderNo);
        System.out.println("----------" + map);
        return  R.ok().data(map);

    }

    @GetMapping("queryPayStatus/{orderNo}")
    @ApiOperation("查询订单支付状态")
    private R queryPayStatus(@PathVariable String orderNo) {
        // 请求微信提供的固定地址，查看支付状态
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("********" + map);
        if (map.isEmpty()) {
            // 支付失败
            return  R.error().message("支付失败");
        }else if (map.get("trade_state").equals("SUCCESS")){
            // 支付成功
            // 向 pay_log 表中插入数据，并修改 order 表中的支付状态
            payLogService.UpdateOrderStatus(map);
            return R.ok().message("支付成功");
        }else{
            return R.error().message("支付中....");
        }
    }

    /*
     * 查询支付日志
     * */
    @ApiOperation("多条件组合查询分页功能")
    @PostMapping("/pageQuery/{current}/{size}")
    public R pageQuery(
            @PathVariable(required = false) long current,
            @PathVariable(required = false) long size,
            @RequestBody(required = false) PayLogQuery payLogQuery
    ) {
        Page<PayLog> pagePayLog =  payLogService.pageQuery(current,size,payLogQuery);

        return R.ok().data("page",pagePayLog);
    }
}
