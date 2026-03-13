package com.bb.eodi.config;

import com.bb.eodi.common.presentation.interceptor.AdminApiAuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CustomSecurityProperties.class)
public class WebConfig implements WebMvcConfigurer {

    private final CorsProperties corsProperties;
    private final AdminApiAuthInterceptor adminApiAuthInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(corsProperties.getAllowedOrigins().toArray(String[]::new))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminApiAuthInterceptor)
                .addPathPatterns("/admin/**");
    }
}