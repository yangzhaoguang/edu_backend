package com.atguigu.statistics.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.statistics.service.UcenterFeignService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.statistics.entity.StatisticsDaily;
import com.atguigu.statistics.service.StatisticsDailyService;
import com.atguigu.statistics.mapper.StatisticsDailyMapper;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 杨照光
 * @description 针对表【statistics_daily(网站统计日数据)】的数据库操作Service实现
 * @createDate 2022-09-13 21:35:43
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily>
        implements StatisticsDailyService {

    @Autowired
    private UcenterFeignService ucenterFeignService;

    @Override
    public void registerCount(String date) {
        // 保存数据库之前，删除相同日期的统计数据
        QueryWrapper<StatisticsDaily> statisticsDailyQueryWrapper = new QueryWrapper<>();
        statisticsDailyQueryWrapper.eq("date_calculated", date);
        this.remove(statisticsDailyQueryWrapper);

        R r = ucenterFeignService.countRegister(date);
        // 获取到注册的人数
        Integer count = (Integer) r.getData().get("count");

        StatisticsDaily statisticsDaily = new StatisticsDaily();

        statisticsDaily.setDateCalculated(date);
        statisticsDaily.setRegisterNum(count);
        // 以下数据 随机生成，只演示一个 注册人数
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100, 200));
        // 保存数据库
        this.save(statisticsDaily);
    }


    /**
     * @description  根据条件统计数据
     * @date 2022/9/14 20:56
     * @param type
     * @param begin
     * @param end
     * @return java.util.Map
     */
    @Override
    public Map getShowData(String type, String begin, String end) {
        // 查询出统计的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between(!StringUtils.isAllEmpty(begin, end), "date_calculated", begin, end);
        // 指定查询的字段
        wrapper.select(type,"date_calculated");
        List<StatisticsDaily> statisticsDailies = this.list(wrapper);

        // 保存 日期 的集合
        List<Object> dateList = new ArrayList<>();
        // 保存 统计数据 的集合
        List<Object> countList = new ArrayList<>();


        for (StatisticsDaily daily : statisticsDailies) {
            dateList.add(daily.getDateCalculated());
            // 统计哪个字段，就保存哪个字段的值
            switch (type) {
                case "register_num":
                    countList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    countList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    countList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    countList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        // 将 list 集合保存到 map 集合中
        HashMap<String, Object> map = new HashMap<>();
        map.put("dateList", dateList);
        map.put("countList", countList);


        return map;
    }
}




