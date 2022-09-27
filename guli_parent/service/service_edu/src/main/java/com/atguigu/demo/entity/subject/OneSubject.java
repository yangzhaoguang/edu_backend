package com.atguigu.demo.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/15 16:28
 * Description:
 */
// 一级分类
@Data
public class OneSubject {
    private String id ;
    private String title;

    // 一级分类包含多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
