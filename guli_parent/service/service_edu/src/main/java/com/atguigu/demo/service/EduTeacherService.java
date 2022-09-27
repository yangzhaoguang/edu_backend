package com.atguigu.demo.service;

import com.atguigu.demo.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.Map;

/**
* @author 杨照光
* @description 针对表【edu_teacher(讲师)】的数据库操作Service
* @createDate 2022-08-16 14:00:39
*/
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> pageTeacher(long current, long limit);
}
