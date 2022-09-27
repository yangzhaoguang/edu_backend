package com.atguigu.demo.controller;

import com.atguigu.commonutils.R;
import com.atguigu.demo.entity.EduVideo;
import com.atguigu.demo.service.EduVideoService;
import com.atguigu.demo.service.VodFeignService;
import com.atguigu.servicebase.handler.GuliException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/16 14:09
 * Description:
 */
@Api("小节管理")
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodFeignService vodFeignService;

    @ApiOperation("增加小节")
    @PostMapping("addVideo")
    private R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    // 删除小节，同时删除 阿里云视频
    @ApiOperation("删除小节")
    @DeleteMapping("deleteVideo/{id}")
    private R deleteVideo(@PathVariable String id) {
        // 通过小节 Id ，获取视频 ID
        EduVideo video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)){
            // 删除视频
            R r = vodFeignService.deleteVideo(videoSourceId);
            // 演示服务降级
            // if (r.getCode() == 20001) {
            //     throw new GuliException(20001,"错误");
            // }
        }
        // 删除小节
        videoService.removeById(id);
        return R.ok();
    }


    @ApiOperation("根据 id 查询小节")
    @GetMapping("getVideoById/{id}")
    private R getVideoById(@PathVariable String id) {
        EduVideo eduVideo = videoService.getById(id);
        return R.ok().data("video",eduVideo);
    }

    @ApiOperation("修改小节")
    @PostMapping("updateVideo")
    private R getVideoById(@RequestBody EduVideo eduVideo) {
        boolean update = videoService.updateById(eduVideo);
        return  update ? R.ok() : R.error().message("修改失败");
    }
}
