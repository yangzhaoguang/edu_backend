package com.atguigu.cms.controller;

import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.service.CrmBannerService;
import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * Author: YZG
 * Date: 2022/8/29 0:21
 * Description: 
 */
@RestController
@RequestMapping("cmsService/cmsAdmin")
//@CrossOrigin
public class CmsAdminController {
    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("pageQuery/{current}/{limit}")
    public R index(
            @PathVariable Long current,
            @PathVariable Long limit) {

        Page<CrmBanner> pageParam = new Page<>(current, limit);
        bannerService.page(pageParam, null);
        return R.ok().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    @ApiOperation(value = "根据 id 获取Banner")
    @GetMapping("getByID/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("saveBanner")
    public R save(@RequestBody CrmBanner banner) {
        bannerService.save(banner);
        return R.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("updateBanner")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("removeBanner/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "修改前台banner图")
    @PostMapping("editFrontBannerCount")
    public R editFrontBannerCount(@RequestBody List<String> bannerIds) {
        bannerService.editFrontBannerCount(bannerIds);
        return R.ok();
    }
}
