package com.app.trycatch.config;


import com.app.trycatch.interceptor.IndividualAlramInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IndividualAlramInterceptor())
                .addPathPatterns("/qna/**")
                .addPathPatterns("/mypage/**");
//                .excludePathPatterns("/test/a");
    }

}
