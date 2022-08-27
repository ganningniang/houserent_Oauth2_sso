package com.xxxx.springsecurityoauth2demo.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单表
 * @author qqq
 * @TableName sys_menu
 */
@TableName(value ="sys_menu")
@Data
public class SysMenuDTO implements Serializable {

    /**
     * 菜单名
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 菜单图标
     */
    private String icon;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}