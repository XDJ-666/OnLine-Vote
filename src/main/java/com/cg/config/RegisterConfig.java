package com.cg.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RegisterConfig implements WebMvcConfigurer {
    @Autowired
    private AdminLoginInterceptor admin;
    @Autowired
    private AudienceLoginInterceptor audience;
    @Autowired
    private JudgeLoginInterceptor judge;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(admin)
                .addPathPatterns("/main/**")
                //排除静态资源
                .excludePathPatterns("/toLogin","/**/*.js","/**/*.css","/img/**","/**/*.jpg","/toRegister","/doLogin","/showResult");
        registry.addInterceptor(audience)
                .addPathPatterns("/poll/addPoll/**")
                .excludePathPatterns("/toLogin","/**/*.js","/**/*.css","/img/**","/**/*.jpg","/toRegister","/doLogin","/showResult");
        registry.addInterceptor(judge)
                .addPathPatterns("/judge/addScore")
                .excludePathPatterns("/toLogin","/**/*.js","/**/*.css","/**/*.jpg","/toRegister","/doLogin","/showResult");

    }



}
