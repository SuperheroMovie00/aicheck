/*
 * Copyright  2018 LianChao Networks 上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.base;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 5:51 PM 2018/11/21
 */
public interface BaseService<T, ID extends Serializable> {
    T save(T t);

    
    List<T> save(List<T> t);

    void delete(ID id);

    T update(T t);

    List<T> find(T t);

    List<T> findAll();

    T findById(ID id);
}
