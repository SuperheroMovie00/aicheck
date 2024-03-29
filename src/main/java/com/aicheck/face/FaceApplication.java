/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;


import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@ImportResource(locations = { "classpath:druid-bean.xml" })
@ServletComponentScan
@EnableScheduling
public class FaceApplication {

    public static void main(String[] args) {
    	
    	/*VideoStatistic demo = new VideoStatistic();
    	demo.InitTest();
    	Date date=new Date();
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
 		
 		SystemParameter.out.println("开始时间"+calendar.getTime());
 		SystemParameter.out.println("结束时间"+date);
 		
    	demo.startFindNumberStatrewrite(calendar.getTime(),date);*/
    	
    	SpringApplication.run(FaceApplication.class, args);
        
    	
    }

    /**
     * 文件上传配置
     * @return
     */

	@Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("500MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("1024MB");
        return factory.createMultipartConfig();
    }

}

