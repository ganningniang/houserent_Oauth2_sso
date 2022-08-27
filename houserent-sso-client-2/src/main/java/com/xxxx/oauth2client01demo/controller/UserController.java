package com.xxxx.oauth2client01demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author qqq
 * @since 1.0.0
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	OAuth2RestTemplate restTemplate;
	/**
	 * 获取当前用户信息
	 * @param authentication
	 * @return
	 *
	 */
	@RequestMapping("getCurrentUser")
	public Object getCurrentUser(Authentication authentication){
		return authentication;
	}


	/**
	 * 访问受保护资源-test
	 * http://localhost:8088/user/remoteCall
	 * @return
	 */
	@GetMapping("/remoteCall")
	public String remoteCall() {
		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity("http://localhost:8080/user/getCurrentUser", String.class);
		return responseEntity.getBody();
	}

	/**
	 * 获取菜单，受SpringSecurity保护
	 * http://localhost:8088/user/getMenu
	 * @return
	 */
	@GetMapping("/getMenu")
	public String getMenu(HttpServletRequest request){
		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity("http://localhost:8080/user/getMenu", String.class);
		return responseEntity.getBody();
	}

	/**
	 * 查询用户数据接口，受SpringSecurity保护
	 * http://localhost:8088/user/getUserInfo
	 * @return
	 */
	@GetMapping("/getUserInfo")
	@ResponseBody
	public String getUserInfo(HttpServletRequest request, HttpServletResponse response){
//		System.out.println("============request headers=============");
//		System.out.println(request.getRequestURI());
//		Enumeration<String> requestHeaderNames = request.getHeaderNames();
//
//		while (requestHeaderNames.hasMoreElements()){
//			String s = requestHeaderNames.nextElement();
//			System.out.println(s + ":" + request.getHeader(s));
//		}
//
//		System.out.println("============request cookie=============");
//		Cookie[] cookies = request.getCookies();
//		for (Cookie cookie :cookies) {
//			System.out.println(cookie +": "+ cookie.getValue());
//		}
//		System.out.println("============request seesion=============");
//		HttpSession session = request.getSession();
//		Enumeration<String> attributeNames = session.getAttributeNames();
//		while(attributeNames.hasMoreElements()){
//			System.out.println(attributeNames.nextElement() + ": " + session.getAttribute(attributeNames.nextElement()));
//		}
//
//		String origin = request.getHeader("origin");
//		System.out.println(request.getRemoteUser());
//		System.out.println("============response headers=============");
//		int status = response.getStatus();
//		System.out.println(status);
//		Collection<String> responseHeaderNames = response.getHeaderNames();
//		System.out.println(responseHeaderNames.size());
//		for (String s : responseHeaderNames) {
//			System.out.println(s);
//			System.out.println(response.getHeader(s));
//		}


//		response.setHeader("Access-Control-Allow-Origin", origin);
//		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//		response.setHeader("Access-Control-Max-Age", "3600");
//		response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,token, content-type"); //这里要加上content-type
//		response.setHeader("Access-Control-Allow-Credentials", "true");

		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity("http://localhost:8080/user/getUserInfo", String.class);
		return responseEntity.getBody();
	}

	/**
	 * 登录接口，受SpringSecurity保护
	 * http://localhost:8088/user/login
	 * @return "登录中，请稍后。。。"
	 */
	@GetMapping("/login")
	@ResponseBody
	public String login(){
		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity("http://localhost:8080/user/login", String.class);
		return responseEntity.getBody();
	}

	/**
	 * 登出接口，不是单点登出，只是把自己的cookie删除掉了
	 * http://localhost:8088/user/logout
	 * @return
	 */
	@GetMapping("/logout")
	@ResponseBody
	public String logout(HttpServletRequest request,HttpServletResponse response, Authentication authentication) throws IOException {
//		ResponseEntity<String> responseEntity = restTemplate
//				.getForEntity("http://localhost:8080/logout", String.class);
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
//				System.out.println(cookie);
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
		ResponseEntity<String> forEntity = restTemplate
				.getForEntity("http://localhost:8080/user/revokeToken", String.class);
//		return "注销成功";
		return forEntity.getBody();
	}
}