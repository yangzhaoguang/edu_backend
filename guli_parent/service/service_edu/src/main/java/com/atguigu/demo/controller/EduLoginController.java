package com.atguigu.demo.controller;

import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/11 13:00
 * Description:
 */
@Api("登录功能")
@RestController
@RequestMapping("eduservice/user")
//@CrossOrigin // 解决跨域问题
public class EduLoginController {

    @ApiOperation("登录")
    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @ApiOperation("登陆之后获取信息")
    @GetMapping("info")
    public R getInfo() {
        return R.ok().data("name", "admin")
                .data("roles", "[admin]")
                .data("avatar", "https://pic4.zhimg.com/80/v2-bd40bafe254de89392bf753cb109f64f_720w.jpg");
    }


}
