package com.atguigu.demo.service;

import com.atguigu.demo.entity.EduSubject;
import com.atguigu.demo.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author 杨照光
* @description 针对表【edu_subject(课程科目)】的数据库操作Service
* @createDate 2022-08-16 13:56:52
*/
public interface EduSubjectService extends IService<EduSubject> {

    void importSubjectInfo(MultipartFile file, EduSubjectService eduSubjectService);

    List<OneSubject> getAllSubject();
}
