package com.atguigu.demo.entity.chapter;

import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: YZG
 * Date: 2022/8/18 13:05
 * Description: 
 */
@Data
public class ChapterVo {

    private String id ;
    private String title ;

    // 每一章里保存若干个小节
    List<VideoVo> children = new ArrayList<>();
}
