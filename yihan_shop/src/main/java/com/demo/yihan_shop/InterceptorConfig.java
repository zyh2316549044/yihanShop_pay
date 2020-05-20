package com.demo.yihan_shop;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description:给springboot配置拦截信息
 * @author: zhangyihan
 * @createDate: 2020-05-03 20:20
 * @version: 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/mallUser/registered","/mallUser/login","/cate","/products","/products/*");
    }
}
