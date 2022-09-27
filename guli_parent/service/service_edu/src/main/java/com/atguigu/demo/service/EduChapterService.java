package com.atguigu.demo.service;

import com.atguigu.demo.entity.EduChapter;
import com.atguigu.demo.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 杨照光
 * @description 针对表【edu_chapter(课程)】的数据库操作Service
 * @createDate 2022-08-16 14:00:39
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * TODO 查询所有的章节
     * @date 2022/8/18 13:12
     * @param courseId 课程 Id
     * @return java.util.List<com.atguigu.demo.entity.chapter.ChapterVo>
     */
    List<ChapterVo> getAllChapterVideo(String courseId);

    /**
     * TODO 删除章节
     * @date 2022/8/20 17:43
     * @param id
     * @return boolean
     */
    boolean deleteChapter(String id);
}
