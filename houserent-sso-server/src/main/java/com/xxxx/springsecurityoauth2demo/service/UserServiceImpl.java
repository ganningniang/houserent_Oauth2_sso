
package com.xxxx.springsecurityoauth2demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.springsecurityoauth2demo.mapper.SysMenuMapper;
import com.xxxx.springsecurityoauth2demo.mapper.SysUserMapper;
import com.xxxx.springsecurityoauth2demo.pojo.SysUser;
import com.xxxx.springsecurityoauth2demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 自定义登录逻辑
 * @author qqq
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserDetailsService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//登录校验
		QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
		sysUserQueryWrapper.eq("user_name",username);
		SysUser loginUser = sysUserMapper.selectOne(sysUserQueryWrapper);
		if (Objects.isNull(loginUser)){
			throw new UsernameNotFoundException("the user is not found");
		}
		List<String> perms = sysMenuMapper.selectPermsByUserId(loginUser.getId());

		List<GrantedAuthority> authorities = perms.stream()
				.map(SimpleGrantedAuthority::new)
//                .map(s -> new SimpleGrantedAuthority(s))
				.collect(Collectors.toList());
		return new User(loginUser.getUserName(), loginUser.getPassword(), authorities);
	}
}