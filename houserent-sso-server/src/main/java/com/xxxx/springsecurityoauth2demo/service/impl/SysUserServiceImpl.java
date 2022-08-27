package com.xxxx.springsecurityoauth2demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.springsecurityoauth2demo.mapper.SysUserMapper;
import com.xxxx.springsecurityoauth2demo.pojo.SysUser;
import com.xxxx.springsecurityoauth2demo.pojo.dto.SysUserDTO;
import com.xxxx.springsecurityoauth2demo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author qqq
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2022-07-08 11:18:20
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService {


    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 根据用户名获得用户信息
     *
     * @param userName
     * @return
     */
    @Override
    public List<SysUserDTO> getUserInfoByUserName(String userName) {
        return sysUserMapper.selectUserInfoByUserName(userName);
    }


}




