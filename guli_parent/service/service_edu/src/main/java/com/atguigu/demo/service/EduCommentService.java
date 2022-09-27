package com.atguigu.demo.service;

import com.atguigu.demo.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
* @author 杨照光
* @description 针对表【edu_comment(评论)】的数据库操作Service
* @createDate 2022-09-07 17:07:53
*/
public interface EduCommentService extends IService<EduComment> {

    Map<String,Object> pageCommentList(String courseId ,long page, long limit);

}
