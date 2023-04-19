package com.atguigu.aclservice.service.impl;

import com.atguigu.aclservice.entity.User;
import com.atguigu.aclservice.entity.UserRole;
import com.atguigu.aclservice.mapper.UserMapper;
import com.atguigu.aclservice.service.RolePermissionService;
import com.atguigu.aclservice.service.RoleService;
import com.atguigu.aclservice.service.UserRoleService;
import com.atguigu.aclservice.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    @Override
    public User selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }


    @Override
    public void removeRoleAndMenuWithUserId(String userId) {
        // 一个用户可能有多个角色
        List<UserRole> roleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", userId));
        if (roleList.size() != 0) {

            for (UserRole userRole : roleList) {
                // 查询出每个角色对应的菜单,如果有直接删除菜单、角色
                roleService.removeMenuWithRoleId(userRole.getRoleId());
                // 删除用户-角色关联表的数据
                roleService.removeById(userRole.getId());
            }
        }
        // 删除用户
        this.removeById(userId);

    }
}
