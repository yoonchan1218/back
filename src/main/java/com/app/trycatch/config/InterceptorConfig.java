package com.app.trycatch.config;

import com.app.trycatch.interceptor.IndividualAlramInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    private final IndividualAlramInterceptor individualAlramInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(individualAlramInterceptor)
                .addPathPatterns("/qna/**")
                .addPathPatterns("/mypage/**")
                .addPathPatterns("/skill-log/**");
    }
}
