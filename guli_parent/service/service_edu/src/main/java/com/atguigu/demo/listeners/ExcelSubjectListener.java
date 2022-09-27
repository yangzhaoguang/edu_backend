package com.atguigu.demo.listeners;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.demo.entity.EduSubject;
import com.atguigu.demo.entity.excel.ExcelSubjectData;
import com.atguigu.demo.service.EduSubjectService;
import com.atguigu.demo.service.impl.EduSubjectServiceImpl;
import com.atguigu.servicebase.handler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/14 21:58
 * Description:
 */
public class ExcelSubjectListener extends AnalysisEventListener<ExcelSubjectData> {

    private EduSubjectService eduSubjectService;

    // 因为该监听器无法交给 Spring 管理，因为在读取的时候需要 new 这个监听器。 因此不能注入其他对象
    // 可以通过 有参构造方法  将 EduSubjectService 传过来，操作数据库
    public ExcelSubjectListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    public ExcelSubjectListener() {

    }


    // EasyExcel 一行一行的读取数据
    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        // 读取不到数据
        if (excelSubjectData == null) {
            throw new GuliException(20001, "空文件");
        }

        //    判断一级分类是否重复
        EduSubject oneSubject = this.existOneSubject(excelSubjectData.getOneSubjectName(), eduSubjectService);
        if (oneSubject == null) {
            //    增加一级标分类
            // oneSubject 是null ，手动 new 出来一个，增加 一级分类 和 parent_id
            oneSubject = new EduSubject();
            oneSubject.setTitle(excelSubjectData.getOneSubjectName());
            oneSubject.setParentId("0");
            eduSubjectService.save(oneSubject);
        }

        //    判断二级分类是否重复
        // 二级分类的pid 是一级分类的 ID
        String pid = oneSubject.getId();
        EduSubject twoSubject = this.existTwoSubject(excelSubjectData.getTwoSubjectName(), eduSubjectService, pid);
        if (twoSubject == null) {
            //    增加二级标分类
            twoSubject = new EduSubject();
            twoSubject.setTitle(excelSubjectData.getTwoSubjectName());
            twoSubject.setParentId(pid);
            eduSubjectService.save(twoSubject);
        }
    }

    /**
     * 判断一级分类名称是否重复条件
     * 1. 名称一致
     * 2. 并且都是一级分类；title = 0
     *
     * @param name              一级分类名称
     * @param eduSubjectService 操作数据库使用
     */
    public EduSubject existOneSubject(String name, EduSubjectService eduSubjectService) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.like("title", name).eq("parent_id", 0);
        return eduSubjectService.getOne(wrapper);
    }

    /**
     * 判断二级分类名称是否重复
     * 1. 名称一致
     * 2. 父分类一样； parent_id 相等
     *
     * @param name              二级分类名称
     * @param eduSubjectService 操作数据库使用
     * @param pid               parent_id
     * @return
     */
    public EduSubject existTwoSubject(String name, EduSubjectService eduSubjectService, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.like("title", name).eq("parent_id", pid);
        return eduSubjectService.getOne(wrapper);
    }


    // 读取完执行的方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
