package com.atguigu.ucenter.service;

import com.atguigu.commonutils.R;
import com.atguigu.ucenter.entity.RegisterVo;
import com.atguigu.ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 杨照光
* @description 针对表【ucenter_member(会员表)】的数据库操作Service
* @createDate 2022-09-04 11:36:08
*/
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);


    R register(RegisterVo registerVo);


    String callback(String code, String state);

    String getQRCode();

    // 统计某天的注册人数
    Integer countRegister(String date);
}
