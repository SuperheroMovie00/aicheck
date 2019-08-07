package com.aicheck.face.modules.pathseting.controller;


import com.aicheck.face.modules.pathseting.entity.pathseting;
import com.aicheck.face.modules.pathseting.entity.pathsetinginput;
import com.aicheck.face.modules.pathseting.service.pathsetingService;
import com.aicheck.face.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.aicheck.face.vo.file.copyDir;


@RestController
@RequestMapping("/v1/pathseting")
@Slf4j
public class pathsetingController {
    @Autowired
    private pathsetingService pathsetingService;
    @Resource
    private PlatformTransactionManager transactionManager;




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
    public R importExcelfore(pathsetinginput pathsetinginput) throws IOException {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);

        /**
         * 处理访客客流资源的路径
         */
        if(pathsetinginput.getVisitorspath()!=null) {
            pathseting pathforvisitors = pathsetingService.findpathfortype("visitors");
            if (pathforvisitors != null) {
                //如果不相等，则修改
                if (!pathforvisitors.getPath().equals(pathsetinginput.getVisitorspath())) {
                    try {
                    try {
                        copyDir(pathforvisitors.getPath(), pathsetinginput.getVisitorspath());
                    }catch (Exception e){
                        log.info("源路径不存在");
                        return R.error("原路径不能不存在");
                    }
                        pathforvisitors.setPath(pathsetinginput.getVisitorspath());
                        pathforvisitors.setModificationTime(new Date());
                        pathsetingService.save(pathforvisitors);
                        transactionManager.commit(status);
                    }catch (Exception e){
                        transactionManager.rollback(status);
                        e.printStackTrace();
                    }
                }

            } else {
                try {
                    copyDir("C://visitory", pathsetinginput.getVisitorspath());
                } catch (Exception e) {
                    log.info("源路径不存在");
                    return R.error("原路径不能不存在");
                }
                pathseting pvisitors = new pathseting();
                pvisitors.setType("visitors");
                pvisitors.setPath(pathsetinginput.getVisitorspath());
                pvisitors.setCreateTime(new Date());
                pathsetingService.save(pvisitors);
            }
        }


        /**
         * 处理访客客流资源的路径
         */
        if(pathsetinginput.getCustomerpath()!=null) {
            pathseting pathforcustomer = pathsetingService.findpathfortype("customer");
            if (pathforcustomer != null) {
                if (!pathforcustomer.getPath().equals(pathsetinginput.getCustomerpath())) {
                try {
                    copyDir(pathforcustomer.getPath(), pathsetinginput.getCustomerpath());
                }catch (Exception e){
                    log.info("源路径不存在");
                    return R.error("原路径不能不存在");
                }
                    pathforcustomer.setPath(pathsetinginput.getCustomerpath());
                    pathforcustomer.setModificationTime(new Date());
                    pathsetingService.save(pathforcustomer);
                }
            } else {
                try {
                copyDir("C://customer", pathsetinginput.getCustomerpath());
            } catch (Exception e) {
                    log.info("源路径不存在");
                return R.error("原路径不能不存在");
            }
                pathseting pcustomer = new pathseting();
                pcustomer.setType("customer");
                pcustomer.setPath(pathsetinginput.getCustomerpath());
                pcustomer.setCreateTime(new Date());
                pathsetingService.save(pcustomer);
            }
        }

        /**
         * 处理显示资源的AI文件夹路径
         */

        if(pathsetinginput.getAipath()!=null){


        pathseting pathforai = pathsetingService.findpathfortype("ai");
        if (pathforai != null) {
            if(!pathforai.getPath().equals(pathsetinginput.getAipath())){
            try {
                copyDir(pathforai.getPath(), pathsetinginput.getAipath());
            }catch (Exception e){
                log.info("源路径不存在");
                return R.error("原路径不能不存在");
            }
            pathforai.setPath(pathsetinginput.getAipath());
            pathforai.setModificationTime(new Date());
            pathsetingService.save(pathforai);
            }
        } else {
            try {
                copyDir("C://ai", pathsetinginput.getAipath());
            } catch (Exception e) {
                log.info("源路径不存在");
                return R.error("原路径不能不存在");
            }
            pathseting pai = new pathseting();
            pai.setType("ai");
            pai.setPath(pathsetinginput.getAipath());
            pai.setCreateTime(new Date());
            pathsetingService.save(pai);
        }

        }

        /**
         * 处理清理时间周期
         */

        if (pathsetinginput.getDeletevisitorytime() != null) {
            pathseting pathfordeletevisitorytime = pathsetingService.findpathfortype("deletevisitorytime");
            if (pathfordeletevisitorytime != null) {
                if (!pathfordeletevisitorytime.getPath().equals(pathsetinginput.getDeletevisitorytime())) {
                    pathfordeletevisitorytime.setPath(pathsetinginput.getDeletevisitorytime());
                    pathfordeletevisitorytime.setModificationTime(new Date());
                    pathsetingService.save(pathfordeletevisitorytime);
                }
            } else {
                pathseting ppathfordeletevisitorytime = new pathseting();
                ppathfordeletevisitorytime.setType("deletevisitorytime");
                ppathfordeletevisitorytime.setPath(pathsetinginput.getDeletevisitorytime());
                ppathfordeletevisitorytime.setCreateTime(new Date());
                pathsetingService.save(ppathfordeletevisitorytime);
            }

        }


        return R.ok();
    }


    @GetMapping("/findallpath")
    public R findallpath() {

        List<pathseting> pathsetings=pathsetingService.findAll();

         return R.ok(pathsetings);
    }




}
