package com.aicheck.face.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {


    public static void main(String[] args) throws ParseException {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.YEAR,-1);

        String dss="2019-07-23T17:50:58.465";

        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy");
        Date dat3 = formatter2.parse(dss);
        String da= sf.format(calendar.getTime());

        System.out.println(dat3);




    }


}
