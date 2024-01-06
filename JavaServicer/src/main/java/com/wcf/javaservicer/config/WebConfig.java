package com.wcf.javaservicer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    //跨域处理
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                 //允许访问路径
                .addMapping("/**")
                //配置请求来源
                .allowedOrigins("http://localhost:8080")
                //允许跨域访问的方法
                .allowedMethods("GET","POST","DELETE","PUT","OPTION")
                //允许存在请求头
                .allowCredentials(true)

                .allowedHeaders("*")
                //最长请求时长
                .maxAge(3600);
        
    }
}
