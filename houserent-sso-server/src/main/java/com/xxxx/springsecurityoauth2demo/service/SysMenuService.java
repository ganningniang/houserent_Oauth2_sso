package com.xxxx.springsecurityoauth2demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.springsecurityoauth2demo.pojo.SysMenu;
import com.xxxx.springsecurityoauth2demo.pojo.dto.SysMenuDTO;

import java.util.List;

/**
* @author qqq
* @description 针对表【sys_menu(菜单表)】的数据库操作Service
* @createDate 2022-07-08 11:21:18
*/
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 根据用户名获得用户可以访问的菜单list
     * @param userName
     * @return
     */
    List<SysMenuDTO> getMenusByUserName(String userName);
}
