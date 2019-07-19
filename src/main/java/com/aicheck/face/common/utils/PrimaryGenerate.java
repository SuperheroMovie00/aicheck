/*
 * Copyright  2018 Yiyuan Networks 上海义援网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.common.utils;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 4:11 PM 2018/11/25
 */
public class PrimaryGenerate {

    @SuppressWarnings("unused")
	private static final String SERIAL_NUMBER = "XXX"; // 流水号格式
    private static PrimaryGenerate primaryGenerate = null;

    private PrimaryGenerate() {
    }

    /**
     * 取得PrimaryGenerater的单例实现
     *
     * @return
     */
    public static PrimaryGenerate getInstance() {
        if (primaryGenerate == null) {
            synchronized (PrimaryGenerate.class) {
                if (primaryGenerate == null) {
                    primaryGenerate = new PrimaryGenerate();
                }
            }
        }
        return primaryGenerate;
    }

    /**
     * 生成下一个编号
     */

    public synchronized String generaterNextNumber(String sno) {
        String id = null;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String a = null;
        if (sno != null) {
            a = sno.substring(6, 10);
        }
        String b = formatter.format(date).substring(4, 8);
        if (sno == null) {
            id = formatter.format(date) + "001";
        } else if (!a.equals(b)) {
            id = formatter.format(date) + "001";

        }
        else {
//			int count = SERIAL_NUMBER.length();
//			StringBuilder sb = new StringBuilder();
//			for (int i = 0; i < count; i++) {
//				sb.append("0");
//			}
            DecimalFormat df = new DecimalFormat("000");
            id = formatter.format(date) + df.format(1 + Integer.parseInt(sno.substring(10, 13)));
        }
        return id;
    }
}
