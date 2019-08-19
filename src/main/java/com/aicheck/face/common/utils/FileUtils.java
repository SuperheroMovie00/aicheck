/*
 * Copyright  2018 LianChao Networks 上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.common.utils;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

import org.apache.commons.codec.binary.Base64;

import java.io.*;

/**
 * @Author: liaojin
 * @Description:
 * @Date: 4:01 PM 2019/1/22
 */
public class FileUtils {
    /**
     * 图片转base64
     *
     * @param imgFile
     * @return
     */
    public static String getImageBase64Str(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
       return Base64.encodeBase64String(data);
    
    }

    public static byte[] getImageBytes(String imgFile) {

        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static boolean generateImage(String imgStr, String path) {
        if (imgStr == null)
            return false;
        	//BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密 
        	byte[] b = Base64.decodeBase64(imgStr);   
            //byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 图片转base64
     *
     * @param file
     * @return
     */
    public static String getImageBase64Str(File file) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(file);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        //BASE64Encoder encoder = new BASE64Encoder();
        //return encoder.encode(data);
        return Base64.encodeBase64String(data);
        
    }

    public static String getBase64StrByBytes(byte[] bytes) {

        //BASE64Encoder encoder = new BASE64Encoder();
        //return encoder.encode(bytes);
        return Base64.encodeBase64String(bytes);
    }

    public static void delete(File file) {
        //获得文件里面所有的文件及文件夹
        File[] files = file.listFiles();
//        //创建您要写入的日志文件
//        String file1 = dirPathLog + "/del_path" + SystemParameter.currentTimeMillis() + ".txt";   //写入的是否操作
        //遍历files里面的所有文件及文件夹

        for (File f : files) {

            //获得绝对路径下的文件及文件夹
            File absFile = f.getAbsoluteFile();

            //计算时间
            long day = 30;
            long hour = 24;
            long minute = 60;
            long second = 60;
            long ms = 1000;


            long currTime = System.currentTimeMillis();   //当前时间


            long lastTime = absFile.lastModified();     //文件被最后一次修改的时间

            //时间差
            long differences = currTime - lastTime;

            long thDay = day * hour * minute * second * ms;

            if (differences <= thDay) {
                absFile.delete();
                if (absFile.isDirectory()) {
                    delete(absFile);
                    absFile.delete();
                }
            }

//            SystemParameter.out.println("当前时间：" + currTime);
//            SystemParameter.out.println("文件最后被修改的时间" + lastTime);
//            SystemParameter.out.println("时间差：" + differences);
//            SystemParameter.out.println("30天的时间：" + thDay);
        }
    }



}
