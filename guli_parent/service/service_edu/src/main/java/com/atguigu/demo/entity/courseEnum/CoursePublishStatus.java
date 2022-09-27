package com.atguigu.demo.entity.courseEnum;

import com.baomidou.mybatisplus.annotation.EnumValue;

// 发布课程状态
public enum CoursePublishStatus {
    PUBLISH_STATUS("Normal"),
    NOT_PUBLISH_STATUS("Draft");

    @EnumValue
    private String status;

    CoursePublishStatus(String status) {
    }

}
