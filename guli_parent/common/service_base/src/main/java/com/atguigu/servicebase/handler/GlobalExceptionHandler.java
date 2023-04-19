package com.atguigu.servicebase.handler;

import com.atguigu.commonutils.ExceptionUtil;
import com.atguigu.commonutils.R;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/6 14:56
 * Description:
 */
@ControllerAdvice // 增强 Controller
@Slf4j
public class GlobalExceptionHandler{

    @ApiOperation("处理所有异常~")
    @ExceptionHandler({Exception.class})
    @ResponseBody  //响应到浏览器上
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("系统管理员正在处理~请稍后再试");
    }

    @ApiOperation("特定异常处理~")
    @ExceptionHandler({ArithmeticException.class})
    @ResponseBody  //响应到浏览器上
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("ArithmeticException异常处理~~~");
    }

    @ApiOperation("处理自定义异常~~")
    @ExceptionHandler({GuliException.class})
    @ResponseBody  //响应到浏览器上
    public R error(GuliException e) {
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
