/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aicheck.face.modules.sms.constant.SmsConstant;
import com.aicheck.face.modules.sms.constant.SmsEnum;
import com.aicheck.face.modules.sms.entity.SmsTemplateInfo;
import com.aicheck.face.modules.sms.exception.SmsException;
import com.aicheck.face.modules.sms.tools.HttpsInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.UnsupportedEncodingException;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 2:22 PM 2018/12/3
 */
@Service("smsSender")
@Slf4j
public class DefaultSmsSenderImpl implements SmsSender {
    @Override
    public String sender(SmsTemplateInfo smsTemplateInfo) throws SmsException {

        log.info("开始发送短信：{}",JSON.toJSONString(smsTemplateInfo));

        SmsEnum smsEnum = SmsEnum.getSmsEnum(smsTemplateInfo.getCode());

        if (smsEnum == null) {

            return "";
        }

//        SmsTemplateInfo smsTemplateInfo = new SmsTemplateInfo();
//        smsTemplateInfo.setOneStr("测试");
//        smsTemplateInfo.setTwoStr("测试");
//        smsTemplateInfo.setThreeStr("测试");
//        List<String> stringList = new ArrayList<>();
//        stringList.add("15573303386");
//        smsTemplateInfo.setReceivers(stringList);

        HttpsURLConnection https;
        String result = "";
        try {

           https =  HttpsInit.initHttps(SmsConstant.URL);

            JSONObject jsonObject = requestData(smsEnum.getCode(),smsTemplateInfo);

            result = HttpsInit.RequestHttps(jsonObject,https);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    @Override
    public String sender(String mobile, String code) {
        HttpsURLConnection https;
        String result = "";
        try {

            https =  HttpsInit.initHttps(SmsConstant.URL);

            JSONObject jsonObject = requestData(SmsEnum.VERIFICATION_CODE.getCode(), mobile,code);

            result = HttpsInit.RequestHttps(jsonObject,https);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private JSONObject requestData(int templateId,String mobile,String code) {

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(code);
        jsonArray.add(10);
        jsonObject.put("to",mobile);
        jsonObject.put("appId",SmsConstant.APP_ID);
        jsonObject.put("templateId",templateId);
        jsonObject.put("datas",jsonArray);

        return jsonObject;
    }


    private JSONObject requestData(int code, SmsTemplateInfo smsTemplateInfo) throws UnsupportedEncodingException {

        if (CollectionUtils.isEmpty(smsTemplateInfo.getReceivers())) {

            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();

        smsTemplateInfo.getReceivers().forEach(str -> {

            if (!StringUtils.isEmpty(str)) {

                stringBuilder.append(str).append(",");
            }

        });

        String mobile = stringBuilder.substring(0,stringBuilder.lastIndexOf(","));

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(smsTemplateInfo.getOneStr());
        jsonArray.add(smsTemplateInfo.getTwoStr());
        jsonArray.add(smsTemplateInfo.getThreeStr());
        jsonObject.put("to",mobile);
        jsonObject.put("appId",SmsConstant.APP_ID);
        jsonObject.put("templateId",code);
        jsonObject.put("datas",jsonArray);

        return jsonObject;
    }

//    public static void main(String[] args) {
//        DefaultSmsSenderImpl smsSender = new DefaultSmsSenderImpl();
//        String result = smsSender.sender(394907);
//        System.out.println(result);
//    }
}
