package com.atguigu.demo.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * Author: YZG
 * Date: 2022/8/24 14:57
 * Description: 
 */
@Data
public class CourseQuery {

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "课程发布状态")
    private String status;

    @ApiModelProperty(value = "课程发布时间")
    private String gmtCreate;

}
