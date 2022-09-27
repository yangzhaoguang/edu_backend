package com.atguigu.cms.service;

import com.atguigu.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 杨照光
* @description 针对表【crm_banner(首页banner表)】的数据库操作Service
* @createDate 2022-08-29 00:17:03
*/
public interface CrmBannerService extends IService<CrmBanner> {

/**
 * @description 获取所有的 banner
 * @date 2022/8/29 13:31
 * @param
 * @return java.util.List<com.atguigu.cms.entity.CrmBanner>
 */
    // 前台显示 banner 图
    List<CrmBanner> selectIndexList();

    // 修改前台banner图的数量
    List<CrmBanner> editFrontBannerCount(List<String> bannerIds);
}
