package com.aicheck.face.modules.pathseting.controller;


import com.aicheck.face.modules.pathseting.entity.pathseting;
import com.aicheck.face.modules.pathseting.entity.pathsetinginput;
import com.aicheck.face.modules.pathseting.service.pathsetingService;
import com.aicheck.face.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/v1/pathseting")
@Slf4j
public class pathsetingController {
    @Autowired
    private pathsetingService pathsetingService;


    /**
     * 修改资源路径
     */
    @PostMapping("/updatepath")
    public R importExcel(pathseting pathseting) {

        //首先根据type查询一下有没有这条设置
        pathseting path = null;
        pathseting pathfortype = pathsetingService.findpathfortype(pathseting.getType());
        if (pathfortype == null) {
            path = pathsetingService.save(pathseting);
        } else {
            path = pathsetingService.save(pathfortype);
        }

        if (path == null) {
            return R.error();
        }
        return R.ok();
    }


    /**
     * 修改资源路径
     */
    @PostMapping("/updatepathfore")
    public R importExcelfore(pathsetinginput pathsetinginput) {

        /**
         * 处理访客客流资源的路径
         */

        pathseting pathforvisitors = pathsetingService.findpathfortype("visitors");
        if (pathforvisitors != null) {
            pathforvisitors.setPath(pathsetinginput.getVisitorspath());
            pathforvisitors.setModificationTime(new Date());
            pathsetingService.save(pathforvisitors);
        } else {
            pathseting pvisitors = new pathseting();
            pvisitors.setType("visitors");
            pvisitors.setPath(pathsetinginput.getVisitorspath());
            pvisitors.setCreateTime(new Date());
            pathsetingService.save(pvisitors);
        }


        /**
         * 处理访客客流资源的路径
         */

        pathseting pathforcustomer = pathsetingService.findpathfortype("customer");
        if (pathforcustomer != null) {
            pathforcustomer.setPath(pathsetinginput.getCustomerpath());
            pathforcustomer.setModificationTime(new Date());
            pathsetingService.save(pathforcustomer);
        } else {
            pathseting pcustomer = new pathseting();
            pcustomer.setType("customer");
            pcustomer.setPath(pathsetinginput.getCustomerpath());
            pcustomer.setCreateTime(new Date());
            pathsetingService.save(pcustomer);
        }

        /**
         * 处理显示资源的AI文件夹路径
         */

        pathseting pathforai = pathsetingService.findpathfortype("ai");
        if (pathforai != null) {
            pathforai.setPath(pathsetinginput.getAipath());
            pathforai.setModificationTime(new Date());
            pathsetingService.save(pathforai);
        } else {
            pathseting pai = new pathseting();
            pai.setType("ai");
            pai.setPath(pathsetinginput.getAipath());
            pai.setCreateTime(new Date());
            pathsetingService.save(pai);
        }

        /**
         * 处理清理时间周期
         */
        pathseting pathfordeletevisitorytime = pathsetingService.findpathfortype("deletevisitorytime");
        if (pathfordeletevisitorytime != null) {
            pathfordeletevisitorytime.setPath(pathsetinginput.getDeletevisitorytime());
            pathfordeletevisitorytime.setModificationTime(new Date());
            pathsetingService.save(pathforai);
        } else {
            pathseting ppathfordeletevisitorytime = new pathseting();
            ppathfordeletevisitorytime.setType("deletevisitorytime");
            ppathfordeletevisitorytime.setPath(pathsetinginput.getDeletevisitorytime());
            ppathfordeletevisitorytime.setCreateTime(new Date());
            pathsetingService.save(ppathfordeletevisitorytime);
        }


        return R.ok();
    }


    @GetMapping("/findallpath")
    public R findallpath() {

        List<pathseting> pathsetings=pathsetingService.findAll();

         return R.ok(pathsetings);
    }




}
