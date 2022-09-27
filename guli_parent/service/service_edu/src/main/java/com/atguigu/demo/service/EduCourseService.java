package com.atguigu.demo.service;

import com.atguigu.demo.entity.EduCourse;
import com.atguigu.demo.entity.frontVo.CourseFrontVo;
import com.atguigu.demo.entity.frontVo.CourseWebVo;
import com.atguigu.demo.entity.vo.CourseInfoVo;
import com.atguigu.demo.entity.vo.CoursePublishVo;
import com.atguigu.demo.entity.vo.CourseQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author 杨照光
 * @Description 针对表【edu_course(课程)】的数据库操作Service
 * @createDate 2022-08-16 14:00:39
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * TODO
     * @date 2022/8/16 16:36
     * @param courseInfoVo 封装前端传过来的课程信息
     * @return void 返回值
     */
    String saveCourse(CourseInfoVo courseInfoVo);

    /**
     * TODO 根据 courseId 查询课程信息
     * @date 2022/8/18 17:30
     * @param courseId
     * @return com.atguigu.demo.entity.vo.CourseInfoVo
     */
    CourseInfoVo getCourseInfoById(String courseId);

    /**
     * TODO 修改课程信息
     * @date 2022/8/18 17:34
     * @param courseInfoVo
     * @return void
     */
    void updateCourse(CourseInfoVo courseInfoVo);

    /**
     * TODO 课程消息确认
     * @date 2022/8/22 20:59
     * @param id
     * @return com.atguigu.demo.entity.vo.CoursePublishVo
     */
    CoursePublishVo getPublishCourseInfo(String id);

    /**
     * TODO 分页查询课程信息，带条件查询
     * @date 2022/8/24 15:14
     * @param current
     * @param size
     * @param courseQuery
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.atguigu.demo.entity.EduCourse>
     */
    Page<EduCourse> pageQueryCourse(long current, long size, CourseQuery courseQuery);

    /**
     * TODO 根据课程 ID 删除课程信息
     * @date 2022/8/24 18:42
     * @param courseId
     * @return com.atguigu.commonutils.R
     */
    void deleteCourse(String courseId);


    Map<String, Object> getCourseFrontList(long page, long limit, CourseFrontVo courseFrontVo);

    /**
     * @description 查询课程详情
     * @date 2022/9/6 21:59
     * @param courseId
     * @return java.util.List<com.atguigu.demo.entity.frontVo.CourseWebVo>
     */
    CourseWebVo getCourseFrontInfo(String courseId);
}
