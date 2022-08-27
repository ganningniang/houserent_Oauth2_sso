package com.xxxx.springsecurityoauth2demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.springsecurityoauth2demo.pojo.SysMenu;
import com.xxxx.springsecurityoauth2demo.pojo.dto.SysMenuDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author qqq
* @description 针对表【sys_menu(菜单表)】的数据库操作Mapper
* @createDate 2022-07-08 11:21:18
* @Entity com.xxxx.springsecurityoauth2demo.pojo.SysMenu
*/
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 多表查询用户权限
     * @param userId 用户id
     * @return  权限列表
     */
    List<String> selectPermsByUserId(Long userId);

    /**
     * 多表查询用户可访问的菜单
     * @param userName
     * @return
     */
    List<SysMenuDTO>  selectMenusByUserName(String userName);
}




