//package com.xxxx.springsecurityoauth2demo.backup;
//
//import com.alibaba.fastjson.JSON;
//import com.xxxx.springsecurityoauth2demo.pojo.Code;
//import com.xxxx.springsecurityoauth2demo.pojo.Result;
//import com.xxxx.springsecurityoauth2demo.pojo.dto.SysMenuDTO;
//import com.xxxx.springsecurityoauth2demo.pojo.dto.SysUserDTO;
//import com.xxxx.springsecurityoauth2demo.service.SysMenuService;
//import com.xxxx.springsecurityoauth2demo.service.SysUserService;
//import io.jsonwebtoken.Jwts;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.nio.charset.StandardCharsets;
//import java.util.*;
//
//import static com.xxxx.springsecurityoauth2demo.utils.Utils.toJsonString;
//
///**
// * @author zhoubin
// * @since 1.0.0
// */
//@RestController
//@RequestMapping("/user")
//@CrossOrigin
//public class UserControllerBackup {
//
//	@Autowired
//	private SysMenuService sysMenuService;
//
//	@Autowired
//	private SysUserService sysUserService;
//
//	/**
//	 * 获取当前用户
//	 * @param authentication
//	 * @return
//	 */
//	@RequestMapping("/getCurrentUser")
//	@PreAuthorize("hasAuthority('system:admin')")
//	public Object getCurrentUser(Authentication authentication, HttpServletRequest request){
//		String head = request.getHeader("Authorization");
//		String token = head.substring(head.indexOf("bearer") + 7);
//		return Jwts.parser()
//				.setSigningKey("test_key".getBytes(StandardCharsets.UTF_8))
//				.parseClaimsJws(token)
//				.getBody();
//	}
//
//
//	@RequestMapping("/getMenu")
//	@PreAuthorize("hasAuthority('system:user')")
//	public Object getMenu(Authentication authentication, HttpServletRequest request){
//		String userName = (String) authentication.getPrincipal();
//		if (Objects.isNull(userName)){
//			return Collections.singletonList("房屋查询");
//		}
//
//		List<SysMenuDTO> menusByUserName =
//				sysMenuService.getMenusByUserName(userName);
//		return sysMenuService.getMenusByUserName(userName);
//	}
//
//	@RequestMapping("/getUserInfo")
//	@PreAuthorize("hasAuthority('system:user')")
//	@ResponseBody
//	public String login(Authentication authentication, HttpServletRequest request, HttpServletResponse response){
//		// 1.从认证信息中取出用户名
//		String userName = (String) authentication.getPrincipal();
//		// 2.到数据库查询用户信息及权限信息
//		List<SysUserDTO> userInfoByUserName =
//				sysUserService.getUserInfoByUserName(userName);
//		List<SysMenuDTO> menusByUserName =
//				sysMenuService.getMenusByUserName(userName);
////		if (Objects.isNull(userName)){
////			return Collections.singletonList("房屋查询");
////		}
////		System.out.println(menusByUserName.get(1));
//		// 3.构造map并转为json格式后返回
//		HashMap<String, List> stringListHashMap = new HashMap<>();
//		stringListHashMap.put("routes",menusByUserName);
//		stringListHashMap.put("userInfo",userInfoByUserName);
//		System.out.println(menusByUserName);
//
//		System.out.println("============request cookie=============");
//		Cookie[] cookies = request.getCookies();
//		if (cookies!=null){
//			for (Cookie cookie :cookies) {
//				if (cookie!=null){
//					System.out.println(cookie +": "+ cookie.getValue());
//				}
//
//			}
//		}
//
//		System.out.println("============request seesion=============");
//		HttpSession session = request.getSession();
//		Enumeration<String> attributeNames = session.getAttributeNames();
//		while(attributeNames.hasMoreElements()){
//			System.out.println(attributeNames.nextElement() + ": " + session.getAttribute(attributeNames.nextElement()));
//		}
//
//		System.out.println("============response headers=============");
//		int status = response.getStatus();
//		System.out.println(status);
//		Collection<String> responseHeaderNames = response.getHeaderNames();
//		System.out.println(responseHeaderNames.size());
//		for (String s : responseHeaderNames) {
//			System.out.println(s);
//			System.out.println(response.getHeader(s));
//		}
//
//		return toJsonString(new Result(Code.SUCCESS,stringListHashMap));
//	}
//
//	@RequestMapping("/login")
//	@PreAuthorize("hasAuthority('system:user')")
//	@ResponseBody
//	public String login(){
////		JSONObject.toJSONString("Logging in, please wait...")
//		return JSON.toJSONString("Logging in, please wait...");
//	}
//
//}
//
//
//
//
//
