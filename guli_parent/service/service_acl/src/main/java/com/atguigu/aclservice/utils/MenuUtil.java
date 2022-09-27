package com.atguigu.aclservice.utils;

import com.atguigu.aclservice.entity.Permission;
import com.atguigu.aclservice.service.PermissionService;
import com.atguigu.aclservice.service.impl.PermissionServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装菜单
 * Author: YZG
 * Date: 2022/9/15 23:04
 * Description: 
 */
// @Component
public class MenuUtil {


    private static PermissionServiceImpl permissionService;

    @Autowired
    public void setPermissionService(PermissionServiceImpl permissionService) {
        MenuUtil.permissionService = permissionService;
    }

    /**
     * @description 获取顶级菜单作为递归的入口
     * @date 2022/9/15 23:13
     * @param allPermissionList 所有菜单
     * @return java.util.List<com.atguigu.aclservice.entity.Permission>
     */
    public static List<Permission> build(List<Permission> allPermissionList) {
        // 用于最终的封装集合
        ArrayList<Permission> finalList = new ArrayList<>();
        // 获取顶级菜单作为一级菜单
        for (Permission permission : allPermissionList) {
            if ("0".equals(permission.getPid())) {
                permission.setLevel(1);

                // 查询一级菜单的所有子菜单
                finalList.add(findChildrenPermission(permission, allPermissionList));
            }
        }
        return finalList;
    }

    /**
     * @description
     * @date 2022/9/15 23:13
     * @param permission 父级菜单
     * @param allPermissionList 所有菜单
     * @return com.atguigu.aclservice.entity.Permission
     */
    private static Permission findChildrenPermission(Permission permission, List<Permission> allPermissionList) {
        // 初始化子级菜单： 由于在Permission 实体类中的 children属性并未初始化。 不初始化的话可能报空指针
        permission.setChildren(new ArrayList<Permission>());
        // 遍历所有的菜单
        for (Permission node : allPermissionList) {
            // 判断父级菜单id 和 子级菜单pid 是否相等，相等则是父子关系。
            if (permission.getId().equals(node.getPid())) {
                // 子级菜单的level = 父级菜单的level+1
                node.setLevel(permission.getLevel() + 1);
                //如果children为空，进行初始化操作
                if (permission.getChildren() == null) {
                    permission.setChildren(new ArrayList<Permission>());
                }
                // 将子级菜单保存到父级菜单的 children属性中. 并递归查找子级菜单的子级菜单
                permission.getChildren().add(findChildrenPermission(node, allPermissionList));
            }
        }
        return permission;
    }

    /**
     * @description 根据id查找所有子级菜单，将所有子级菜单的id封装到 idsList 中
     * @date 2022/9/16 14:11
     * @param id
     * @param idsList
     * @return void
     */
    public static void selectChildrenById(String id, ArrayList<String> idsList) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        // 1.首先找出该 id 对应的子级菜单
        queryWrapper.eq("pid",id);
        queryWrapper.select("id");
        List<Permission> list = permissionService.list(queryWrapper);

        // 2. 遍历所有的子级菜单，将子级菜单的 id 增加到集合中
        for (Permission permission : list) {
            idsList.add(permission.getId());
            // 3. 继续递归找下一个子级菜单
            selectChildrenById(permission.getId(),idsList);
        }
    }


}
