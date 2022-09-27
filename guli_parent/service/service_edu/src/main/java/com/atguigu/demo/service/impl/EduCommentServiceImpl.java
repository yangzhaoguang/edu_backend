package com.atguigu.demo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.demo.entity.EduComment;
import com.atguigu.demo.service.EduCommentService;
import com.atguigu.demo.mapper.EduCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 杨照光
 * @description 针对表【edu_comment(评论)】的数据库操作Service实现
 * @createDate 2022-09-07 17:07:53
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment>
        implements EduCommentService {


    /**
     * @description 分页查询评论列表
     * @date 2022/9/7 17:24
     * @param page
     * @param limit
     * @return java.util.Map
     */
    @Override
    public Map<String,Object> pageCommentList(String courseId ,long page, long limit) {
        Page<EduComment> eduCommentPage = new Page<>(page, limit);
        QueryWrapper<EduComment> queryWrapper = new QueryWrapper<>();
        // 根据评论时间排序
        queryWrapper.orderByDesc("gmt_create");
        // 根据课程 id 查询评论
        queryWrapper.eq("course_id",courseId);
        this.page(eduCommentPage, queryWrapper);

        // 封装成 map
        List<EduComment> commentList = eduCommentPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", eduCommentPage.getCurrent());
        map.put("pages", eduCommentPage.getPages());
        map.put("size", eduCommentPage.getSize());
        map.put("total", eduCommentPage.getTotal());
        map.put("hasNext", eduCommentPage.hasNext());
        map.put("hasPrevious", eduCommentPage.hasPrevious());
        return map;
    }
}




