package com.xxxx.springsecurityoauth2demo.config;

import com.xxxx.springsecurityoauth2demo.handler.CustomLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * Security配置类
 * @author qqq
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	private CustomLoginSuccessHandler customLoginSuccessHandler;

	@Autowired
	private CorsFilter corsFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/oauth/**","/login/**","logout/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.successHandler(customLoginSuccessHandler)
				.permitAll()
				.and()
				// 添加处理跨域过滤器
				.addFilterBefore(corsFilter, WebAsyncManagerIntegrationFilter.class)
				// 允许iframe嵌入
				.headers()
				.frameOptions()
				.disable()
				.and()
				// 配置logout
				.logout();
	}
}