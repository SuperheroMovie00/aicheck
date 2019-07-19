/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.sms.tools;

import com.alibaba.fastjson.JSONObject;
import com.aicheck.face.modules.sms.constant.SmsConstant;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * @Author: liaojin
 * @Description: https 连接容联云 初始化
 * @Date: Created in 下午3:37 2018/10/22
 */
@Slf4j
public class HttpsInit {

    public static String RequestHttps(JSONObject jsonObject, HttpsURLConnection https) throws IOException {

        OutputStream out = https.getOutputStream();

        log.info("json短信参数：{}",jsonObject.toJSONString());

        out.write(jsonObject.toString().getBytes("UTF-8"));

        out.flush();

        out.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(https.getInputStream(),"UTF-8"));

        String result =br.readLine();

        log.info("短信发送返回状态：{}",result);

        return result;
    }

    public static HttpsURLConnection initHttps(String url) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        String date = sdf.format(new Date());

        String sigParameter = SmsConstant.ACCOUNT_SID+SmsConstant.AUTH_TOKEN+date;

        String md5Sig = MD5Utils.getMD5(sigParameter);

        String uri = url+md5Sig;

        Base64.Encoder encoder = Base64.getEncoder();

        String authorization = encoder.encodeToString((SmsConstant.ACCOUNT_SID+":"+date).getBytes());

        HttpsURLConnection https = HttpsUtils.getHttpsURLConnection(uri);

        https.setRequestMethod("POST");

        https.setRequestProperty("Content-type", "application/json;charset=UTF-8");

        https.setRequestProperty("Accept", "application/json;charset=UTF-8");

        https.setRequestProperty("Authorization", authorization);

        https.setInstanceFollowRedirects(false);

        return https;
    }
}
