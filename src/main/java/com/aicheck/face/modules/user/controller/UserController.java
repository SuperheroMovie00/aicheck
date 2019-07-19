package com.aicheck.face.modules.user.controller;

import com.aicheck.face.common.utils.PropertiesUtils;
import com.aicheck.face.vo.R;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 10:29 AM 2019/2/13
 */
@RestController
@RequestMapping("/v1/user")
public class UserController {

    @PostMapping("/folder")
    public R updateFolder(@RequestParam(value = "type") Integer type,@RequestParam(value = "path") String path) {

        String key = "";

        switch (type) {
            case 1:key = "visitors";break; //访客图片文件夹
            case 2:key = "ai";break;    // ai 图片与视频文件夹
            case 3:key = "customer";break; // 客户头像文件夹
        }

        PropertiesUtils.getInstance().settingProperties(key,path);

        return R.ok();
    }

    @PutMapping("/camera")
    public R setCameraPassword(@RequestParam(value = "password") String password) {

        PropertiesUtils.getInstance().settingProperties("cameraPassword",password);

        return R.ok();
    }

    @GetMapping("/folder")
    public R findFolder() {
        Map<String,String> map = new HashMap<>();
        map.put("visitors",PropertiesUtils.getInstance().getProperties("visitors"));
        map.put("ai",PropertiesUtils.getInstance().getProperties("ai"));
        map.put("customer",PropertiesUtils.getInstance().getProperties("customer"));
        map.put("camera", PropertiesUtils.getInstance().getProperties("cameraPassword"));
        return R.ok(map);
    }

    @PostMapping
    public R update(@RequestParam(value = "username") String username) {

        PropertiesUtils.getInstance().settingProperties("username",username);

        return R.ok();
    }


    @GetMapping
    public R find() {

        String username = PropertiesUtils.getInstance().getProperties("username");

        return R.ok(username);
    }
}
