package com.aicheck.face.vo;

import com.aicheck.face.modules.pathseting.entity.pathseting;
import com.aicheck.face.modules.pathseting.service.pathsetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static com.aicheck.face.vo.file.deleteDir;
import static com.aicheck.face.vo.file.copyDir;
import static org.springframework.boot.autoconfigure.condition.ConditionOutcome.match;

public class Test {

    @Autowired
    private pathsetingService pathsetingService;




    public static void main(String[] args) throws IOException {


        String regex = "^(?:[1-9][0-9]?|1[01][0-9]|120)$";
        System.out.println(Pattern.matches(regex, "120"));




        /*Detest t=new Detest();
        t.add();
*/




        /*  List<String> list =new ArrayList<>();

        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        for (String c:list) {
            System.out.println(c);

        }
*/

        //copyDir("D:\\111","D:\\222333");


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


    public  void add(){
        URL path= this.getClass().getClassLoader().getResource("");
        System.out.println(path);
    }










}
