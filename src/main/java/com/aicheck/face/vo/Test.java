package com.aicheck.face.vo;

import com.aicheck.face.modules.pathseting.entity.pathseting;
import com.aicheck.face.modules.pathseting.service.pathsetingService;
import com.aicheck.face.modules.visitorsRecord.entity.Videostatistic;
import com.aicheck.face.modules.visitorsRecord.service.VideostatisticService;
import com.netsdk.demo.VideoStatistic;

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
import java.util.Map;
import java.util.regex.Pattern;

import static com.aicheck.face.vo.file.deleteDir;
import static com.aicheck.face.vo.file.copyDir;
import static org.springframework.boot.autoconfigure.condition.ConditionOutcome.match;

public class Test {

    @Autowired
    private pathsetingService pathsetingService;
    
    @Autowired 
    private static VideostatisticService videostatisticService;




    public static void main(String[] args) throws IOException {

    	SimpleDateFormat format=new SimpleDateFormat("HH");
		
		String createtimehour=format.format(new Date());
    	
    	System.out.println(createtimehour);

      /*  SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        SystemParameter.out.println("现在时间："+simdf.format(cal.getTime()));


        cal.set(cal.DAY_OF_WEEK, cal.MONDAY);
        String weekhand = simdf.format(cal.getTime());
        int begin=cal.get(Calendar.DATE);
        SystemParameter.out.println("当前时间所在周周一日期："+weekhand+"hahahah"+begin);

        cal.set(Calendar.DATE, cal.get(cal.DATE) + 6);
        String weeklast = simdf.format(cal.getTime());
        int end=cal.get(Calendar.DATE);
        //SystemParameter.out.println("当前时间所在周周日日期："+weeklast+"hahahah"+e);

        for(int r=begin;r<=end;r++) {
       	 SystemParameter.out.println("hahah"+r);
        }
*/

    	

        /*String regex = "^(?:[1-9][0-9]?|1[01][0-9]|120)$";
        SystemParameter.out.println(Pattern.matches(regex, "120"));*/
//        Date date=new Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
//        Date d= calendar.getTime();
//
//        SystemParameter.out.println("当前时间："+date+"后退一小时:"+d);

    	/*VideoStatistic demo = new VideoStatistic();
    	demo.InitTest();
    
    	List<String> day=new ArrayList<String>();
    	List<String> count=new ArrayList<String>();
    	Date date1=new Date();
    	Calendar calendar1 = Calendar.getInstance();
    	calendar1.setTime(date1);
        int	Number=calendar1.get(Calendar.HOUR_OF_DAY);
    		
    	for(int r=1;r<=Number;r++) {
    		Date date=new Date();
        	Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - r);
            calendar2.set(Calendar.HOUR_OF_DAY, calendar2.get(Calendar.HOUR_OF_DAY) - r+1);
            Map<String,Integer> cMap= VideoStatistic.startFindNumberStatrewrite(calendar.getTime(),calendar2.getTime());
            Integer innum=cMap.get("IN");
            count.add(String.valueOf(innum));
    		day.add(String.valueOf(r));
    	}
    	
    	SystemParameter.out.println(day);
    	SystemParameter.out.println(count);
*/
        /*List<String> day=new ArrayList<String>();
        for(int r=1;r<=12;r++) {
            day.add(String.valueOf(r));
        }

        SystemParameter.out.println(day);*/


        /*Detest t=new Detest();
        t.add();
*/




        /*  List<String> list =new ArrayList<>();

        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        for (String c:list) {
            SystemParameter.out.println(c);

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

        SystemParameter.out.println(minute);
*/



    }


    public  void add(){
        URL path= this.getClass().getClassLoader().getResource("");
        System.out.println(path);
    }










}
