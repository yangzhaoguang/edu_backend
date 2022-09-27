package com.atguigu.ucenter.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * Author: YZG
 * Date: 2022/9/4 12:36
 * Description: 
 */
@Data
@Api("注册对象")
public class RegisterVo {
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;
}
