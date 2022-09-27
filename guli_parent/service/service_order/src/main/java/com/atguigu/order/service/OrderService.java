package com.atguigu.order.service;

import com.atguigu.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 杨照光
* @description 针对表【t_order(订单)】的数据库操作Service
* @createDate 2022-09-08 12:50:32
*/
public interface OrderService extends IService<Order> {

    String saveOrder(String courseId, HttpServletRequest request);

    // 根据课程id、用户id 查询订单支付状态
    boolean isBuy(String courseId, String orderNo);
}
