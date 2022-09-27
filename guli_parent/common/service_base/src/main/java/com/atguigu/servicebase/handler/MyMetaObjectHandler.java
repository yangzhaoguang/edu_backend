package com.atguigu.servicebase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Handsome Man.
 * <p>
 * Author: YZG
 * Date: 2022/8/6 14:00
 * Description:
 */
@Api("自动填充配置类")
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // fieldName : 是实体类的属性名，不是字段名
        this.strictInsertFill(metaObject,"gmtCreate", Date.class,new Date());
        this.strictInsertFill(metaObject,"gmtModified", Date.class,new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject,"gmtModified", Date.class,new Date());
    }
}
