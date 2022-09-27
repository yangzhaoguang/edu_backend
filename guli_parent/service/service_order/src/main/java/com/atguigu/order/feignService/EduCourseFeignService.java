package com.atguigu.order.feignService;

import com.atguigu.commonutils.orderVo.CourseOrderVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * Author: YZG
 * Date: 2022/9/8 16:07
 * Description: 
 */
@FeignClient("service-edu")
@Service
public interface EduCourseFeignService {

    @ApiOperation("根据课程 id 查询课程")
    @GetMapping("/eduservice/courseFront/getCourseById/{courseId}")
    // 如果是路径参数，PathVariable 里必须加上参数名
    CourseOrderVo getCourseById(@PathVariable("courseId") String courseId);
}
