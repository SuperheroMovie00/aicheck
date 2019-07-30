/*
 * Copyright  2018 LianChao Networks 上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.common.utils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 11:40 AM 2018/11/23
 */
public class FileUploadUtils {
    /**
     * 文件上传
     *
     * @param file
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + "/" + fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
