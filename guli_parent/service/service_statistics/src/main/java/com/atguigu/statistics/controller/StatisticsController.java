package com.atguigu.statistics.controller;

import com.atguigu.commonutils.R;
import com.atguigu.statistics.SearchVo;
import com.atguigu.statistics.service.StatisticsDailyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *
 * Author: YZG
 * Date: 2022/9/13 21:43
 * Description: 
 */
@RestController
@RequestMapping("staService/statistics")
//@CrossOrigin
public class StatisticsController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     * @description 将统计数据表村到 统计表中
     * @date 2022/9/13 22:34
     * @param
     * @return com.atguigu.commonutils.R
     */
    @PostMapping("registerCount/{date}")
    private R registerCount(@PathVariable String date) {
        // 保存统计数据
        statisticsDailyService.registerCount(date);
        return R.ok();
    }

    /**
     * @description 获取统计的数据
     * @date 2022/9/14 20:44
     * @return com.atguigu.commonutils.R
     */
    @PostMapping("showData/")
    @ApiOperation("获取展示的数据")
    private R getShowData(@RequestBody SearchVo searchVo) {

        Map map = statisticsDailyService.getShowData(searchVo);
        // statisticsDailyService
        return R.ok().data(map);
    }

}
