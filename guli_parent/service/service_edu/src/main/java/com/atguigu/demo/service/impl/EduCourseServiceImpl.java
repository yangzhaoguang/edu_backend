package com.atguigu.demo.service.impl;

import com.atguigu.demo.entity.EduChapter;
import com.atguigu.demo.entity.EduCourseDescription;
import com.atguigu.demo.entity.EduVideo;
import com.atguigu.demo.entity.frontVo.CourseFrontVo;
import com.atguigu.demo.entity.frontVo.CourseWebVo;
import com.atguigu.demo.entity.vo.CourseInfoVo;
import com.atguigu.demo.entity.vo.CoursePublishVo;
import com.atguigu.demo.entity.vo.CourseQuery;
import com.atguigu.demo.service.*;
import com.atguigu.servicebase.handler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.demo.entity.EduCourse;
import com.atguigu.demo.mapper.EduCourseMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 杨照光
 * @description 针对表【edu_course(课程)】的数据库操作Service实现
 * @createDate 2022-08-16 14:00:39
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse>
        implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private VodFeignService vodFeignService;

    @Autowired
    private EduCourseMapper courseMapper;

    /**
     *
     * @param courseInfoVo 封装前端传过来的课程信息
     * @return
     */
    @Override
    public String saveCourse(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        // 1. 保存课程信息到数据库
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        if (!this.save(eduCourse)) {
            throw new GuliException(20001, "保存失败");
        }
        // 2. 保存课程简介到数据库
        EduCourseDescription courseDescription = new EduCourseDescription();
        // 将课程简介保存到 EduCourseDescription 实体类中
        courseDescription.setDescription(courseInfoVo.getDescription());
        // 赋值 EduCourse 的 ID，实现关联
        courseDescription.setId(eduCourse.getId());
        eduCourseDescriptionService.save(courseDescription);

        return eduCourse.getId();
    }

    /**
     * TODO 根据 courseId 查询课程信息
     * @date 2022/8/18 17:32
     * @param courseId
     * @return com.atguigu.demo.entity.vo.CourseInfoVo
     */
    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        // 1. 根据 courseId 查询课程信息
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        // 2. 根据 courseId 查询课程简介
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        // 判断一下描述是否为空
        String description = "";
        if (null == eduCourseDescription) {
            description = "无简介";
        } else {
            description = eduCourseDescription.getDescription();
        }
        courseInfoVo.setDescription(description);

        return courseInfoVo;
    }

    /**
     * TODO 修改课程信息
     * @date 2022/8/18 17:34
     * @param courseInfoVo
     * @return void
     */
    @Override
    public void updateCourse(CourseInfoVo courseInfoVo) {
        // 1.修改课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int i = baseMapper.updateById(eduCourse);
        // 2. 修改课程简介
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        boolean b = eduCourseDescriptionService.updateById(description);

        if (i == 0 || !b) {
            throw new GuliException(20001, "修改失败");
        }
    }

    /**
     * TODO 课程消息确认
     * @date 2022/8/22 20:59
     * @param id
     * @return com.atguigu.demo.entity.vo.CoursePublishVo
     */
    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        return baseMapper.getCoursePublishInfo(id);
    }

    /**
     * TODO
     * @date 2022/8/24 15:15
     * @param current
     * @param size
     * @param courseQuery
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.atguigu.demo.entity.EduCourse>
     */
    @Override
    public Page<EduCourse> pageQueryCourse(long current, long size, CourseQuery courseQuery) {

        Page<EduCourse> page = new Page<EduCourse>(current, size);
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        // 取出查询条件
        String status = courseQuery.getStatus();
        String title = courseQuery.getTitle();
        String gmtCreate = courseQuery.getGmtCreate();
        // 开始组装条件
        // 如果 status != null , 就判断传过来的是 已发布 还是 未发布
        queryWrapper.like(null != status, "status", status)
                .like(null != title, "title", title)
                .ge(null != gmtCreate, "gmt_create", gmtCreate)
                .orderByDesc("gmt_create");
        this.page(page, queryWrapper);

        return page;
    }

    /**
     * 根据课程 Id 删除课程信息【视频，小节，章节，描述】
     * @date 2022/8/24 18:42
     * @param courseId
     * @return com.atguigu.commonutils.R
     */
    @Override
    public void deleteCourse(String courseId) {

        //1.删除小节 以及 视频
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        // 获取课程下的所有小节
        List<EduVideo> videos = eduVideoService.list(videoQueryWrapper);
        // 保存视频 Id
        List<String> videoIds = new ArrayList<>();
        for (EduVideo video : videos) {
            if (!StringUtils.isEmpty(video.getVideoSourceId())) {
                // 取出每小节的视频 ID 放入集合
                videoIds.add(video.getVideoSourceId());
            }
        }
        // 删除视频
        vodFeignService.deleteBatch(videoIds);
        // 删除小节
        eduVideoService.remove(videoQueryWrapper);

        //2.删除章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        eduChapterService.remove(chapterQueryWrapper);
        //3.删除描述信息
        eduCourseDescriptionService.removeById(courseId);
        //4.删除课程信息
        this.removeById(courseId);
    }

    /**
     * @description 前台条件分页查询课程信息
     * @date 2022/9/6 15:33
     * @param current
     * @param limit
     * @param courseFrontVo
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    @Override
    public Map<String, Object> getCourseFrontList(long current, long limit, CourseFrontVo courseFrontVo) {
        Page<EduCourse> page = new Page<>(current, limit);

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        // 组装条件查询
        queryWrapper.eq(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId()), "subject_parent_id", courseFrontVo.getSubjectParentId())
                .eq(!StringUtils.isEmpty(courseFrontVo.getSubjectId()), "subject_id", courseFrontVo.getSubjectId())
                .orderByDesc(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort()), "buy_count")
                .orderByDesc(!StringUtils.isEmpty(courseFrontVo.getPriceSort()), "price")
                .orderByDesc(!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort()), "gmt_create")
                // 查询已发布的课程
                .eq("status","Normal");

        this.page(page, queryWrapper);

        List<EduCourse> records = page.getRecords();
        long pageCurrent = page.getCurrent();
        long pages = page.getPages();
        long size = page.getSize();
        long total = page.getTotal();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", pageCurrent);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    /**
     * @description 根据课程 id 查询课程详情
     * @date 2022/9/6 22:01
     * @param courseId
     * @return java.util.List<com.atguigu.demo.entity.frontVo.CourseWebVo>
     */
    @Override
    public CourseWebVo getCourseFrontInfo(String courseId) {

        return  courseMapper.getCourseFrontInfo(courseId);
    }
}




