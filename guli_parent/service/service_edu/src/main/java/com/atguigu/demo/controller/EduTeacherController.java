package com.atguigu.demo.controller;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.atguigu.commonutils.R;
import com.atguigu.demo.entity.EduTeacher;
import com.atguigu.demo.entity.vo.TeacherQuery;
import com.atguigu.demo.service.EduTeacherService;
import com.atguigu.servicebase.handler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.util.List;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/5 14:04
 * Description:
 */
@Api("讲师管理")
@RestController
@RequestMapping("eduservice/teacher")
//@CrossOrigin // 解决跨域问题
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation("查询所有教师")
    @GetMapping("/findAll")
    public R findAll() {

        List<EduTeacher> list = eduTeacherService.list();
        return list.isEmpty() ? R.error() : R.ok().data("items", list);

    }

    // 逻辑删除
    @ApiOperation("逻辑删除教师")
    @DeleteMapping("{id}")
    public R removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {

        boolean flag = eduTeacherService.removeById(id);
        return flag ? R.ok() : R.error();

    }

    @ApiOperation("普通分页功能")
    @GetMapping("/pageTeacher/{current}/{size}")
    public R pageTeacher(
            @PathVariable long current,
            @PathVariable long size
    ) {
        // 创建分页对象
        Page<EduTeacher> pageTeacher = new Page<>(current, size);
        eduTeacherService.page(pageTeacher);
        // 返回总页数 和 分页数据
        return R.ok().data("rows", pageTeacher.getRecords()).data("total", pageTeacher.getTotal());
    }


    @ApiOperation("多条件组合查询分页功能")
    @PostMapping("/pageQuery/{current}/{size}")
    public R pageQuery(
            @PathVariable(required = false) long current,
            @PathVariable(required = false) long size,
            @RequestBody(required = false) TeacherQuery teacherQuery
    ) {
        // 创建分页对象
        Page<EduTeacher> pageTeacher = new Page<>(current, size);
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        // 获取条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        // begin ~ end 课程创建时间
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        // 使用 condition 组装条件
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name)
                .eq(level != null, "level", level)
                .ge(StringUtils.isNotBlank(begin), "gmt_create", begin)
                .le(StringUtils.isNotBlank(end), "gmt_create", end);
        //根据创建时间排序
        queryWrapper.orderByDesc("gmt_create");

        eduTeacherService.page(pageTeacher, queryWrapper);
        // 返回总页数 和 分页数据
        return R.ok().data("rows", pageTeacher.getRecords()).data("total", pageTeacher.getTotal());
    }

    @ApiOperation("增加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {

        return eduTeacherService.save(eduTeacher) ? R.ok() : R.error();
    }

    @ApiOperation("根据ID查询讲师")
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable Long id) {

        // try {
        //     int i = 10 / 0 ;
        // } catch (Exception e) {
        //     // 手动抛出异常
        //     throw new GuliException(20002,"自定义异常处理~");
        // }
        EduTeacher teacher = eduTeacherService.getById(id);

        return teacher != null ? R.ok().data("teacher", teacher) : R.error().message("您查询的讲师不存在");
    }

    @ApiOperation("根据ID修改讲师")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {

        return eduTeacherService.updateById(eduTeacher) ? R.ok() : R.error();
    }
}
