/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.quartz.service;

import com.aicheck.face.modules.quartz.entity.JobDetails;

import java.util.List;
import java.util.Map;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:07 PM 2019/4/8
 */
public interface JobAndTriggerService {

    String findByCron();

    /**
     * @param @param  search
     * @param @return 参数
     * @return Map<String   ,   Object>    返回类型
     * @throws
     * @Title: getPageJob
     * @Description: TODO(查询定时任务 ， 分页)
     */
    Map<String, Object> getPageJob(Integer currentPage, Integer pageSize);

    /**
     * @param @return 参数
     * @return JobAndTriggerDto    返回类型
     * @throws
     * @Title: getPageJobmod
     * @Description: TODO(查询定时任务)
     */
    List<JobDetails> getPageJobmod();

    /**
     * @param @param  jobClassName 任务路径名称
     * @param @param  jobGroupName 任务分组
     * @param @param  cronExpression cron时间规则
     * @param @throws Exception    参数
     * @return void    返回类型
     * @throws
     * @Title: addJob
     * @Description: TODO(添加任务)
     */
    void addJob(String jobClassName, String jobGroupName, String cronExpression);

    /**
     * @param @param  jobClassName 任务路径名称
     * @param @param  jobGroupName 任务分组
     * @param @param  cronExpression cron时间规则
     * @param @param  jobDescription 参数
     * @param @param  params
     * @param @throws Exception  参数说明
     * @return void    返回类型
     * @throws
     * @Title: addJob
     * @Description: TODO(添加动态任务)
     */
    void addJob(String jobClassName, String jobGroupName, String cronExpression, String jobDescription, Map<String, Object> params);

    /**
     * @param @param  jobClassName 任务路径名称
     * @param @param  jobGroupName 任务分组
     * @param @param  cronExpression cron时间规则
     * @param @throws Exception    参数
     * @return void    返回类型
     * @throws
     * @Title: updateJob
     * @Description: TODO(更新定时任务)
     */
    void updateJob(String jobClassName, String jobGroupName, String cronExpression);

    /**
     * @param @param  jobClassName 任务路径名称
     * @param @param  jobGroupName 任务分组
     * @param @throws Exception    参数
     * @return void    返回类型
     * @throws
     * @Title: deleteJob
     * @Description: TODO(删除定时任务)
     */
    void deleteJob(String jobClassName, String jobGroupName);

    /**
     * @param @param  jobClassName 任务路径名称
     * @param @param  jobGroupName 任务分组
     * @param @throws Exception    参数
     * @return void    返回类型
     * @throws
     * @Title: pauseJob
     * @Description: TODO(暂停定时任务)
     */
    void pauseJob(String jobClassName, String jobGroupName);

    /**
     * @param @param  jobClassName 任务路径名称
     * @param @param  jobGroupName 任务分组
     * @param @throws Exception    参数
     * @return void    返回类型
     * @throws
     * @Title: resumejob
     * @Description: TODO(恢复任务)
     */
    void resumejob(String jobClassName, String jobGroupName);
}
