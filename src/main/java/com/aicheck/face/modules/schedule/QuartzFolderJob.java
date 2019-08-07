/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.schedule;

import com.aicheck.face.modules.quartz.base.BaseJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 4:33 PM 2019/4/8
 */
@Slf4j
public class QuartzFolderJob implements BaseJob {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("清空文件夹定时任务执行");
//        String path = PropertiesUtils.getProperties("visitors");
//        File file = new File(path);
//        if (file.exists()) {   //文件或文件夹是否存在
//            if (file.isDirectory()) {   //判断是否是目录
//                FileUtils.delete(file);
//            }
//        }
    }
}
