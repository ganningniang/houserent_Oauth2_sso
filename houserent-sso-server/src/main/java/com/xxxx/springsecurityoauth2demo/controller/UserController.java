package com.xxxx.springsecurityoauth2demo.controller;

import com.alibaba.fastjson.JSON;
import com.xxxx.springsecurityoauth2demo.pojo.Code;
import com.xxxx.springsecurityoauth2demo.pojo.Result;
import com.xxxx.springsecurityoauth2demo.pojo.dto.SysMenuDTO;
import com.xxxx.springsecurityoauth2demo.pojo.dto.SysUserDTO;
import com.xxxx.springsecurityoauth2demo.service.SysMenuService;
import com.xxxx.springsecurityoauth2demo.service.SysUserService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.xxxx.springsecurityoauth2demo.utils.Utils.toJsonString;

/**
 * @author qqq
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private SysMenuService sysMenuService;

	@Autowired
	private SysUserService sysUserService;

	/**
	 * 获取当前用户
	 * @param authentication
	 * @return
	 */
	@RequestMapping("/getCurrentUser")
	@PreAuthorize("hasAuthority('system:admin')")
	public Object getCurrentUser(Authentication authentication, HttpServletRequest request){
		String head = request.getHeader("Authorization");
		String token = head.substring(head.indexOf("bearer") + 7);
		return Jwts.parser()
				.setSigningKey("test_key".getBytes(StandardCharsets.UTF_8))
				.parseClaimsJws(token)
				.getBody();
	}


	@RequestMapping("/getMenu")
	@PreAuthorize("hasAuthority('system:user')")
	public Object getMenu(Authentication authentication, HttpServletRequest request){
		String userName = (String) authentication.getPrincipal();
		if (Objects.isNull(userName)){
			throw new RuntimeException("用户未登录");
		}

		List<SysMenuDTO> menusByUserName =
				sysMenuService.getMenusByUserName(userName);
		HashMap<String, List> stringListHashMap = new HashMap<>();
		stringListHashMap.put("routes",menusByUserName);
		System.out.println(userName + ":" + menusByUserName);
		return toJsonString(new Result(Code.SUCCESS,stringListHashMap));
	}

	@RequestMapping("/getUserInfo")
	@PreAuthorize("hasAuthority('system:user')")
	@ResponseBody
	public String login(Authentication authentication, HttpServletRequest request, HttpServletResponse response){
		// 1.从认证信息中取出用户名
		String userName = (String) authentication.getPrincipal();
		// 2.到数据库查询用户信息及权限信息
		List<SysUserDTO> userInfoByUserName =
				sysUserService.getUserInfoByUserName(userName);
		// 3.构造map并转为json格式后返回
		HashMap<String, List> stringListHashMap = new HashMap<>();
		stringListHashMap.put("userInfo",userInfoByUserName);
//		return toJsonString(new Result(Code.SUCCESS,userInfoByUserName));
		return toJsonString(new Result(Code.SUCCESS,stringListHashMap));
	}

	@RequestMapping("/login")
	@PreAuthorize("hasAuthority('system:user')")
	@ResponseBody
	public String login(){
//		JSONObject.toJSONString("Logging in, please wait...")
		return JSON.toJSONString("Logging in, please wait...");
	}


	@Autowired
	@Qualifier("jwtTokenStore")
	private TokenStore tokenStore;

	// token失效
	// 没有实现单点登出，只是客户端把自己的cookie删除掉
	@GetMapping("/revokeToken")
	public void revokeToken(HttpServletRequest request,HttpServletResponse response, Authentication authentication) {
//		new SecurityContextLogoutHandler().logout(request,response,authentication);
		// 将子系统的cookie删掉
//		HttpSession session = request.getSession();
//		System.out.println(session);
//
//		session.invalidate();
//		System.out.println("============request headers=============");
//		System.out.println(request.getRequestURI());
//		Enumeration<String> requestHeaderNames = request.getHeaderNames();
//
//		while (requestHeaderNames.hasMoreElements()){
//			String s = requestHeaderNames.nextElement();
//			System.out.println(s + ":" + request.getHeader(s));
//		}


//		String authHeader = request.getHeader("Authorization");
//		System.out.println(authHeader);
//		if (authHeader != null) {
//			String tokenValue = authHeader.replace("Bearer", "").trim();
//			OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
//			tokenStore.removeAccessToken(accessToken);
//		}
	}

}





