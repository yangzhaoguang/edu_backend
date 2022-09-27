package com.atguigu.demo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.demo.entity.EduSubject;
import com.atguigu.demo.entity.excel.ExcelSubjectData;
import com.atguigu.demo.entity.subject.OneSubject;
import com.atguigu.demo.entity.subject.TwoSubject;
import com.atguigu.demo.listeners.ExcelSubjectListener;
import com.atguigu.demo.service.EduSubjectService;
import com.atguigu.demo.mapper.EduSubjectMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* @author 杨照光
* @description 针对表【edu_subject(课程科目)】的数据库操作Service实现
* @createDate 2022-08-16 13:56:52
*/
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject>
implements EduSubjectService{

        @Override
        public void importSubjectInfo(MultipartFile file, EduSubjectService eduSubjectService) {
            try {
                // 读取内容
                // 文件流、实体类、监听器
                EasyExcel.read(file.getInputStream(), ExcelSubjectData.class,new ExcelSubjectListener(eduSubjectService)).sheet().doRead();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    /**
     *
     * @return 获取所有课程分类 —— 用树形结构显示
     */
    @Override
    public List<OneSubject> getAllSubject() {

        // 1.查询所有的一级分类 => parent_id = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id",0);
        List<EduSubject> oneSubjectsList = baseMapper.selectList(wrapperOne);

        // 2.查询所有的二级分类 => parent_id != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id",0);
        List<EduSubject> twoSubjectsList = baseMapper.selectList(wrapperTwo);

        // 3.封装一级分类
        // 保存树形结构
        ArrayList<OneSubject> finalList = new ArrayList<>();

        for (EduSubject eduSubject : oneSubjectsList) {
            // 创建树形结构中一级分类对象
            OneSubject oneSubject = new OneSubject();
            // 将 eduSubject 拷贝到 oneSubject , 自动拷贝、有相同属性的值。
            BeanUtils.copyProperties(eduSubject,oneSubject);


            // 4.封装二级分类到一级分类对象中的 children 中
            for (EduSubject subject : twoSubjectsList) {
                TwoSubject twoSubject = new TwoSubject();
                // 拷贝到二级分类对象中
                BeanUtils.copyProperties(subject,twoSubject);

                // 将二级分类对象保存到对应的一级分类的 children 集合中
                // 保存条件就是： 二级分类的 parent_id == 一级分类的 ID
                if (subject.getParentId().equals(oneSubject.getId())){
                    oneSubject.getChildren().add(twoSubject);
                }

            }
            // 最终将一级分类保存到 树形结构 集合中
            finalList.add(oneSubject);
        }
        return finalList;
    }
    }

