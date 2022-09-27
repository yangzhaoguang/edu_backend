package com.atguigu.demo.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.demo.entity.EduCourse;
import com.atguigu.demo.entity.EduTeacher;
import com.atguigu.demo.service.EduCourseService;
import com.atguigu.demo.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台管理
 * Author: YZG
 * Date: 2022/8/29 15:30
 * Description: 
 */
@RestController
@RequestMapping("/eduservice/front")
//@CrossOrigin
public class IndexController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;


    /**
     * @description 查询前 8 条热门课程，前 4 条热门讲师
     * @date 2022/8/29 15:32
     * @param
     * @return com.atguigu.commonutils.R
     */
    @GetMapping("index")
    private R getData() {

        // 查询课程
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        // 根据 ID 降序
        courseQueryWrapper.orderByDesc("id");
        // last 可以在 后面拼接 sql 语句
        courseQueryWrapper.last("limit 8");
        // 显示已发布的课程
        courseQueryWrapper.eq("status","Normal");
        List<EduCourse> eduCourseList = eduCourseService.list(courseQueryWrapper);

        // 查询讲师
        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
        // 根据 ID 降序
        teacherQueryWrapper.orderByDesc("id");
        // last 可以在 后面拼接 sql 语句
        teacherQueryWrapper.last("limit 4");
        List<EduTeacher> eduTeacherList = eduTeacherService.list(teacherQueryWrapper);


        return  R.ok().data("courseList",eduCourseList).data("teacherList",eduTeacherList);
    }
}
