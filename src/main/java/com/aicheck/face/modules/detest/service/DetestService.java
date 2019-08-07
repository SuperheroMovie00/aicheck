package com.aicheck.face.modules.detest.service;

import com.aicheck.face.base.BaseService;

import com.aicheck.face.modules.detest.entity.Detest;
import com.aicheck.face.vo.R;

public interface DetestService extends BaseService<Detest,Integer> {


    /**
     * 新增
     * @param detest
     * @return
     */
    R add(Detest detest);

    /**
     * 删除（根据id）
     * @param id
     * @return
     */
    R deleteforid(Integer id);

    /**
     * 修改
     * @param detest
     * @return
     */
    R updatetest(Detest detest);





}
