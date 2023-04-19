package com.atguigu.order.service;

import com.atguigu.commonutils.orderVo.OrderQuery;
import com.atguigu.order.entity.Order;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    /**
     * @description 根据查询条件分页查询
     * @date 2023/4/17 10:27
     * @param current
     * @param size
     * @param orderQuery
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.atguigu.order.entity.Order>
     */
    Page<Order> pageQuery(long current, long size, OrderQuery orderQuery);
}
