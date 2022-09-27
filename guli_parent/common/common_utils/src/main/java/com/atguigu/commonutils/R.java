package com.atguigu.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/5 16:54
 * Description:
 */
@Data

public class R {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    // 把 构造方法私有化的作用： 除本类外不允许 new
    private R (){}

    // 成功的返回方法
    // 加上 static 可通过 类名. 的方式调用
    public static R ok(){
        R r = new R();
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        r.setSuccess(true);
        return  r;
    }

    // 失败的返回方法
    // 加上 static 可通过 类名. 的方式调用
    public static R error(){
        R r = new R();
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        r.setSuccess(false);
        return  r;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
