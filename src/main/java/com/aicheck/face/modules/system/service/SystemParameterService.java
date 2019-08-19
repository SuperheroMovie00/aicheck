package com.aicheck.face.modules.system.service;

import com.aicheck.face.base.BaseService;
import com.aicheck.face.modules.system.entity.SystemParameter;

public interface SystemParameterService extends BaseService<SystemParameter,Integer> {


    SystemParameter querysystem( String systemtype);

}
