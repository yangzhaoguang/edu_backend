package com.atguigu.demo.mapper;

import com.atguigu.demo.entity.EduCourse;
import com.atguigu.demo.entity.frontVo.CourseWebVo;
import com.atguigu.demo.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 杨照光
 * @description 针对表【edu_course(课程)】的数据库操作Mapper
 * @createDate 2022-08-16 14:00:39
 * @Entity com.atguigu.demo.entity.EduCourse
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     * TODO 发布课程消息确认
     * @date 2022/8/22 21:20
     * @param courseId
     * @return com.atguigu.demo.entity.vo.CoursePublishVo
     */
    CoursePublishVo getCoursePublishInfo(@Param("courseId") String courseId);

    /**
     * @description 查询课程详情
     * @date 2022/9/6 21:59
     * @param courseId
     * @return java.util.List<com.atguigu.demo.entity.frontVo.CourseWebVo>
     */
    CourseWebVo getCourseFrontInfo(String courseId);
}




