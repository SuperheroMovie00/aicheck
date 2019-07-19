/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.schedule;

import com.aicheck.face.common.utils.FileUtils;
import com.aicheck.face.common.utils.PropertiesUtils;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 11:34 AM 2019/3/5
 */
@Component
//@Slf4j
public class FolderSchedule {

//    @Scheduled(cron = "0 0 0 1 * ?")
    public void monitorFolder() {

        String path = PropertiesUtils.getInstance().getProperties("visitors");
        File file = new File(path);
        if (file.exists()) {   //文件或文件夹是否存在
            if (file.isDirectory()) {   //判断是否是目录
                FileUtils.delete(file);
            }
        }
    }


}
