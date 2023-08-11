package com.water.config;

import com.water.interceptor.TokenInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebLoginConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/register");
    }
}
