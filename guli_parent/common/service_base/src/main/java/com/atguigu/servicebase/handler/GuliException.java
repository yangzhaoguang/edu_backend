package com.atguigu.servicebase.handler;

import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/6 17:34
 * Description:
 */
@Api("自定义异常~")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException{

    // 状态码
    private Integer code ;
    // 错误信息
    private String msg ;
}
