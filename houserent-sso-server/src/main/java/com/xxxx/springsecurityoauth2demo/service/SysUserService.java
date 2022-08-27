package com.xxxx.springsecurityoauth2demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.springsecurityoauth2demo.pojo.SysUser;
import com.xxxx.springsecurityoauth2demo.pojo.dto.SysUserDTO;

import java.util.List;

/**
* @author qqq
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2022-07-08 11:18:20
*/
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户名获得用户信息
     *
     * @param userName
     * @return
     */
    List<SysUserDTO> getUserInfoByUserName(String userName);
}
