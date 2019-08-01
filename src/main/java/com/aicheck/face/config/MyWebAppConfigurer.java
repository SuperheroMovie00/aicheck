/*
 * Copyright  2018 LianChao Networks 上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.config;

import com.aicheck.face.common.interceptor.AuthorizationInterceptor;
import com.aicheck.face.common.utils.PropertiesUtils;
import com.aicheck.face.modules.pathseting.entity.pathseting;
import com.aicheck.face.modules.pathseting.service.pathsetingService;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 1:58 PM 2018/11/23
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;
    @Autowired
    private pathsetingService pathsetingService;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String visitorspath="C://visitory",customerpath="C://customer",aipath="C://ai";
        List<pathseting> pathlist=pathsetingService.findAll();
        for(int i=0;i<pathlist.size();i++){
            if(pathlist.get(i).getType().equals("visitors")){
                visitorspath=pathlist.get(i).getPath();
            }
            if(pathlist.get(i).getType().equals("customer")){
                customerpath=pathlist.get(i).getPath();
            }
            if(pathlist.get(i).getType().equals("ai")){
                aipath=pathlist.get(i).getPath();
            }
        }


        //remark:date :2019-7-29 原因：将原来的定死的路径改为可以更该的路径
        if(false){
//      registry.addResourceHandler("/images/**").addResourceLocations("file:////Users/liaojin/Desktop/opencv/");
        registry.addResourceHandler("/visitors/**").addResourceLocations("file:///" + PropertiesUtils.getInstance().getProperties("visitors"));
        //registry.addResourceHandler("/visitors/**").addResourceLocations("file:///" + "D:\\aicheck-face\\visitors\\");
        registry.addResourceHandler("/ai/**").addResourceLocations("file:///" + PropertiesUtils.getInstance().getProperties("ai"));
        registry.addResourceHandler("/images/**").addResourceLocations("file:///" + PropertiesUtils.getInstance().getProperties("customer"));
//      registry.addResourceHandler("/ai/**").addResourceLocations("file:///" + PropertiesUtils.getProperties("ai"));
//      registry.addResourceHandler("/images/**").addResourceLocations("file:/usr/Java/TRT/img/");

        }

        /**
         * 配置服务器地址指向的本地地址
         */
        registry.addResourceHandler("/visitors/**").addResourceLocations("file:///" + visitorspath+"//");
        registry.addResourceHandler("/ai/**").addResourceLocations("file:///" + aipath+"//");
        registry.addResourceHandler("/images/**").addResourceLocations("file:///" + customerpath+"//");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/v1/**");
    }

}
