package com.atguigu.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.demo.entity.EduTeacher;
import com.atguigu.demo.service.EduTeacherService;
import com.atguigu.demo.mapper.EduTeacherMapper;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 杨照光
 * @description 针对表【edu_teacher(讲师)】的数据库操作Service实现
 * @createDate 2022-08-16 14:00:39
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher>
        implements EduTeacherService {

    /**
     * @description 分页查询讲师列表
     * @date 2022/9/6 9:11
     * @param current 当前页
     * @param limit 每页显示数据
     * @return java.util.HashMap<java.lang.String, java.lang.Object>
     */
    @Override
    public Map<String, Object> pageTeacher(long current, long limit) {
        Page<EduTeacher> eduTeacherPage = new Page<>(current,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        this.page(eduTeacherPage,wrapper);


        long current1 = eduTeacherPage.getCurrent();
        List<EduTeacher> records = eduTeacherPage.getRecords();
        long total = eduTeacherPage.getTotal();
        long size = eduTeacherPage.getSize();
        long pages = eduTeacherPage.getPages();

        boolean hasNext = eduTeacherPage.hasNext();
        boolean hasPrevious = eduTeacherPage.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
}




