/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.quartz.service.impl;

import com.aicheck.face.modules.quartz.base.BaseJob;
import com.aicheck.face.modules.quartz.entity.JobDetails;
import com.aicheck.face.modules.quartz.repository.JobDetailsRepository;
import com.aicheck.face.modules.quartz.service.JobAndTriggerService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:12 PM 2019/4/8
 */
@Service
@Slf4j
public class JobAndTriggerServiceImpl implements JobAndTriggerService {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private JobDetailsRepository jobDetailsRepository;

    @Override
    public String findByCron() {
        return jobDetailsRepository.findByCron();
    }

    @Override
    public Map<String, Object> getPageJob(Integer currentPage, Integer pageSize) {
        return null;
    }

    @Override
    public List<JobDetails> getPageJobmod() {
        return jobDetailsRepository.findByAll();
    }

    @Override
    public void addJob(String jobClassName, String jobGroupName, String cronExpression) {
        // 启动调度器
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        // 构建job信息
        JobDetail jobDetail = null;
        try {
            jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass())
                    .withIdentity(jobClassName, jobGroupName).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

//        SimpleScheduleBuilder.repeatHourlyForever()

        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName)
                .withSchedule(scheduleBuilder).build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
            log.info("创建定时任务成功");

        } catch (SchedulerException e) {
            log.error("创建定时任务失败");
            log.error(e.getMessage());
        }

    }

    @Override
    public void addJob(String jobClassName, String jobGroupName, String cronExpression, String jobDescription,
                       Map<String, Object> params) {

        // 启动调度器
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        // 构建job信息
        JobDetail jobDetail = null;
        try {
            jobDetail = JobBuilder.newJob(JobAndTriggerServiceImpl.getClass(jobClassName).getClass())
                    .withIdentity(jobClassName, jobGroupName).withDescription(jobDescription).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Iterator<Map.Entry<String, Object>> var7 = params.entrySet().iterator();
        while (var7.hasNext()) {
            Map.Entry<String, Object> entry = var7.next();
            jobDetail.getJobDataMap().put((String) entry.getKey(), entry.getValue());
        }
        log.info("jobDetail数据：--------" + jobDetail.toString());
        // 表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName)
                .withSchedule(scheduleBuilder).build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
            log.info("创建定时任务成功");

        } catch (SchedulerException e) {
            log.error("创建定时任务失败");
            log.error(e.getMessage());
        }
    }

    @Override
    public void updateJob(String jobClassName, String jobGroupName, String cronExpression) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            // 表达式调度构建器（动态修改后不立即执行）
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            log.info("更新定时任务失败");
            log.error(e.getMessage());
        }
    }

    @Override
    public void deleteJob(String jobClassName, String jobGroupName) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
            scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (SchedulerException e) {

            log.error(e.getMessage());
        }

    }

    @Override
    public void pauseJob(String jobClassName, String jobGroupName) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void resumejob(String jobClassName, String jobGroupName) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    public static BaseJob getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob) class1.newInstance();
    }
}
