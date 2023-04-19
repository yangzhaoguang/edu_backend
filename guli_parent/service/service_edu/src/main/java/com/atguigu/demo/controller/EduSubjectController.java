package com.atguigu.demo.controller;

import com.atguigu.commonutils.R;
import com.atguigu.demo.entity.subject.OneSubject;
import com.atguigu.demo.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/14 21:41
 * Description:
 */
@Api("课程分类管理")
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService ;

    @ApiOperation("增加课程分类")
    @PostMapping("addSubject")
    private R addSubject(MultipartFile file){

        // 根据文件，导入课程分类信息
        eduSubjectService.importSubjectInfo(file,eduSubjectService);

        return R.ok();
    }

    @ApiOperation("显示课程分类列表")
    @GetMapping("getAllSubject")
    private R selectAllSubject(){

       //  获取树形结构，包含多个一级分类
       List<OneSubject> list =  eduSubjectService.getAllSubject();
       return R.ok().data("list",list);
    }
}
