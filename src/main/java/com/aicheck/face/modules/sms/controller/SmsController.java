/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.sms.controller;

import com.alibaba.fastjson.JSON;
import com.aicheck.face.common.constant.ResultEnum;
import com.aicheck.face.modules.sms.DefaultSmsSenderImpl;
import com.aicheck.face.modules.sms.constant.SmsEnum;
import com.aicheck.face.modules.sms.entity.SmsTemplateInfo;
import com.aicheck.face.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.regex.Pattern;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:01 PM 2018/12/4
 */
@RestController
@RequestMapping("/v1/sms")
@Slf4j
public class SmsController {
    @Autowired
    private DefaultSmsSenderImpl smsSender;

    @GetMapping
    public R templateList() {

        List<Map<String,Object>> maps = new ArrayList<>();

        for (SmsEnum smsEnum : SmsEnum.values()) {

            Map<String,Object> map = new HashMap<>();
            map.put("templateId",smsEnum.getCode());
            map.put("templateContent",smsEnum.getTemplate());

            maps.add(map);
        }

        return R.ok(maps);
    }

    @PostMapping("/sender/code")
    public R defaultSenderSms(@RequestParam(value = "mobile") String mobile) {

        boolean b = Pattern.matches("^[1](([3][0-9])|([4][5,7,9])|([5][0-9])|([6][6])|([7][3,5,6,7,8])|([8][0-9])|([9][8,9]))[0-9]{8}$",mobile);

        if (!b) {
            return R.error(ResultEnum.MOBILE_FORMAT_NOT.getCode(),ResultEnum.MOBILE_FORMAT_NOT.getMsg());
        }

        String code = RandomStringUtils.randomNumeric(6);

        smsSender.sender(mobile,code);

        return R.ok();
    }

    @PostMapping("/sender")
    public R defaultSenderSms(@RequestBody @Valid SmsTemplateInfo smsTemplateInfo) {

        log.info("senderController接收参数:{}",JSON.toJSONString(smsTemplateInfo));

        smsSender.sender(smsTemplateInfo);

        return R.ok();
    }




}
