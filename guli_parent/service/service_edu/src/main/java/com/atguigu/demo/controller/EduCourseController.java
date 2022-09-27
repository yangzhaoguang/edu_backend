package com.atguigu.demo.controller;

import com.atguigu.commonutils.R;
import com.atguigu.demo.entity.EduCourse;
import com.atguigu.demo.entity.courseEnum.CoursePublishStatus;
import com.atguigu.demo.entity.vo.CourseInfoVo;
import com.atguigu.demo.entity.vo.CoursePublishVo;
import com.atguigu.demo.entity.vo.CourseQuery;
import com.atguigu.demo.service.impl.EduCourseServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/16 14:09
 * Description:
 */
@Api("课程管理")
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseServiceImpl eduCourseService;

    /**
     * TODO 根据课程 ID 删除课程信息
     * @date 2022/8/24 18:42
     * @param courseId
     * @return com.atguigu.commonutils.R
     */
    @ApiOperation("删除课程信息")
    @DeleteMapping("deleteCourse/{courseId}")
    private R deleteCourse(@PathVariable String courseId){
        eduCourseService.deleteCourse(courseId);
        return  R.ok();
    }

    /**
     * 分页查询带条件
     * @date 2022/8/24 15:02
     * @param current 当前页
     * @param limit 每页显示数据条数
     * @param courseQuery 查询条件
     * @return com.atguigu.commonutils.R
     */
    @ApiOperation("条件查询带分页")
    @PostMapping("pageQuery/{current}/{limit}")
    private R pageQuery(@PathVariable(required = false) long current,
                        @PathVariable(required = false) long limit,
                        @RequestBody(required = false) CourseQuery courseQuery) {

        Page<EduCourse> page = eduCourseService.pageQueryCourse(current,limit,courseQuery);

        return R.ok().data("rows",page.getRecords()).data("total",page.getTotal());
    }

    @ApiOperation("增加课程信息")
    @PostMapping("addCourse")
    private R addCourse(@RequestBody CourseInfoVo courseInfoVo) {
        // 增加课程基本信息
        // 返回课程 ID，方便后序做 课程大纲以及修改课程信息使用
        String courseId = eduCourseService.saveCourse(courseInfoVo);
        return R.ok().data("courseId", courseId);
    }

    @ApiOperation("根据 courseId 查询课程信息")
    @GetMapping("getCourseInfoById/{courseId}")
    private R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfoById(courseId);
        System.out.println(courseInfoVo.getLessonNum());
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    @ApiOperation("修改课程信息")
    @PostMapping("updateCourse")
    private R getCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourse(courseInfoVo);
        return R.ok();
    }

    @ApiOperation("课程消息确认")
    @GetMapping("getPublishCourseInfo/{id}")
    private R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo vo = eduCourseService.getPublishCourseInfo(id);
        return R.ok().data("coursePublish", vo);
    }

    @ApiOperation("课程最终发布")
    @PostMapping("coursePublish/{courseId}")
    private R coursePublish(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        // 设置发布状态
        eduCourse.setStatus("Normal");
        // 修改
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }
}
