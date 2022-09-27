package com.atguigu.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.service.CrmBannerService;
import com.atguigu.cms.mapper.CrmBannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 杨照光
 * @description 针对表【crm_banner(首页banner表)】的数据库操作Service实现
 * @createDate 2022-08-29 00:17:03
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner>
        implements CrmBannerService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @description 前台banner图
     * @date 2022/8/31 15:08
     * @param
     * @return java.util.List<com.atguigu.cms.entity.CrmBanner>
     */
    @Override
    public List<CrmBanner> selectIndexList() {
        List<CrmBanner> crmBanners = new ArrayList<>();
        // 从 redis 取出
        crmBanners = (List<CrmBanner>) redisTemplate.opsForValue().get("editBannerList");
        if (null == crmBanners) {
            // 说明没有设置banner图
            crmBanners = baseMapper.selectList(null);
        }

        return crmBanners;
    }

    /**
     * @description 修改前台 banner 图的数量
     * @date 2022/8/31 14:35
     * @param bannerIds
     * @return java.util.List<com.atguigu.cms.entity.CrmBanner>
     */
    @Override
    public List<CrmBanner> editFrontBannerCount(List<String> bannerIds) {
        // 创建 list 集合 保存banner 图
        List<CrmBanner> list = new ArrayList<>();
        for (String bannerId : bannerIds) {
            CrmBanner crmBanner = baseMapper.selectById(bannerId);
            if (crmBanner != null) {
                list.add(crmBanner);
            }
        }
        // 存入 redis,设置永不过期
        redisTemplate.opsForValue().set("editBannerList",list);
        return list;
    }
}




