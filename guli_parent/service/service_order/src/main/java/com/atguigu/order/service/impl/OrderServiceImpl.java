package com.atguigu.order.service.impl;
import java.math.BigDecimal;
import java.util.Date;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.OrderNoUtil;
import com.atguigu.commonutils.orderVo.CourseOrderVo;
import com.atguigu.commonutils.orderVo.UcenterOrderVo;
import com.atguigu.order.feignService.EduCourseFeignService;
import com.atguigu.order.feignService.UcenterFeignService;
import com.atguigu.servicebase.handler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.order.entity.Order;
import com.atguigu.order.service.OrderService;
import com.atguigu.order.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 杨照光
 * @description 针对表【t_order(订单)】的数据库操作Service实现
 * @createDate 2022-09-08 12:50:32
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {

    @Autowired
    private EduCourseFeignService courseFeignService;
    @Autowired
    private UcenterFeignService ucenterFeignService;
    /**
     * @description 生成订单
     * @date 2022/9/8 16:14
     * @param courseId
     * @param request
     * @return java.lang.String
     */
    @Override
    public String saveOrder(String courseId, HttpServletRequest request) {
        // 根据 request 中的token 获取用户 id
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(userId)) {
            throw new GuliException(20001,"用户未登录");
        }
        // 根据课程 id 查询课程信息
        CourseOrderVo courseOrderVo = courseFeignService.getCourseById(courseId);
        // 根据用户 id 查询用户信息
        UcenterOrderVo ucenterOrderVo = ucenterFeignService.getUserInfoById(userId);
        // 创建订单对象
        Order order = new Order();
        //创建订单
        order.setOrderNo(OrderNoUtil.getOrderNo()); // 使用 OrderNoUtils 生成订单号
        order.setCourseId(courseId);
        order.setCourseTitle(courseOrderVo.getTitle());
        order.setCourseCover(courseOrderVo.getCover());
        order.setTeacherName("test");
        order.setTotalFee(courseOrderVo.getPrice());
        order.setMemberId(userId);
        order.setMobile(ucenterOrderVo.getMobile());
        order.setNickname(ucenterOrderVo.getNickname());

        order.setStatus(0); // 支付状态 -- 0：未支付
        order.setPayType(1); // 支付类型 -- 1：微信支付
        baseMapper.insert(order);


        return order.getOrderNo();
    }


    @Override
    public boolean isBuy(String courseId, String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.eq("member_id",memberId);
        queryWrapper.eq("status",1);
        Order order = this.getOne(queryWrapper);

        return order != null;
    }
}




