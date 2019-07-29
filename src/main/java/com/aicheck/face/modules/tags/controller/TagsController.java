/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.modules.tags.controller;

import com.aicheck.face.common.constant.ResultEnum;
import com.aicheck.face.common.utils.BeanUtils;
import com.aicheck.face.common.utils.UpdateUtils;
import com.aicheck.face.modules.tags.entity.Tags;
import com.aicheck.face.modules.tags.entity.TagsForm;
import com.aicheck.face.modules.tags.service.TagsService;
import com.aicheck.face.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 3:53 PM 2018/11/26
 */
@RestController
@RequestMapping("/v1/tags")
public class TagsController {
    @Autowired
    private TagsService tagsService;

    @GetMapping
    public R findAllList(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage, @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize) {

        Page<Tags> page = tagsService.findAllList(currentPage,pageSize);
        if(page==null){
            return R.error("/v1/tags/get=>page为空");
        }

        return R.ok(page.getContent());
    }


    @GetMapping("/{id}")
    public R findById(@PathVariable Integer id) {

        Tags tags = tagsService.findById(id);
        if(tags==null){
            return R.error("/v1/tags//{id}=>tags为空");
        }
        return R.ok(tags);
    }
 

    //新写的根据会员id查询所拥有的标签
    @GetMapping("/querytagsforcustomerid")
    public R querytagsforcustomerid(@RequestParam(value = "customerid") Integer customerid) {
    	System.out.println(customerid);
    	List<Tags> tags=tagsService.querytagsforcustomerid(customerid);
        if(tags==null){
            return R.error("/v1/tags/querytagsforcustomerid=>tags为空");
        }
        return R.ok(tags);
    }
    
 
    @PostMapping
    public R save(@RequestBody @Valid List<TagsForm> tagsForm) {

        List<Tags> tags = BeanUtils.batchTransform(Tags.class,tagsForm);

        tags.forEach(tag -> {
            tag.setCreateId(1);
        });

        tags = tagsService.save(tags);

        return R.ok(tags);
    }


    @PutMapping("/{id}")
    public R update(@PathVariable Integer id, @RequestBody @Valid TagsForm tagsForm) {
        Tags tags = tagsService.findById(id);

        if (null == tags) {
            return R.error(ResultEnum.IS_NOT_EXIST.getCode(),ResultEnum.IS_NOT_EXIST.getMsg());
        }

        UpdateUtils.copyNullProperties(tagsForm,tags);

        tags = tagsService.update(tags);

        return R.ok(tags);
    }


    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        tagsService.delete(id);
        return R.ok();
    }


}
