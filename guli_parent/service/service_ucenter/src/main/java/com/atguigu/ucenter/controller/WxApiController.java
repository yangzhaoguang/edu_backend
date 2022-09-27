package com.atguigu.ucenter.controller;

import com.atguigu.commonutils.HttpClientUtils;
import com.atguigu.commonutils.JwtUtils;
import com.atguigu.servicebase.handler.GuliException;
import com.atguigu.ucenter.entity.UcenterMember;
import com.atguigu.ucenter.entity.WXConstantPropertiesUtil;
import com.atguigu.ucenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Author: YZG
 * Date: 2022/9/5 12:38
 * Description: 
 */
@RequestMapping("/api/ucenter/wx")
//@CrossOrigin
@Controller
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation("获取扫描人的信息")
    @GetMapping("callback")
    private String getInfo(String code, String state) {
        // 获取用户信息，并保存到 token 中
        String token = memberService.callback(code,state);
        return "redirect:http://localhost:3000?token=" + token;
    }

    /**
     * @description 获取微信登录二维码
     * @date 2022/9/5 12:40
     * @param
     * @return java.lang.String
     */
    @ApiOperation("获取二维码")
    @GetMapping("login")
    private String getCode() {
        // 向 微信平台 提供的固定地址发送请求，获取微信登录二维码
        String redirect_url = memberService.getQRCode();
        return "redirect:" + redirect_url;
    }
}
