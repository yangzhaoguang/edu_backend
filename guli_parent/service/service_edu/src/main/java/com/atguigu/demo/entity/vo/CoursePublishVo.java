package com.atguigu.demo.entity.vo;

import lombok.Data;

/**
 *
 * Author: YZG
 * Date: 2022/8/22 20:39
 * Description: 
 */
@Data
public class CoursePublishVo {
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
