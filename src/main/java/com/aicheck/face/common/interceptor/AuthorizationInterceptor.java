/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.common.interceptor;

import com.aicheck.face.common.constant.AuthorizationEnum;
import com.aicheck.face.common.exception.FaceException;
import com.aicheck.face.common.utils.ApiUtil;
import com.aicheck.face.common.utils.MD5Util;
import com.aicheck.face.modules.device.entity.Device;
import com.aicheck.face.modules.device.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.net.InetAddress;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 4:09 PM 2019/1/22
 */
@Component
@Slf4j
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private DeviceService deviceservice;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        response.setHeader("Access-Control-Allow-Origin", "*");


        // 随机字符串
        String timestamp = request.getHeader("timestamp");
        String nonce = request.getHeader("nonce");
        String sign = request.getHeader("sign");
        String mac = request.getHeader("mac");

        if (mac == null) {
            mac = "";
        }
        /**
         * 如果请求头中取出的内容都为空的话就从请求参数中取
         */
        if (timestamp == null && nonce == null && sign == null) {
            timestamp = request.getParameter("timestamp");
            nonce = request.getParameter("nonce");
            sign = request.getParameter("sign");

        }

        if (true) {
            String ip = null;
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if (ip != null) {
                // 对于通过多个代理的情况，最后IP为客户端真实IP,多个IP按照','分割
                int position = ip.indexOf(",");
                if (position > 0) {
                    ip = ip.substring(0, position);
                }
            }


            if (timestamp == null || "".equals(timestamp)) {
                throw new FaceException("timestamp 不能为空", 400);
            } else if (nonce == null || "".equals(nonce)) {
                throw new FaceException("nonce 不能为空", 400);
            } else if (sign == null || "".equals(sign)) {
                throw new FaceException("sign 不能为空", 400);
            }

            // 请求时间间隔
            // long requestInterval = SystemParameter.currentTimeMillis() - Long.valueOf(timestamp);

            // if (requestInterval >=
            // Integer.parseInt(AuthorizationEnum.EXPIRE_TIME.getValue())) {
            //
            // throw new FaceException("请求超时，请重新请求", 400);
            // }
            //
            // 校验签名(将所有的参数加进来，防止别人篡改参数) 所有参数看参数名升续排序拼接成url
            // 请求参数 + token + timestamp + nonce

            String signString = ApiUtil.concatSignString(request) + AuthorizationEnum.TOKEN.getValue() + timestamp
                    + nonce + mac;

            log.info("加密之前的" + signString);

            log.info(request.getParameterMap().toString());
            String signature = MD5Util.getMD5(signString);

            log.info("加密之后的sign=>" + signature.toUpperCase());

            if (!signature.equals(sign)) {
                throw new FaceException("签名错误", 400);
            } else {
                String serverip = InetAddress.getLocalHost().getHostAddress();
                if (!ip.equals("127.0.0.1")) {

                    Device device = deviceservice.finddeviceforplatformandmacaddress("mobile", mac);
                    if (device == null) {
                        device = new Device();
                        device.setMacAddress(mac);
                        device.setCreateTime(new Date());

                        device.setIpAddress("/" + ip);
                        device.setPlatform("mobile");

                        deviceservice.save(device);


                    } else {
                        device.setCreateTime(new Date());
                        device.setIpAddress("/" + ip);
                        deviceservice.save(device);
                    }
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.info("afterCompletion ----------ex => " + ex);
        response.setHeader("Access-Control-Allow-Origin", "*");

    }

}
