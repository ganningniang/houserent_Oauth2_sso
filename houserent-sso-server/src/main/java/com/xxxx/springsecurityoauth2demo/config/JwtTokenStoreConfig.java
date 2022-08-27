package com.xxxx.springsecurityoauth2demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * JwtToken配置类
 *
 * @author qqq
 * @since 1.0.0
 */
@Configuration
public class JwtTokenStoreConfig {

	@Bean
	public TokenStore jwtTokenStore(){
		return new JwtTokenStore(jwtAccessTokenConverter());
	}


	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter(){
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		//配置JWT使用的秘钥
		accessTokenConverter.setSigningKey("test_key");
		return accessTokenConverter;
	}

	@Bean
	public JwtTokenEnhancer jwtTokenEnhancer(){
		return new JwtTokenEnhancer();
	}
}