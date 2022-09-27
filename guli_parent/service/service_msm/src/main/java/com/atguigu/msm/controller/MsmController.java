package com.atguigu.msm.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msm.service.MsmService;
import com.atguigu.msm.utils.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 * Author: YZG
 * Date: 2022/9/1 19:17
 * Description: 
 */
//@CrossOrigin
@RequestMapping("/msmService/msm")
@RestController
public class MsmController {

    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @description 向手机发送短信验证码
     * @date 2022/9/3 21:33
     * @param phone
     * @return com.atguigu.commonutils.R
     */
    @ApiOperation("发送验证码")
    @GetMapping("/sendCode/{phone}")
    private R sendCode(@PathVariable String phone) {
        // 从 redis 取出验证码
        String code1 = (String) redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code1)) {
            R.ok();
        }
        // 随机生成验证码
        String code = RandomUtil.getSixBitRandom();
        boolean result = msmService.send(code, phone);

        if (result) {
            // 将 code 存入redis 并设置过期时间为 5 分钟
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("发送短信失败");
        }
    }
}
