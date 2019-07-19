/*
 * Copyright  哈哈哈哈哈哈哈哈. All rights reserved.
 */
package com.aicheck.face.common.utils;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @Author: liaojin
 * @Description:
 * @Date: Created in 7:01 PM 2019/4/11
 */
public class ZipUtils {

    public static void downLoadZip(HttpServletResponse response,String path) throws Exception {
        OutputStream out = response.getOutputStream();
        byte[] data = createZip(path);//服务器存储地址
        response.reset();
        response.setHeader("Content-Disposition","attachment;fileName=user.zip");
        response.addHeader("Content-Length", ""+data.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        IOUtils.write(data, out);
        out.flush();
        out.close();
    }

    @SuppressWarnings("deprecation")
	public static byte[] createZip(String srcSource) throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        //将目标文件打包成zip导出
        File file = new File(srcSource);
        packageZip(zip,file,"");
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    @SuppressWarnings("deprecation")
	private static void packageZip(ZipOutputStream zip, File file, String dir) throws Exception {
        //如果当前的是文件夹，则进行进一步处理
        if (file.isDirectory()) {
            //得到文件列表信息
            File[] files = file.listFiles();
            //将文件夹添加到下一级打包目录
            zip.putNextEntry(new ZipEntry(dir + "/"));
            dir = dir.length() == 0 ? "" : dir + "/";
            //循环将文件夹中的文件打包
            for (int i = 0; i < files.length; i++) {
                packageZip(zip, files[i], dir + files[i].getName());         //递归处理
            }
        } else {   //当前的是文件，打包处理
            //文件输入流
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(dir);
            zip.putNextEntry(entry);
            zip.write(org.apache.commons.io.FileUtils.readFileToByteArray(file));
            IOUtils.closeQuietly(bis);
            zip.flush();
            zip.closeEntry();
        }
    }


    /**
     * 解压方法
     *
     * @param parent 解压到目录下
     * @param zip    zip文件
     */
    public static void decompressed(String parent, String zip) {
        try {
            decompressed(new File(parent), new ZipFile(zip));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压方法
     *
     * @param parent 解压到目录下
     * @param zip    zip文件
     */
    public static void decompressed(File parent, File zip) {
        try {
            decompressed(parent, new ZipFile(zip));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压方法
     *
     * @param parent 解压到目录下
     * @param zip    zip文件
     */
    public static void decompressed(String parent, File zip) {
        try {
            decompressed(new File(parent), new ZipFile(zip));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压方法
     *
     * @param parent  解压到目录下
     * @param zipFile zip文件
     */
    public static void decompressed(File parent, ZipFile zipFile) {
        mkdirs(parent);
        try {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                File file = new File(parent, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    mkdirs(file);
                } else {
                    createFile(file);
                    InputStream is = zipFile.getInputStream(zipEntry);
                    @SuppressWarnings("resource")
					FileOutputStream fos = new FileOutputStream(file);
                    byte[] b = new byte[1024];
                    int len;
                    while ((len = is.read(b)) != -1) {
                        fos.write(b, 0, len);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件顺便创建父目录
     *
     * @param path 文件字符串路径例如d:/fulld/why/ass/a/asd
     */
    public static void createFile(String path) {
        createFile(new File(path));
    }

    /**
     * 创建文件顺便创建父目录
     *
     * @param file file类
     */
    public static void createFile(File file) {
        if (file.exists() && file.isFile()) {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        File parentFile = file.getParentFile();
        if (parentFile.exists()) {
            if (parentFile.isFile()) {
                parentFile.delete();
                parentFile.mkdirs();
            }
        } else {
            parentFile.mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件夹顺便创建父目录
     *
     * @param path 文件夹的路径字符串例如d:/fulld/why/ass/a/asd/
     * @return 如果本来就存在或者创建成功，那么就返回true
     */
    public static void mkdirs(String path) {
        mkdirs(new File(path));
    }

    /**
     * 创建文件夹顺便创建父目录
     *
     * @param file file类
     */
    public static void mkdirs(File file) {
        if (file.exists() && file.isDirectory()) {
            return;
        }
        if (file.exists()) {//肯定是文件了，文件删掉
            file.delete();
            file.mkdirs();
        } else {
            file.mkdirs();
        }
    }
}
