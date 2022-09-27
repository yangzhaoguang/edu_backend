package com.atguigu.statistics.service;

import com.atguigu.statistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author 杨照光
* @description 针对表【statistics_daily(网站统计日数据)】的数据库操作Service
* @createDate 2022-09-13 21:35:43
*/
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    // 将统计的数据保存到数据库表中
    void registerCount(String date);

    // 获取统计的数据
    Map getShowData(String type, String begin, String end);
}
