/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.common.utils;

import org.springframework.util.StringUtils;

import java.io.*;
import java.util.Properties;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 11:12 AM 2019/3/5
 */
public class PropertiesUtils {


    public void settingProperties(String key,String value) {
        Properties prop = new Properties();
        try {
            File file = new File("user.properties");
            if (!file.exists()) {
                file.createNewFile();
            }
            InputStream in = new FileInputStream("user.properties");
            prop.load(in);
            OutputStream oFile = new FileOutputStream("user.properties");
            prop.setProperty(key, value);
            prop.store(oFile, null);
            oFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getProperties(String key) {
        Properties prop = new Properties();
        String value = "";
        try {
            File file = new File("user.properties");
            if (!file.exists()) {
                file.createNewFile();
            }
            InputStream in = new FileInputStream("user.properties");
            prop.load(in);
            value = prop.getProperty(key);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (StringUtils.isEmpty(value)) {

            if ("cameraPassword".equals(key)) {
                return "";
            }

            value = "username".equals(key)?"admin":getFolder(key);
        }
        return value;
    }

    private String getFolder(String key) {
        String folder = "";
        if ("visitors".equals(key)) {
            folder = "C:\\yy-face\\visitors\\";
        } else if ("ai".equals(key)) {
//            folder = "/Users/liaojin/Desktop/opencv/";
            folder = "C:\\yy-face\\ai\\";
        } else if ("customer".equals(key)) {
//            folder = "/Users/liaojin/Desktop/opencv/";
            folder = "C:\\yy-face\\customer\\";
        }
        return folder;
    }

    private static class SingletonInstance {

        private static final PropertiesUtils INSTANCE = new PropertiesUtils();

    }

    public static PropertiesUtils getInstance() {
        return PropertiesUtils.SingletonInstance.INSTANCE;
    }

}
