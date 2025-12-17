package com.agri.platform.config.users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 配置访问权限
            .authorizeHttpRequests(auth -> auth
                // 允许所有静态资源访问
                .requestMatchers("/", "/*.html").permitAll()
                // 允许所有API接口访问
                .requestMatchers("/api/**").permitAll()
                // 所有请求都允许访问（开发环境可以这样设置）
                .anyRequest().permitAll()
            )
            // 禁用默认表单登录
            .formLogin(form -> form.disable())
            // 禁用HTTP基本认证
            .httpBasic(basic -> basic.disable())
            // 禁用CSRF保护（开发环境可以禁用）
            .csrf(csrf -> csrf.disable());
            
        return http.build();
    }
}