/*
 * Copyright  2018 LianChao Networks 上海联朝网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.datasources;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author liaojin
 *
 */
@WebServlet(urlPatterns = "/druid/*",
initParams={
//		@WebInitParam(name="allow",value=""),// IP白名单 (没有配置或者为空，则允许所有访问)
//        @WebInitParam(name="deny",value="192.168.16.111"),// IP黑名单 (存在共同时，deny优先于allow)
        @WebInitParam(name="loginUsername",value="admin"),// 用户名
        @WebInitParam(name="loginPassword",value="yyFace-admin"),// 密码
        @WebInitParam(name="resetEnable",value="false")// 禁用HTML页面上的“Reset All”功能
})
public class DruidStatViewServlet extends StatViewServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

}
