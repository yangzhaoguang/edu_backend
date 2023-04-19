package com.atguigu.aclservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.aclservice.entity.Permission;
import com.atguigu.aclservice.entity.User;
import com.atguigu.aclservice.entity.UserAccessRecords;
import com.atguigu.aclservice.service.IndexService;
import com.atguigu.aclservice.service.PermissionService;
import com.atguigu.aclservice.service.UserAccessRecordsService;
import com.atguigu.aclservice.service.UserService;
import com.atguigu.aclservice.utils.IPUtils;
import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/index")
// //@CrossOrigin
public class IndexController {

    @Autowired
    private IndexService indexService;
    @Autowired
    private UserAccessRecordsService userAccessRecordsService;
    @Autowired
    private UserService userService;

    /*
    * 获取登录访问日志
    * */
    @GetMapping("loginRecords")
    public R getLoginRecords(HttpServletRequest request){
        LambdaQueryWrapper<UserAccessRecords> wrapper = new LambdaQueryWrapper<UserAccessRecords>()
                .last("limit 3")
                .orderByDesc(UserAccessRecords::getLoginDate);
        List<UserAccessRecords> list = userAccessRecordsService.list(wrapper);
        return  R.ok().data("data",list);

    }

    /*
    * 保存用户登录信息，在SECURITY模块使用HTTP调用
    * */
    @GetMapping("saveUserLoginInfo")
    public R saveUserLoginInfo(@RequestParam String username,HttpServletRequest request){
        // 增加ip地址、访问时间等信息
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        UserAccessRecords userAccessRecords = new UserAccessRecords();
        userAccessRecords.setUserId(user.getId());
        userAccessRecords.setUsername(username);
        userAccessRecords.setLoginDate(new Date());
        userAccessRecords.setLoginIp(request.getRemoteAddr());

        userAccessRecordsService.save(userAccessRecords);
        return  R.ok();
    }

    /**
     * 根据token获取用户信息
     */
    @GetMapping("info")
    public R info(HttpServletRequest request){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return R.ok().data(userInfo);
    }

    /**
     * 获取菜单
     * @return
     */
    @GetMapping("menu")
    public R getMenu(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return R.ok().data("permissionList", permissionList);
    }

    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }

}
