package com.xxxx.springsecurityoauth2demo.config;

import com.xxxx.springsecurityoauth2demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 授权服务器配置
 *
 * @author qqq
 * @since 1.0.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	@Qualifier("jwtTokenStore")
	private TokenStore tokenStore;
	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	@Autowired
	private JwtTokenEnhancer jwtTokenEnhancer;
	@Autowired
	private DataSource dataSource;

	/**
	 * 使用密码模式所需配置
	 * @param endpoints
	 * @throws Exception
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		//配置JWT内容增强器
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		List<TokenEnhancer> delegates = new ArrayList<>();
		delegates.add(jwtTokenEnhancer);
		delegates.add(jwtAccessTokenConverter);
		enhancerChain.setTokenEnhancers(delegates);
		endpoints.authenticationManager(authenticationManager)
				.userDetailsService(userServiceImpl)
				//配置存储令牌策略
				.tokenStore(tokenStore)
				.accessTokenConverter(jwtAccessTokenConverter)
				.tokenEnhancer(enhancerChain);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
//		clients.inMemory()
//				//配置client-id
//				.withClient("admin")
//				//配置client-secret
//				.secret(passwordEncoder.encode("112233"))
//				//配置访问token的有效期
//				.accessTokenValiditySeconds(3600)
//				//配置刷新Token的有效期
//				.refreshTokenValiditySeconds(864000)
//				//配置redirect_uri,用于授权成功后跳转
//				.redirectUris("http://localhost:8088/login")
//				//自动授权配置
//				.autoApprove(true)
//				//配置申请的权限范围
//				.scopes("all")
//				//配置grant_type，表示授权类型
//				.authorizedGrantTypes("password","refresh_token","authorization_code");
	}


	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//获取秘钥需要身份认证，使用单点登录时必须配置
		security.tokenKeyAccess("isAuthenticated()");
	}
}