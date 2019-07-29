package com.aicheck.face.vo;

import java.io.File;

public class file {

    public static boolean deleteDir(String path){
        File file = new File(path);
        //判断是否待删除目录是否存在
        if(!file.exists()){
            System.err.println("The dir are not exists!");
            return false;
        }
        //取得当前目录下所有文件和文件夹
        String[] content = file.list();
        for(String name : content){
            File temp = new File(path, name);
            //判断是否是目录
            if(temp.isDirectory()){
                //递归调用，删除目录里的内容
                deleteDir(temp.getAbsolutePath());
                temp.delete();//删除空目录
            }else{
                //直接删除文件
                if(!temp.delete()){
                    System.err.println("Failed to delete " + name);
                }
            }
        }
        return true;
    }


}
