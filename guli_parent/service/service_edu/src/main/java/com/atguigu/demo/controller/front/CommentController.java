package com.atguigu.demo.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ResultCode;
import com.atguigu.demo.entity.EduComment;
import com.atguigu.demo.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * Author: YZG
 * Date: 2022/9/7 17:08
 * Description: 
 */
@RestController
@RequestMapping("/eduservice/comment")
//@CrossOrigin
public class CommentController {


    @Autowired
    private EduCommentService commentService;

    /**
     * @description 根据课程 id 查询对应的评论
     * @date 2022/9/8 11:29
     * @param courseId
     * @param page
     * @param limit
     * @return com.atguigu.commonutils.R
     */
    @ApiOperation("分页显示评论列表")
    @GetMapping("pageCommentList/{courseId}/{page}/{limit}")
    private R pageCommentList(@PathVariable String courseId,@PathVariable long page, @PathVariable long limit) {
        // 分页查询 评论列表，封装成一个 map
        Map<String,Object> commentMap = commentService.pageCommentList(courseId,page, limit);

        return R.ok().data(commentMap);
    }

    // 根据课程id查询所有评论
    @GetMapping("/selectComment/{courseId}")
    private R selectComment(@PathVariable String courseId) {
        List<EduComment> list = commentService.list(new LambdaQueryWrapper<EduComment>().eq(EduComment::getCourseId, courseId));
        if (list.size() == 0) {
            return  R.error().code(ResultCode.ERROR).message("该课程暂无评论");
        }
        return R.ok().data("commentList",list);
    }

    /**
     * @description
     * @date 2022/9/7 17:31
     * @param eduComment 评论对象，该对象里面没有包含用户信息
     * @return com.atguigu.commonutils.R
     */
    @ApiOperation("增加评论")
    @PostMapping("saveComment")
    private R saveComment(@RequestBody EduComment eduComment) {
        boolean result = commentService.save(eduComment);
        return  result ? R.ok().message("发布评论成功") : R.ok().message("发布评论失败");
    }

    @ApiOperation("删除评论")
    @DeleteMapping("removeComment/{commentId}")
    private R removeComment(@PathVariable String commentId ) {
        commentService.removeById(commentId);
        return  R.ok();
    }

}
