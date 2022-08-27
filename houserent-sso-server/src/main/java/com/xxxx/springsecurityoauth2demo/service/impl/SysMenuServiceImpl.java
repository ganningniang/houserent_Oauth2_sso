package com.xxxx.springsecurityoauth2demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.springsecurityoauth2demo.mapper.SysMenuMapper;
import com.xxxx.springsecurityoauth2demo.pojo.SysMenu;
import com.xxxx.springsecurityoauth2demo.pojo.dto.SysMenuDTO;
import com.xxxx.springsecurityoauth2demo.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author qqq
* @description 针对表【sys_menu(菜单表)】的数据库操作Service实现
* @createDate 2022-07-08 11:21:18
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenuDTO> getMenusByUserName(String userName) {
        return sysMenuMapper.selectMenusByUserName(userName);
    }
}




