package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.kms.model.v20160120.DeleteAliasRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.handler.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantUtil;
import com.atguigu.vod.utils.InitVodClient;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * Author: YZG
 * Date: 2022/8/26 17:56
 * Description: 
 */
@RestController
//@CrossOrigin
@RequestMapping("/vodservice/vod")
public class VodController {

    @Autowired
    VodService vodService;

    @ApiOperation("视频上传")
    @PostMapping("uploadVideo")
    private R uploadVideo(MultipartFile file) {
        // 返回上传视频的 Id
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId", videoId);
    }

    /**
     * @description 删除一个上传视频.
     * @date 2022/8/27 22:37
     * @param id
     * @return com.atguigu.commonutils.R
     */
    @ApiOperation("删除上传视频")
    @DeleteMapping("deleteALiYunVideo/{id}")
    private R deleteVideo(@PathVariable String id) {
        //  //  演示服务降级
        // try {
        //     TimeUnit.SECONDS.sleep(10);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        try {
            //    1.初始化 client对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantUtil.ACCESS_KEY_ID, ConstantUtil.ACCESS_KEY_SECRET);
            //    2.c创建 request 对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            return R.error();
        }

    }

    @ApiOperation("删除多个视频")
    @DeleteMapping("deleteBatch")
    private R deleteBatch(@RequestParam("videoIds") List<String> videoIds) {
        try {
            //    1.初始化 client对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantUtil.ACCESS_KEY_ID, ConstantUtil.ACCESS_KEY_SECRET);
            //    2.c创建 request 对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            // StringUtils 工具包是org.apache.commons.lang3包下的
            // join：将数组中的元素用指定的分隔符分开，返回一个字符串
            String ids = StringUtils.join(videoIds.toArray(), ",");
            // 可以删除多个视频，视频 Id 用 ，号隔开
            request.setVideoIds(ids);
            client.getAcsResponse(request);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * @description 根据视频id获取视频播放凭证
     * @date 2022/9/7 11:03
     * @param id
     * @return com.atguigu.commonutils.R
     */
    @ApiOperation("获取视频播放凭证")
    @GetMapping("getPlayAuth/{id}")
    private R getPlayAuth(@PathVariable String id) {
        System.out.println("获取播放凭证: ");
        try {
            // 初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantUtil.ACCESS_KEY_ID,ConstantUtil.ACCESS_KEY_SECRET);

            // 获取 request 对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            // 设置 id
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            // 获取视频播放凭证
            String playAuth  = response.getPlayAuth();

            return R.ok().data("playAuth" ,playAuth);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"获取视频播放凭证失败");
        }
    }
}
