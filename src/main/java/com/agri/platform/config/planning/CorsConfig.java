package com.agri.platform.config.planning;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration  // 标记为配置类
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:8080");  // 允许前端地址（本地开发）
        config.addAllowedMethod("*");  // 允许所有HTTP方法（GET/POST/PUT/DELETE）
        config.addAllowedHeader("*");  // 允许所有请求头
        config.setAllowCredentials(true);  // 允许携带Cookie

        // 对所有接口生效
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
