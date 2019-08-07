package com.aicheck.face.modules.detest.controller;


import com.aicheck.face.modules.detest.entity.Detest;
import com.aicheck.face.modules.detest.service.DetestService;
import com.aicheck.face.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/detest")
@Slf4j
public class DetestController {

    @Autowired
    private DetestService detestService;

    /**
     * 新增
     * @param test
     * @return
     */
    @PostMapping("/addtest")
    public R addtest(Detest test){
        return detestService.add(test);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/deletetest")
    public R deletetest(Integer id){
        return detestService.deleteforid(id);
    }

    /**
     * 修改
     * @param test
     * @return
     */
    @PostMapping("/updatetest")
    public R updatetest(Detest test){
        return detestService.updatetest(test);
    }




}
