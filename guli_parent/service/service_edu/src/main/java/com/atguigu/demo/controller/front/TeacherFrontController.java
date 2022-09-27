package com.atguigu.demo.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.demo.entity.EduCourse;
import com.atguigu.demo.entity.EduTeacher;
import com.atguigu.demo.service.EduCourseService;
import com.atguigu.demo.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Author: YZG
 * Date: 2022/9/6 9:06
 * Description: 
 */
@RestController
@RequestMapping("/eduservice/teacherFront")
//@CrossOrigin
@ApiModel(value = "前台讲师模块" ,description = "前台讲师模块")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    @ApiOperation("分页查询讲师列表")
    @PostMapping("/pageTeacher/{current}/{limit}")
    private R pageTeacher(@PathVariable long current, @PathVariable long limit) {
        // 分页查询讲师列表，将所有数据封装成 map 集合
        Map<String, Object> pageData = teacherService.pageTeacher(current,limit);

        return R.ok().data(pageData);
    }

    @ApiOperation("讲师详情")
    @GetMapping("teacherInfoFront/{teacherId}")
    private R getTeacherInfoFront(@PathVariable String teacherId){
    //    1.根据教师id查询教师
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        //    2.根据教师id查询课程
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = courseService.list(queryWrapper);

        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }

}
