package com.atguigu.aclservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.aclservice.entity.UserAccessRecords;
import com.atguigu.aclservice.service.UserAccessRecordsService;
import com.atguigu.aclservice.mapper.UserAccessRecordsMapper;
import org.springframework.stereotype.Service;

/**
* @author YZG
* @description 针对表【user_access_records】的数据库操作Service实现
* @createDate 2023-04-16 12:05:43
*/
@Service
public class UserAccessRecordsServiceImpl extends ServiceImpl<UserAccessRecordsMapper, UserAccessRecords>
    implements UserAccessRecordsService{

}




