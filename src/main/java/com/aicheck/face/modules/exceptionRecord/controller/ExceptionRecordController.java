/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.exceptionRecord.controller;

import com.aicheck.face.common.constant.ResultEnum;
import com.aicheck.face.common.utils.UpdateUtils;
import com.aicheck.face.modules.exceptionRecord.entity.ExceptionRecord;
import com.aicheck.face.modules.exceptionRecord.entity.ExceptionRecordForm;
import com.aicheck.face.modules.exceptionRecord.service.ExceptionRecordService;
import com.aicheck.face.vo.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 1:45 PM 2019/1/24
 */
@RestController
@RequestMapping("/v1/exception-record")
public class ExceptionRecordController {
    @Autowired
    private ExceptionRecordService exceptionRecordService;

    @GetMapping
    public R findAll(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                     @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize) {

        Page<ExceptionRecord> page = exceptionRecordService.findAll(currentPage,pageSize);

        if(page==null){
            return R.error("/v1/exception-record/get=>page为空");
        }

        return R.ok(page.getContent());
    }

    @PostMapping
    public R save(@RequestBody ExceptionRecordForm exceptionRecordForm) {
        ExceptionRecord exceptionRecord = new ExceptionRecord();
        BeanUtils.copyProperties(exceptionRecordForm,exceptionRecord);
        exceptionRecord = exceptionRecordService.save(exceptionRecord);
        return R.ok(exceptionRecord);
    }

    @PutMapping("/{id}")
    public R update(@PathVariable Integer id,@RequestBody ExceptionRecordForm exceptionRecordForm) {
        ExceptionRecord exceptionRecord = exceptionRecordService.findById(id);

        if (exceptionRecord == null) {
            return R.error(ResultEnum.IS_NOT_EXIST.getCode(),ResultEnum.IS_NOT_EXIST.getMsg());
        }
        UpdateUtils.copyNullProperties(exceptionRecordForm,exceptionRecord);
        exceptionRecord = exceptionRecordService.update(exceptionRecord);
        return R.ok(exceptionRecord);
    }

    @DeleteMapping
    public R delete(@RequestParam(value = "ids") Integer[] ids) {

        exceptionRecordService.delete(ids);

        return R.ok();
    }


}
