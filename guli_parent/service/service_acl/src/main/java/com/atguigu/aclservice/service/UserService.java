package com.atguigu.aclservice.service;

import com.atguigu.aclservice.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface UserService extends IService<User> {

    // 从数据库中取出用户信息
    User selectByUsername(String username);

    /**
     * @description 根据用户id删除用户、角色、菜单
     * @date 2023/4/14 9:30
     * @param userId
     * @return void
     */
    void removeRoleAndMenuWithUserId(String userId);
}
