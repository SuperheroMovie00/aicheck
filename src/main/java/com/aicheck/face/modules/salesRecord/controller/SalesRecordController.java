/*
 * Copyright  上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.modules.salesRecord.controller;

import com.aicheck.face.common.constant.ResultEnum;
import com.aicheck.face.common.utils.UpdateUtils;
import com.aicheck.face.modules.customer.entity.Customer;
import com.aicheck.face.modules.customer.service.CustomerService;
import com.aicheck.face.modules.customer.vo.CustomerVO;
import com.aicheck.face.modules.product.entity.Product;
import com.aicheck.face.modules.product.service.ProductService;
import com.aicheck.face.modules.salesDetail.entity.SalesDetail;
import com.aicheck.face.modules.salesDetail.service.SalesDetailService;
import com.aicheck.face.modules.salesDetail.vo.SalesDetailVO;
import com.aicheck.face.modules.salesRecord.entity.SalesProductForm;
import com.aicheck.face.modules.salesRecord.entity.SalesRecord;
import com.aicheck.face.modules.salesRecord.entity.SalesRecordForm;
import com.aicheck.face.modules.salesRecord.service.SalesRecordService;
import com.aicheck.face.modules.salesRecord.vo.SalesHistoryVO;
import com.aicheck.face.modules.salesRecord.vo.SalesRecordVO;
import com.aicheck.face.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:41 PM 2018/11/21
 */
@RestController
@RequestMapping("/v1/sales-record")
@Slf4j
public class SalesRecordController {
    @Autowired
    private SalesRecordService salesRecordService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SalesDetailService salesDetailService;

    @GetMapping
    public R findAllList(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                         @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize) {
        Page<SalesRecord> page = salesRecordService.findAll(currentPage,pageSize);
        if(page==null){
            return R.error("/v1/sales-record/Get=>page为空");
        }
        List<SalesRecord> salesRecords =  page.getContent();

        return R.ok(convert(salesRecords));
    }

    @GetMapping("/faceId")
    public R findByFaceId(@RequestParam(value = "customerId") String customerId,
                          @RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                          @RequestParam(value = "pageSize",defaultValue = "15") Integer pageSize) {
        log.info("查询购买记录:customerId = {}",customerId);

        Customer customer = customerService.findById(Integer.parseInt(customerId));

        if (customer == null) {
            return R.error(ResultEnum.IS_NOT_EXIST.getCode(),ResultEnum.IS_NOT_EXIST.getMsg());
        }

        Page<SalesRecord> page = salesRecordService.findByCustomerId(customer.getId(),currentPage,pageSize);


        log.info("成功返回数据:{}",page.getContent());
        return R.ok(convert(page.getContent(),customer.getId()));
    }

    @PostMapping("/customerId/{id}")
    public R findByCustomerId(@PathVariable Integer id,
                              @RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                              @RequestParam(value = "pageSize",defaultValue = "1000") Integer pageSize) {

        log.info("Post - 查询购买记录:customerId = {}",id);

        Page<SalesRecord> page = salesRecordService.findByCustomerId(id,currentPage,pageSize);
        if(page==null){
            return R.error("/v1/sales-record//customerId/{id}=>page为空");
        }

//        Map<String,Object> map = new HashMap<>();
//        map.put("salesRecords",);
        log.info("Post - 成功返回数据:{}",page.getContent());
        return R.ok(convert(page.getContent(),id));
    }

    @GetMapping("/{id}")
    public R findById(@PathVariable Integer id) {

        SalesRecord salesRecord = salesRecordService.findById(id);
        if(salesRecord==null){
            return R.error("/v1/sales-record/{id}=>salesRecord为空");
        }
        SalesRecordVO salesRecordVO = new SalesRecordVO();
        BeanUtils.copyProperties(salesRecord,salesRecordVO);
        //查询客户
        Customer customer = customerService.findById(salesRecord.getCustomerId());
        if(customer==null){
            return R.error("/v1/sales-record/{id}=>customer为空");
        }
        CustomerVO customerVO = new CustomerVO();
        BeanUtils.copyProperties(customer,customerVO);
        salesRecordVO.setCustomerVO(customerVO);

        List<SalesDetail> salesDetails = salesDetailService.findBySalesId(salesRecord.getId());
        if(salesDetails==null){
            return R.error("/v1/sales-record/{id}=>salesDetails为空");
        }
        List<SalesDetailVO> salesDetailVOS = new ArrayList<>();
        for (SalesDetail salesDetail : salesDetails) {
            SalesDetailVO salesDetailVO = new SalesDetailVO();
            BeanUtils.copyProperties(salesDetail,salesDetailVO);
            Product product = productService.findById(salesDetail.getProductId());
            salesDetailVO.setProduct(product);
            salesDetailVOS.add(salesDetailVO);
        }
        salesRecordVO.setSalesDetailVOS(salesDetailVOS);
        return R.ok(salesRecordVO);
    }


    @PostMapping
    public R save(@RequestBody @Valid SalesRecordForm salesRecordForm) {

        //添加购买记录
        SalesRecord salesRecord = new SalesRecord();

        salesRecord.setCustomerId(salesRecordForm.getCustomerId());

        salesRecord = salesRecordService.save(salesRecord);

        Integer salesId = salesRecord.getId();

        List<SalesProductForm> salesProductForms = salesRecordForm.getSalesProductFormList();

        if (!CollectionUtils.isEmpty(salesProductForms)) {
            List<SalesDetail> salesDetails = new ArrayList<>();
            for (SalesProductForm salesProductForm : salesProductForms) {
                SalesDetail salesDetail = new SalesDetail();
                BeanUtils.copyProperties(salesProductForm,salesDetail);
                salesDetail.setCreateId(1);
                salesDetail.setSalesId(salesId);
                salesDetails.add(salesDetail);
            }
            //添加购买详细
            salesDetailService.save(salesDetails);
        }

        return R.ok();
    }

    @PutMapping("/{id}")
    public R update(@PathVariable Integer id, @RequestBody @Valid SalesRecordForm salesRecordForm) {
        SalesRecord salesRecord = salesRecordService.findById(id);
        if (salesRecord == null) {
            return R.error(ResultEnum.IS_NOT_EXIST.getCode(),ResultEnum.IS_NOT_EXIST.getMsg());
        }
        UpdateUtils.copyNullProperties(salesRecordForm,salesRecord);
        SalesRecord result = salesRecordService.update(salesRecord);
        return R.ok(result);
    }
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        salesRecordService.delete(id);
        return R.ok();
    }

    private List<SalesRecordVO> convert(List<SalesRecord> salesRecords) {

        List<SalesRecordVO> salesRecordVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(salesRecords)) {
            for (SalesRecord salesRecord : salesRecords) {
                SalesRecordVO salesRecordVO = new SalesRecordVO();
                BeanUtils.copyProperties(salesRecord,salesRecordVO);
                //查询客户
                Customer customer = customerService.findById(salesRecord.getCustomerId());
                if (customer == null) {
                    continue;
                }
                CustomerVO customerVO = new CustomerVO();
                BeanUtils.copyProperties(customer,customerVO);
                salesRecordVO.setCustomerVO(customerVO);

                List<SalesDetail> salesDetails = salesDetailService.findBySalesId(salesRecord.getId());
                if(salesDetails==null){
                    return null;
                }

                List<SalesDetailVO> salesDetailVOS = new ArrayList<>();
                for (SalesDetail salesDetail : salesDetails) {

                    SalesDetailVO salesDetailVO = new SalesDetailVO();
                    BeanUtils.copyProperties(salesDetail,salesDetailVO);
                    Product product = productService.findById(salesDetail.getProductId());
                    if(product==null){
                        return null;
                    }
                    salesDetailVO.setProduct(product);
                    salesDetailVOS.add(salesDetailVO);
                }
                salesRecordVO.setSalesDetailVOS(salesDetailVOS);

                salesRecordVOS.add(salesRecordVO);
            }

        }
        return salesRecordVOS;
    }


    private SalesHistoryVO convert(List<SalesRecord> salesRecords, Integer id) {

        Customer customer = customerService.findById(id);

        if (customer == null) {
            return null;
        }

        SalesHistoryVO salesHistoryVO = new SalesHistoryVO();
        salesHistoryVO.setCustomerId(String.valueOf(id));
        salesHistoryVO.setCustomerName(customer.getName());

        List<SalesRecordVO> salesRecordVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(salesRecords)) {
            for (SalesRecord salesRecord : salesRecords) {
                SalesRecordVO salesRecordVO = new SalesRecordVO();
                BeanUtils.copyProperties(salesRecord,salesRecordVO);
                //查询客户

                CustomerVO customerVO = new CustomerVO();
                BeanUtils.copyProperties(customer,customerVO);
                salesRecordVO.setCustomerVO(customerVO);

                List<SalesDetail> salesDetails = salesDetailService.findBySalesId(salesRecord.getId());
                List<SalesDetailVO> salesDetailVOS = new ArrayList<>();
                for (SalesDetail salesDetail : salesDetails) {

                    SalesDetailVO salesDetailVO = new SalesDetailVO();
                    BeanUtils.copyProperties(salesDetail,salesDetailVO);
                    Product product = productService.findById(salesDetail.getProductId());
                    salesDetailVO.setProduct(product);
                    salesDetailVOS.add(salesDetailVO);
                }
                salesRecordVO.setSalesDetailVOS(salesDetailVOS);

                salesRecordVOS.add(salesRecordVO);
            }
        }

        salesHistoryVO.setSalesRecordVOS(salesRecordVOS);
        return salesHistoryVO;
    }
}
