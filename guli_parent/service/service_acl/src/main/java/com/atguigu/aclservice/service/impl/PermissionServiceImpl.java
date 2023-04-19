package com.atguigu.aclservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.aclservice.entity.Permission;
import com.atguigu.aclservice.entity.RolePermission;
import com.atguigu.aclservice.entity.User;
import com.atguigu.aclservice.helper.MemuHelper;
import com.atguigu.aclservice.helper.PermissionHelper;
import com.atguigu.aclservice.mapper.PermissionMapper;
import com.atguigu.aclservice.service.PermissionService;
import com.atguigu.aclservice.service.RolePermissionService;
import com.atguigu.aclservice.service.UserService;
import com.atguigu.aclservice.utils.MenuUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;


    //根据角色获取菜单
    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id", roleId));
        //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RolePermission rolePermission = rolePermissionList.get(m);
                if (rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }


        List<Permission> permissionList = MenuUtil.build(allPermissionList);
        return permissionList;
    }

    //根据用户id获取用户菜单
    @Override
    public List<String> selectPermissionValueByUserId(String id) {

        List<String> selectPermissionValueList = null;
        if (this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if (this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }
        List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
        System.out.println("permissionList==>" + permissionList.size());
        List<JSONObject> result = MemuHelper.bulid(permissionList); 
        return result;
    }

    /**
     * 判断用户是否系统管理员
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if (null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }


    //======================== 递归查询所有菜单 ================================================

    /**
     * @description 获取所有的菜单，并封装
     * @date 2022/9/15 22:33
     * @param
     * @return java.util.List<com.atguigu.aclservice.entity.Permission>
     */
    @Override
    public List<Permission> queryAllMenuGuli() {
        //1 查询菜单表所有数据
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(wrapper);
        //2 把查询所有菜单list集合按照要求进行封装
        return MenuUtil.build(permissionList);
    }


    //======================== 递归删除菜单 ================================================

    /**
     * @description 根据菜单id 删除该菜单下的所有子级菜单
     * @date 2022/9/16 14:19
     * @param id
     * @return void
     */
    @Override
    public void removeChildByIdGuli(String id) {
        List<Permission> list = permissionService.list(new QueryWrapper<Permission>().eq("pid", id));
        ArrayList<String> idsList = new ArrayList<>();
        // 判断删除的菜单下是否有子菜单
        if (list.size() > 0) {
            // 保存删除菜单的 id 集合
            // 根据子级菜单递归查找下一个子级菜单,封装到 idsList中
            MenuUtil.selectChildrenById(id, idsList);
        }
        idsList.add(id);
        // 删除 所有菜单
        this.removeBatchByIds(idsList);
    }

    //======================== 给角色分配菜单 ================================================
    @Override
    public void saveRolePermissionRealtionShipGuli(String roleId, String[] permissionIds) {

        boolean flag = false;
        // 创建集合保存 角色和菜单 的对应关系
        ArrayList<RolePermission> rolePermissionList = new ArrayList<>();
        for (String permissionId : permissionIds) {
            // 说明有了全部数据的权限
            if ("1".equals(permissionId)) {
                flag = true;
            }
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            // 保存到集合中
            rolePermissionList.add(rolePermission);
        }
        if (!flag){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId("1");
            // 保存到集合中
            rolePermissionList.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissionList);
    }
}
