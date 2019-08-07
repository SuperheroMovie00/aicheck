/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.advertisingImages.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 10:32 AM 2019/4/9
 */
public class FileSuffixUtils {

    public static boolean checkSuffix(String imgPath) {
        Boolean flag =false;
        //图片格式
        String[] FILETYPES = new String[]{
                ".jpg", ".bmp", ".jpeg", ".png", ".gif",
                ".JPG", ".BMP", ".JPEG", ".PNG", ".GIF"
        };
        if(!StringUtils.isBlank(imgPath)){
            for (int i = 0; i < FILETYPES.length; i++) {
                String fileType = FILETYPES[i];
                if (imgPath.endsWith(fileType)) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }
}
