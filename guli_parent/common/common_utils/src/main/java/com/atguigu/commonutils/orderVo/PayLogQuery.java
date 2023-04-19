package com.atguigu.commonutils.orderVo;

import lombok.Data;

import java.util.Date;

/**
 *
 * Author: YZG
 * Date: 2023/4/17 11:40
 * Description: 
 */
@Data
public class PayLogQuery {

    // 支付时间
    private Date pay_time;

    // 支付类型
    private Integer payType;
}
