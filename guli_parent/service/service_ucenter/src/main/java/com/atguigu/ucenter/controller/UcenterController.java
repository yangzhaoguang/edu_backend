package com.atguigu.ucenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.orderVo.UcenterOrderVo;
import com.atguigu.ucenter.entity.RegisterVo;
import com.atguigu.ucenter.entity.UcenterMember;
import com.atguigu.ucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Author: YZG
 * Date: 2022/9/4 11:37
 * Description: 
 */
@RequestMapping("ucenterService/ucenter")
//@CrossOrigin
@RestController
public class UcenterController {

    @Autowired
    private UcenterMemberService memberService;

    /**
     * @description 前台进行登录
     * @date 2022/9/4 12:21
     * @param member
     * @return com.atguigu.commonutils.R
     */
    @PostMapping("frontLogin")
    @ApiOperation("前台登录")
    private R loginUser(@RequestBody UcenterMember member) {
        // 登录成功返回一个 token，使用 jwt 生成
        String token = memberService.login(member);
        return R.ok().data("token", token);
    }

    @PostMapping("frontRegister")
    @ApiOperation("前台注册")
    private R registerUser(@RequestBody RegisterVo registerVo) {

        return memberService.register(registerVo);
    }


    @GetMapping("getUserInfo")
    @ApiOperation("根据token获取用户信息")
    private R getUserInfoByToken(HttpServletRequest request) {

        // 根据 request 获取用户 ID
        String userID = JwtUtils.getMemberIdByJwtToken(request);

        UcenterMember member = memberService.getById(userID);

        return R.ok().data("userInfo", member);
    }

    /**
     * @description 供 service_order 模块远程调用
     * @date 2022/9/7 17:40
     * @param userId
     * @return com.atguigu.ucenter.entity.UcenterMember
     */
    @GetMapping("getUserInfoById/{userId}")
    @ApiOperation("根据用户id获取用户信息")
    private UcenterOrderVo getUserInfoById(@PathVariable("userId") String userId) {
        UcenterMember ucenterMember = memberService.getById(userId);
        UcenterOrderVo ucenterOrderVo = new UcenterOrderVo();
        BeanUtils.copyProperties(ucenterMember,ucenterOrderVo);

        return ucenterOrderVo;
    }


    @ApiOperation("统计某天的注册人数")
    @GetMapping("countRegister/{date}")
    private R countRegister(@PathVariable String date) {
        Integer count = memberService.countRegister(date);
        return R.ok().data("count",count);
    }



}
