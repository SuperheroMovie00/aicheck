/*
 * Copyright  2018 Yiyuan Networks 上海义援网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.common.exception;

import com.aicheck.face.common.utils.AppBaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 4:19 PM 2019/1/22
 */
@SuppressWarnings("rawtypes")
@RestControllerAdvice
@Slf4j
public class FaceExceptionHandler extends AppBaseResult {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 自定义异常
     */
    
	@ExceptionHandler(FaceException.class)
    public AppBaseResult handleRRException(FaceException e){
        AppBaseResult appBaseResult = new AppBaseResult();
        appBaseResult.setCode(e.getCode());
        appBaseResult.setMessage(e.getMessage());
        return appBaseResult;
    }

    
	@ExceptionHandler(Exception.class)
    public AppBaseResult handleException(Exception e){
        if (e instanceof MethodArgumentNotValidException) {
            return AppBaseResult.error(400,e.getMessage());
        }
       log.error(e.getMessage(), e);
        return AppBaseResult.error();
    }
}
