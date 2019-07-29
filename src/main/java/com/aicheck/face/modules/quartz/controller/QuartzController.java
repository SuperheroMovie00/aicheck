/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.quartz.controller;

import com.aicheck.face.modules.quartz.service.JobAndTriggerService;
import com.aicheck.face.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:22 PM 2019/4/8
 */
@RestController
@RequestMapping("/v1/quartz")
public class QuartzController {
    @Autowired
    private JobAndTriggerService jobAndTriggerService;

    @GetMapping
    public R find() {

        String s = jobAndTriggerService.findByCron();
        if(s==null){
            return R.error("/v1/quartz/Get=>s为空");
        }

        return R.ok(s);
    }

    @PostMapping
    public R save(@RequestParam(value = "jobClassName",defaultValue = "com.aicheck.face.modules.schedule.QuartzFolderJob") String jobClassName,
                  @RequestParam(value = "jobGroupName",defaultValue = "QuartzFolderJobGroup") String jobGroupName,
                  @RequestParam(value = "cronExpression",defaultValue = "0 0 0 1 * ?") String cronExpression) {

        jobAndTriggerService.addJob(jobClassName,jobGroupName,cronExpression);

        return R.ok();
    }


    @PostMapping("/pause")
    public R pause(@RequestParam(value = "jobClassName") String jobClassName,
                   @RequestParam(value = "jobGroupName") String jobGroupName) {

        jobAndTriggerService.pauseJob(jobClassName,jobGroupName);

        return R.ok();
    }

    @PostMapping("/resume")
    public R resume(@RequestParam(value = "jobClassName") String jobClassName,
                    @RequestParam(value = "jobGroupName") String jobGroupName) {

        jobAndTriggerService.resumejob(jobClassName,jobGroupName);

        return R.ok();
    }

    @PostMapping("/reschedule")
    public R reschedule(@RequestParam(value = "jobClassName",defaultValue = "com.aicheck.face.modules.schedule.QuartzFolderJob") String jobClassName,
                        @RequestParam(value = "jobGroupName",defaultValue = "QuartzFolderJobGroup") String jobGroupName,
                        @RequestParam(value = "cronExpression") String cronExpression) {

        jobAndTriggerService.updateJob(jobClassName,jobGroupName,cronExpression);

        return R.ok();
    }

    @DeleteMapping
    public R delete(@RequestParam(value = "jobClassName",defaultValue = "com.aicheck.face.modules.schedule.QuartzFolderJob") String jobClassName,
                    @RequestParam(value = "jobGroupName",defaultValue = "QuartzFolderJobGroup") String jobGroupName) {

        jobAndTriggerService.deleteJob(jobClassName,jobGroupName);

        return R.ok();
    }
}
