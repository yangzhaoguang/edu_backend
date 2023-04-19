package com.atguigu.order.service;

import com.atguigu.commonutils.orderVo.PayLogQuery;
import com.atguigu.order.entity.Order;
import com.atguigu.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author 杨照光
* @description 针对表【t_pay_log(支付日志表)】的数据库操作Service
* @createDate 2022-09-08 12:50:44
*/
public interface PayLogService extends IService<PayLog> {

    // 生成订单
    Map createNative(String orderNo);

    // 查询支付状态
    Map<String, String> queryPayStatus(String orderNo);

    // 修改支付状态，插入数据
    void UpdateOrderStatus(Map<String, String> map);


    Page<PayLog> pageQuery(long current, long size, PayLogQuery payLogQuery);
}
