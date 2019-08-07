package com.aicheck.face.modules.detest.service.impl;


import com.aicheck.face.modules.detest.entity.Detest;
import com.aicheck.face.modules.detest.repository.DetestRepository;
import com.aicheck.face.modules.detest.service.DetestService;

import com.aicheck.face.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service("testservice")
@Slf4j
public class DetestServiceimpl implements DetestService {
    @Autowired
    private DetestRepository detestRepository;
    @Resource
    private PlatformTransactionManager transactionManager;


    @Override
    public Detest save(Detest test) {
        return detestRepository.save(test);
    }

    @Override
    public List<Detest> save(List<Detest> t) {

        return detestRepository.saveAll(t);
    }

    @Override
    public void delete(Integer integer) {
        detestRepository.deleteById(integer);
    }

    @Override
    public Detest update(Detest test) {
        return detestRepository.save(test);
    }

    @Override
    public List<Detest> find(Detest test) {
        return null;
    }

    @Override
    public List<Detest> findAll() {
        return detestRepository.findAll();
    }

    @Override
    public Detest findById(Integer integer) {
        Optional<Detest> optionalTest = detestRepository.findById(integer);
        return optionalTest.isPresent() ? optionalTest.get() : null;
    }


    /**
     * 以上是定式化的内容   以下就是根据自己的业务 添加的内容
     */


    //年龄正则
    String ageregex = "^(?:[1-9][0-9]?|1[01][0-9]|120)$";
    //手机号正则
    String pthoneregex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\\\d{8}$";
    //邮箱正则
    String emailregex = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    //ip正则
    String IPregex = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    //验证网址Url
    String urlregex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    //验证输入身份证号
    String cardregex = "(^\\d{18}$)|(^\\d{15}$)";
    //验证date
    String dateregex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
    //验证数字输入
    String numregex = "^[0-9]*$";


    /**
     * 新增
     * @param detest
     * @return
     */
    @Override
    public R add(Detest detest) {


        if (detest == null) {
            log.info("数据为空");
            return R.error("数据为空");
        } else {
            if (!Pattern.matches(ageregex, String.valueOf(detest.getAge()))) {
                log.info("年龄非法");
                return R.error("年龄非法");
            }

            if (!Pattern.matches(pthoneregex, detest.getPhone())) {
                log.info("手机号非法");
                return R.error("手机号非法");
            }

            if (!Pattern.matches(emailregex, detest.getEmail())) {
                log.info("邮箱非法");
                return R.error("邮箱非法");
            }

            DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
            defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);
            try {
                 Detest test1= detestRepository.save(detest);
                if(test1!=null){
                    transactionManager.commit(status);
                    log.info("新增成功");
                    return R.ok("新增成功");
                }else{
                    transactionManager.rollback(status);
                    log.info("新增成功");
                    return R.error("新增失败");
                }

            } catch (Exception e) {
                transactionManager.rollback(status);
                e.getMessage();
                log.info("新增失败"+e.getMessage());
                return R.error("新增失败");
            }
            finally {
                status.flush();
            }
        }


    }


    /**
     * 删除（根据id）
     * @param id
     * @return
     */
    @Override
    public R deleteforid(Integer id) {



        if (id == null) {
            log.info("新增成功");
            return R.error("数据为空");
        } else {
            if (!Pattern.matches(numregex, String.valueOf(id))) {
                log.info("要删除的id非法");
                return R.error("要删除的id非法");
            }
            Detest test = findById(id);
            if (test.getStatus().equals("1")) {
                log.info("要删除的数据状态不可操作");
                return R.error("要删除的数据状态不可操作");
            }

            DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
            defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);

            try {
                detestRepository.deleteById(id);
                Detest test1=findById(id);
                if(test1==null){
                    transactionManager.commit(status);
                    log.info("删除成功");
                    return R.ok("删除成功");
                }else{
                    transactionManager.rollback(status);
                    log.info("删除失败");
                    return R.error("删除失败");
                }

            } catch (Exception e) {
                e.getMessage();
                transactionManager.rollback(status);
                log.info("删除失败"+e.getMessage());
                return R.error("删除失败");
            }
            finally {
                status.flush();
            }
        }
    }

    /**
     * 修改
     * @param detest
     * @return
     */
    @Override
    public R updatetest(Detest detest) {



        Detest testdb = findById(detest.getId());
        if (detest == null ) {
            log.info("数据为空");
            return R.error("数据为空");

            }else{
            if(testdb==null){
                log.info("要修改的数据为空");
                return R.error("要修改的数据为空");
            }else{



            if (!Pattern.matches(ageregex, String.valueOf(detest.getAge()))) {
                log.info("年龄非法");
                return R.error("年龄非法");
            }

            if (!Pattern.matches(pthoneregex, detest.getPhone())) {
                log.info("手机号非法");
                return R.error("手机号非法");
            }

            if (!Pattern.matches(emailregex, detest.getEmail())) {
                log.info("邮箱非法");
                return R.error("邮箱非法");
            }

            if(testdb.getStatus().equals("1")){
                log.info("要删除的数据状态不可操作");
                return R.error("要删除的数据状态不可操作");
            }

            DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
            defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);

            try {
                Detest test1=detestRepository.save(detest);
                if(test1!=null){
                    transactionManager.commit(status);
                    log.info("修改成功");
                    return R.ok("修改成功");
                }else{
                    transactionManager.rollback(status);
                    log.info("修改失败");
                    return R.error("修改失败");
                }

            } catch (Exception e) {
                e.getMessage();
                transactionManager.rollback(status);
                log.info("修改失败"+e.getMessage());
                return R.error("修改失败");
            }
            finally {
                status.flush();
            }

            }
        }



    }
}