package com.atguigu.demo.controller;

import com.atguigu.commonutils.R;
import com.atguigu.demo.entity.EduChapter;
import com.atguigu.demo.entity.chapter.ChapterVo;
import com.atguigu.demo.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/16 14:08
 * Description:
 */
@Api("课程章节")
@RestController
@RequestMapping("/eduservice/chapter")
//@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    @ApiOperation("查询所有章节")
    @GetMapping("/getAllChapter/{courseId}")
    private R getAllChapterVideo(@PathVariable String courseId) {

        // 章节适合课程相关联的，因此根据 课程 Id 查询所有的章节信息
        List<ChapterVo> list = chapterService.getAllChapterVideo(courseId);
        return R.ok().data("ChaptersAndVideos", list);
    }


    /**
     * TODO 增加章节
     * @date 2022/8/20 17:35
     * @param chapter
     * @return com.atguigu.commonutils.R
     */
    @ApiOperation("增加章节")
    @PostMapping("addChapter")
    private R addChapter(@RequestBody EduChapter chapter) {
        boolean save = chapterService.save(chapter);
        return save ? R.ok() : R.error();
    }

    /**
     * TODO 根据 ID 查询章节
     * @date 2022/8/20 17:39
     * @param id
     * @return com.atguigu.commonutils.R
     */
    @ApiOperation("根据Id查询章节")
    @GetMapping("/getChapterById/{id}")
    private R getChapterById(@PathVariable String id) {
        EduChapter chapter = chapterService.getById(id);
        return R.ok().data("chapter", chapter);
    }

    /**
     * TODO 修改章节
     * @date 2022/8/20 17:42
     * @param chapter
     * @return com.atguigu.commonutils.R
     */
    @ApiOperation("修改章节")
    @PostMapping("updateChapter")
    private R updateChapter(@RequestBody EduChapter chapter) {
        boolean b = chapterService.updateById(chapter);
        return b ? R.ok() : R.error();
    }

    /**
     * TODO 删除章节，若章节下有小节不允许删除
     * @date 2022/8/20 17:53
     * @param id
     * @return com.atguigu.commonutils.R
     */
    @ApiOperation("删除章节")
    @DeleteMapping("deleteChapter/{id}")
    private R deleteChapter(@PathVariable String id) {
        boolean result = chapterService.deleteChapter(id);
        return result ? R.ok() : R.error().message("删除失败");
    }
}
