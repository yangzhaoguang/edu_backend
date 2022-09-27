package com.atguigu.demo.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.orderVo.CourseOrderVo;
import com.atguigu.demo.entity.EduCourse;
import com.atguigu.demo.entity.EduTeacher;
import com.atguigu.demo.entity.chapter.ChapterVo;
import com.atguigu.demo.entity.frontVo.CourseFrontVo;
import com.atguigu.demo.entity.frontVo.CourseWebVo;
import com.atguigu.demo.service.EduChapterService;
import com.atguigu.demo.service.EduCourseService;
import com.atguigu.demo.service.EduTeacherService;
import com.atguigu.demo.service.OrderFeignService;
import com.atguigu.servicebase.handler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * Author: YZG
 * Date: 2022/9/6 9:06
 * Description: 
 */
@RestController
@RequestMapping("/eduservice/courseFront")
//@CrossOrigin
@ApiModel(value = "前台课程模块", description = "前台课程模块")
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private OrderFeignService orderFeignService;


    @ApiOperation("条件分页查询课程列表")
    @PostMapping("getCourseFrontList/{page}/{limit}")
    private R getCourseFrontList(@PathVariable long page,
                                 @PathVariable long limit,
                                 @RequestBody(required = false) CourseFrontVo courseFrontVo) {

        Map<String, Object> map = courseService.getCourseFrontList(page, limit, courseFrontVo);
        return R.ok().data(map);
    }

    /**
     * @description 根据 courseId 查询课程详情
     * @date 2022/9/6 22:11
     * @param courseId
     * @return com.atguigu.commonutils.R
     */
    @GetMapping("getCourseFrontInfo/{courseId}")
    @ApiOperation("查询课程详情")
    private R getCourseFrontInfo(@PathVariable String courseId, HttpServletRequest request) {
        // 1.查询课程基本信息
        CourseWebVo courseInfo = courseService.getCourseFrontInfo(courseId);
        // 2.查询章节信息
        List<ChapterVo> allChapterVideo = chapterService.getAllChapterVideo(courseId);
        // 3.判断该课程是否已经购买过
        // 用户id 通过 request 请求中的token获取
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            // 这里如果是未登录的话直接 return，就没有保存 courseInfo 等信息了。
            // 所以在这里也要保存 courseInfo 信息。
            return R.error().message("未登录...")
                    .data("courseInfo", courseInfo)
                    .data("chapterVideoList", allChapterVideo)
                    .data("isBuy", false);
        }
        boolean result = orderFeignService.isBuy(courseId, memberId);
        return R.ok()
                .data("courseInfo", courseInfo)
                .data("chapterVideoList", allChapterVideo)
                .data("isBuy", result);
    }

    /**
     * @description 根据课程 id 查询课程信息i，供 order模块调用
     * @date 2022/9/8 15:53
     * @param courseId
     * @return
     */
    @ApiOperation("根据课程 id 查询课程")
    @GetMapping("getCourseById/{courseId}")
    private CourseOrderVo getCourseById(@PathVariable("courseId") String courseId) {
        EduCourse course = courseService.getById(courseId);
        CourseOrderVo courseOrderVo = new CourseOrderVo();
        BeanUtils.copyProperties(course, courseOrderVo);
        return courseOrderVo;
    }
}
