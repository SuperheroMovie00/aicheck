package com.aicheck.face.modules.pathseting.service;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.pathseting.entity.pathseting;


public interface pathsetingService extends BaseService<pathseting,Integer> {


    pathseting  findpathfortype(String type);

}
