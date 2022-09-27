package com.atguigu.demo.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/14 21:55
 * Description:
 */
@Data
public class ExcelSubjectData {

    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
