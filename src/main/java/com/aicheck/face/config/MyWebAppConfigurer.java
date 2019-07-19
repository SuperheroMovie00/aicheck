/*
 * Copyright  2018 Yiyuan Networks 上海义援网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.config;

import com.aicheck.face.common.interceptor.AuthorizationInterceptor;
import com.aicheck.face.common.utils.PropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 1:58 PM 2018/11/23
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/images/**").addResourceLocations("file:////Users/liaojin/Desktop/opencv/");
        registry.addResourceHandler("/visitors/**").addResourceLocations("file:///" + PropertiesUtils.getInstance().getProperties("visitors"));
        registry.addResourceHandler("/ai/**").addResourceLocations("file:///" + PropertiesUtils.getInstance().getProperties("ai"));
        registry.addResourceHandler("/images/**").addResourceLocations("file:///" + PropertiesUtils.getInstance().getProperties("customer"));
//        registry.addResourceHandler("/ai/**").addResourceLocations("file:///" + PropertiesUtils.getProperties("ai"));
//            registry.addResourceHandler("/images/**").addResourceLocations("file:/usr/Java/TRT/img/");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/v1/**");
    }

}
