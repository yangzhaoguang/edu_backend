package com.atguigu.ucenter.mapper;

import com.atguigu.ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 杨照光
* @description 针对表【ucenter_member(会员表)】的数据库操作Mapper
* @createDate 2022-09-04 11:36:08
* @Entity com.atguigu.cms.entity.UcenterMember
*/
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    // 统计某天的注册人数
    Integer countRegister(String date);
}




