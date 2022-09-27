package com.atguigu.demo.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.demo.service.VodFeignService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * Author: YZG
 * Date: 2022/8/28 12:36
 * Description: 
 */
@Component
public class VodFeignServiceImpl implements VodFeignService {
    @Override
    public R deleteVideo(String id) {
        return R.error().message("删除单个视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoIds) {
        return R.error().message("删除多个视频出错");
    }
}
