package com.atguigu.order.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.atguigu.commonutils.HttpClient;
import com.atguigu.commonutils.orderVo.PayLogQuery;
import com.atguigu.order.entity.Order;
import com.atguigu.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.order.entity.PayLog;
import com.atguigu.order.service.PayLogService;
import com.atguigu.order.mapper.PayLogMapper;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 杨照光
 * @description 针对表【t_pay_log(支付日志表)】的数据库操作Service实现
 * @createDate 2022-09-08 12:50:44
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog>
        implements PayLogService {

    @Autowired
    private OrderService orderService;

    /**
     * @description 生成微信支付二维码
     * @date 2022/9/10 19:02
     * @param orderNo
     * @return java.util.Map<java.lang.String, java.lang.String>
     */
    @Override
    public Map createNative(String orderNo) {
        try {
            // 1.查询出订单信息
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(queryWrapper);

            Map<String, String> m = new HashMap();
            //2、设置支付参数
            m.put("appid", "wx74862e0dfcf69954"); // 设置 appid
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr()); // 生成字符串，根据这个字符串规则生成二维码
            m.put("body", order.getCourseTitle());
            m.put("out_trade_no", orderNo);
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("trade_type", "NATIVE");


            //3、HTTPClient来根据URL访问第三方接口并且传递参数
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");


            //client设置参数，参数类型是 xml 格式
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //4、返回第三方的数据，getContent(): 获取响应中的数据。返回类型是 xml 格式
            String xml = client.getContent();
            // 将 xml 转换成 map 集合
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);


            //5、封装返回结果集
            Map map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee()); // 价格
            map.put("result_code", resultMap.get("result_code")); // 返回二维码操作的状态码
            map.put("code_url", resultMap.get("code_url")); // 二维码地址

            //微信支付二维码2小时过期，可采取2小时未支付取消订单
            //redisTemplate.opsForValue().set(orderNo, map, 120, TimeUnit.MINUTES);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @description 查询订单支付状态
     * @date 2022/9/10 19:42
     * @param orderNo
     * @return java.util.Map<java.lang.String, java.lang.String>
     */
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求。固定地址
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            //4、转成Map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //5、返回
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @description 插入数据 并且修改支付状态
     * @date 2022/9/10 19:44
     * @param map
     * @return void
     */
    @Override
    public void UpdateOrderStatus(Map<String, String> map) {
        // 获取订单号
        String orderNo = map.get("out_trade_no");

        // 查询订单信息
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(queryWrapper);

        // 修改支付状态,如果是已支付，就不用修改
        if (order.getStatus() == 1) return;
        order.setStatus(1);
        orderService.updateById(order);

        //记录支付日志
        PayLog payLog = new PayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id")); // 流水号
        payLog.setAttr(JSONObject.toJSONString(map)); // 其余属性

        baseMapper.insert(payLog);//插入到支付日志表
    }

    @Override
    public Page<PayLog> pageQuery(long current, long size, PayLogQuery payLogQuery) {
        // 创建分页对象
        Page<PayLog> pagePayLog = new Page<>(current, size);
        QueryWrapper<PayLog> queryWrapper = new QueryWrapper<>();

        Date payTime = null;
        Integer payType = null;

        if (payLogQuery != null) {
            payTime = payLogQuery.getPay_time();
            payType = payLogQuery.getPayType();
        }
        // 获取条件
        // 使用 condition 组装条件
        queryWrapper
                .ge(payTime != null, "pay_time", payTime)
                .eq(payType != null, "pay_type", payType);
        //根据创建时间排序
        queryWrapper.orderByDesc("gmt_create");

        this.page(pagePayLog, queryWrapper);
        // 返回总页数 和 分页数据
        return pagePayLog;
    }
}




