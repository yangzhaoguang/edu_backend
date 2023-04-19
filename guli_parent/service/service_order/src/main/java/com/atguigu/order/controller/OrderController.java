package com.atguigu.order.controller;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.orderVo.OrderQuery;
import com.atguigu.order.entity.Order;
import com.atguigu.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * Author: YZG
 * Date: 2022/9/8 12:53
 * Description: 
 */
@RequestMapping("orderService/order")
//@CrossOrigin
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("生成订单")
    @PostMapping("createOrder/{courseId}")
    private R createOrder(@PathVariable String courseId, HttpServletRequest request) {
        // 返回订单号
        String orderNo = orderService.saveOrder(courseId,request);
        return R.ok().data("orderNo",orderNo);
    }


    @ApiOperation("根据订单号查询订单信息")
    @GetMapping("getOderByOrderNo/{orderNo}")
    private R getOrderByOderNo(@PathVariable String orderNo) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(orderQueryWrapper);

        return R.ok().data("order",order);
    }

    @ApiOperation("查询订单支付状态")
    @GetMapping("isBuy/{courseId}/{memberId}")
    private boolean isBuy(@PathVariable String courseId,@PathVariable(required = false) String memberId) {
        // true : 已支付  false ： 未支付
        return orderService.isBuy(courseId,memberId);
    }

    /*
    * 查询订单
    * */
    @ApiOperation("多条件组合查询分页功能")
    @PostMapping("/pageQuery/{current}/{size}")
    public R pageQuery(
            @PathVariable(required = false) long current,
            @PathVariable(required = false) long size,
            @RequestBody(required = false) OrderQuery orderQuery
    ) {
        Page<Order> pageTeacher =  orderService.pageQuery(current,size,orderQuery);

        return R.ok().data("page",pageTeacher);
    }

}
