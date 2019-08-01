package com.aicheck.face.vo;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.aicheck.face.vo.file.deleteDir;

public class Test  extends Date{



    private static final long serialVersionUID = 1L;
    private void test(){
        System.out.println(super.getClass().getName());
    }



    public static void main(String[] args) {

        new Test().test();


        /**
         * 清理文件夹中的文件
         */

       /* String path = "D:\\123";
        deleteDir(path);
*/

        /*Integer[] arr=new Integer[3];
        arr[0]=5;*/



/*      Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.YEAR,-1);

        String dss="2019-7-24 18:38:40";

        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy");
        Date dat3 = sf.parse(dss);


        long newtime=new Date().getTime();
        long oldtime=dat3.getTime();
        long diff = newtime - oldtime;
        long minute = diff / 1000L / 60L;

        System.out.println(minute);
*/



    }


}
