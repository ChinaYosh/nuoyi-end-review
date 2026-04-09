package com.ruoyi.framework.config;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * MyBatis-Plus自动填充处理器
 * 自动填充创建时间、更新时间、创建者、更新者
 *
 * @author ruoyi
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler
{
    @Override
    public void insertFill(MetaObject metaObject)
    {
        // 自动填充创建时间
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        // 自动填充更新时间
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
        try
        {
            // 自动填充创建者
            this.strictInsertFill(metaObject, "createBy", String.class, SecurityUtils.getUsername());
            // 自动填充更新者
            this.strictInsertFill(metaObject, "updateBy", String.class, SecurityUtils.getUsername());
        }
        catch (Exception ignored)
        {
            // 忽略未登录场景
        }
    }

    @Override
    public void updateFill(MetaObject metaObject)
    {
        // 自动填充更新时间
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
        try
        {
            // 自动填充更新者
            this.strictUpdateFill(metaObject, "updateBy", String.class, SecurityUtils.getUsername());
        }
        catch (Exception ignored)
        {
            // 忽略未登录场景
        }
    }
}
