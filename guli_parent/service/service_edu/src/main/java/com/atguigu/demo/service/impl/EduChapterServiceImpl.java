package com.atguigu.demo.service.impl;

import com.atguigu.demo.entity.EduVideo;
import com.atguigu.demo.entity.chapter.ChapterVo;
import com.atguigu.demo.entity.chapter.VideoVo;
import com.atguigu.demo.mapper.EduVideoMapper;
import com.atguigu.demo.service.EduVideoService;
import com.atguigu.servicebase.handler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.demo.entity.EduChapter;
import com.atguigu.demo.service.EduChapterService;
import com.atguigu.demo.mapper.EduChapterMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 杨照光
 * @description 针对表【edu_chapter(课程)】的数据库操作Service实现
 * @createDate 2022-08-16 14:00:39
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter>
        implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    /**
     * TODO 查询某个课程的所有章节 和小节
     * @date 2022/8/18 13:14
     * @param courseId
     * @return java.util.List<com.atguigu.demo.entity.chapter.ChapterVo>
     */
    @Override
    public List<ChapterVo> getAllChapterVideo(String courseId) {
        // 1. 根据课程ID查询所有的章节
        QueryWrapper<EduChapter> chapterVoQueryWrapper = new QueryWrapper<>();
        chapterVoQueryWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(chapterVoQueryWrapper);

        // 2. 根据课程 Id 查询所有的小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideos = eduVideoService.list(videoQueryWrapper);

        // 保存树形结构的集合
        List<ChapterVo> finalList = new ArrayList<>();
        // 3. 封装章节
        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            // 遍历章节集合，把每yi章节都保存在 chapterVo 对象里
            BeanUtils.copyProperties(eduChapter, chapterVo);

            // 4。 封装小节
            for (EduVideo eduVideo : eduVideos) {
                // 判断该小节是否属于同一个章节
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    // 将小节复制到  树形结构的实体类对象中
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    // Chapter 中的 Children 集合是用来保存 小节信息的
                    chapterVo.getChildren().add(videoVo);
                }
            }
            // 增加每一章节到树形结构集合中
            finalList.add(chapterVo);
        }
        return finalList;
    }

    /**
     * TODO 删除章节
     * @date 2022/8/20 17:43
     * @param id
     * @return boolean
     */
    @Override
    public boolean deleteChapter(String id) {
        // 1.根据id查询章节所对应的小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id",id);
        // 2. 如果有小节无法删除章节
        if (eduVideoService.count(videoQueryWrapper) > 0){
            throw new GuliException(20001,"请确保该章节下没有小节才能删除");
        }
        return this.removeById(id) ;
    }
}




