package com.atguigu.commonutils.orderVo;

import lombok.Data;

import java.util.Date;

/**
 *
 * Author: YZG
 * Date: 2023/4/17 10:22
 * Description: 
 */
@Data
public class OrderQuery {

    // 支付状态
    private Integer status;
    // 订单创建时间
    private Date createTime;
    // 会员昵称
    private String username;

}
