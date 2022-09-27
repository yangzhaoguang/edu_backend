package com.atguigu.statistics.schedule;

import com.atguigu.commonutils.DateUtil;
import com.atguigu.statistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *
 * Author: YZG
 * Date: 2022/9/14 17:20
 * Description: 
 */
@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;
    /**
     * @description 定时任务, 每日的凌晨一点执行，统计前一天的数据
     * @date 2022/9/14 17:21
     * @param
     * @return void
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task01() {
        // 使用 DateUtil 获取前一天的日期
        statisticsDailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
