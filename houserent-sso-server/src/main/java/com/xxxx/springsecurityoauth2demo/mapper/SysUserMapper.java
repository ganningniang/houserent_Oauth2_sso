package com.xxxx.springsecurityoauth2demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.springsecurityoauth2demo.pojo.SysUser;
import com.xxxx.springsecurityoauth2demo.pojo.dto.SysUserDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author qqq
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2022-07-08 11:18:20
* @Entity com.xxxx.springsecurityoauth2demo.pojo.SysUser
*/
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 查询用户信息
     * @param userName
     * @return
     */
    List<SysUserDTO> selectUserInfoByUserName(String userName);
}




