/*
 * Copyright  2018 Yiyuan Networks 上海义援网络科技有限公司. All rights reserved.
 */
package com.aicheck.face.common.utils;


import com.aicheck.face.common.exception.FaceException;
import com.aicheck.face.modules.customer.entity.Customer;
import com.aicheck.face.modules.product.entity.Product;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelUtil {


    public static void exportExcel(HttpServletResponse response, String excelName, String sheetName, List<String> headers, List<List<String>> contentObj,Integer type) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);

        String fileName = excelName + ".xlsx";
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        //headers表示excel表中第一行的表头
        XSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for (int i = 0; i < headers.size(); i++) {
            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers.get(i));
            cell.setCellValue(text);
        }

        //在表中存放查询到的数据放入对应的列
        for (List<String> obj : contentObj) {
            XSSFRow row1 = sheet.createRow(rowNum);
            for (int i = 0; i < headers.size(); i++) {

                row1.createCell(i).setCellValue(obj.get(i));
            }
            rowNum++;
        }

        if (type == null) {
            //浏览器下载excel
            buildExcelDocument(fileName, workbook, response);
        } else {
            //生成excel文件
            buildExcelFile(fileName, workbook,PropertiesUtils.getInstance().getProperties("customer"));
        }



    }

    public static void exportExcel(HttpServletResponse response, String excelName, String sheetName, List<String> headers, List<List<Object>> contentObj, String url) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);

        String fileName = excelName + ".xlsx";
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        //headers表示excel表中第一行的表头
        XSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for (int i = 0; i < headers.size(); i++) {
            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers.get(i));
            cell.setCellValue(text);
        }

        //在表中存放查询到的数据放入对应的列
        for (List<Object> obj : contentObj) {
            XSSFRow row1 = sheet.createRow(rowNum);
            for (int i = 0; i < headers.size(); i++) {
                row1.createCell(i).setCellValue(String.valueOf(obj.get(i)));
            }
            rowNum++;
        }
        //生成excel文件
        buildExcelFile(fileName, workbook, url);
    }

    public static File exportExcelNew(HttpServletResponse response, String excelName, String sheetName, List<String> headers, List<List<Object>> contentObj, String url) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);

        String fileName = excelName + ".xlsx";
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        //headers表示excel表中第一行的表头
        XSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for (int i = 0; i < headers.size(); i++) {
            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers.get(i));
            cell.setCellValue(text);
        }

        //在表中存放查询到的数据放入对应的列
        for (List<Object> obj : contentObj) {
            XSSFRow row1 = sheet.createRow(rowNum);
            for (int i = 0; i < headers.size(); i++) {
                row1.createCell(i).setCellValue(String.valueOf(obj.get(i)));
            }
            rowNum++;
        }
        //生成excel文件
        return buildExcelFile2(fileName, workbook, url);
    }

    @SuppressWarnings({ "resource", "unused" })
	public static List<Customer> importCustomerExcel(String fileName, String filePath) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @SuppressWarnings("unused")
		boolean notNull = false;
        List<Customer> customerList = new ArrayList<>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new FaceException("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
//      InputStream is = file.getInputStream();
        InputStream is = new FileInputStream(filePath);
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null) {
            notNull = true;
        }
        Customer customer;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }

            customer = new Customer();

//            Integer id = Integer.parseInt(row.getCell(0).getStringCellValue());
//
//            if (id == null) {
//                throw new FaceException("导入失败(第" + (r + 1) + "行,编号未填写)");
//            }
            row.getCell(0).setCellType(CellType.STRING);
            String name = row.getCell(0).getStringCellValue();

            if (StringUtils.isEmpty(name)) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,名称未填写)");
            }
            row.getCell(1).setCellType(CellType.STRING);
            Integer age = Integer.parseInt(row.getCell(1).getStringCellValue());

            if (age == null) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,年龄未填写)");
            }
            row.getCell(2).setCellType(CellType.STRING);
            String gender = row.getCell(2).getStringCellValue();
            if (StringUtils.isEmpty(gender)) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,性别未填写)");
            }
            row.getCell(3).setCellType(CellType.STRING);
            String mobile = row.getCell(3).getStringCellValue();
            if (StringUtils.isEmpty(mobile)) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,手机未填写)");
            }
            row.getCell(4).setCellType(CellType.STRING);
            String email = row.getCell(4).getStringCellValue();
            if (StringUtils.isEmpty(email)) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,邮箱未填写)");
            }
            row.getCell(5).setCellType(CellType.STRING);
            String address = row.getCell(5).getStringCellValue();
            if (StringUtils.isEmpty(address)) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,地址未填写)");
            }
            row.getCell(6).setCellType(CellType.STRING);
            Date createTime = simpleDateFormat.parse(row.getCell(6).getStringCellValue());
            if (createTime == null) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,日期未填写)");
            }
            row.getCell(7).setCellType(CellType.STRING);
            Integer creatorId = Integer.parseInt(row.getCell(7).getStringCellValue());
            if (creatorId == null) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,创建编号未填写)");
            }
            row.getCell(8).setCellType(CellType.STRING);
            String userModel = row.getCell(8).getStringCellValue();
            if (StringUtils.isEmpty(userModel)) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,用户JSON未填写)");
            }
            row.getCell(9).setCellType(CellType.STRING);
            String urlStr = row.getCell(9).getStringCellValue();

            if (StringUtils.isEmpty(urlStr)) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,头像未填写)");
            }

            row.getCell(10).setCellType(CellType.STRING);
            String faceId = row.getCell(10).getStringCellValue();
            if (StringUtils.isEmpty(faceId)) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,人脸图片未填写)");
            }

//            customer.setId(id);
            customer.setName(name);
            customer.setAge(age);
            customer.setGender(gender);
            customer.setMobile(mobile);
            customer.setEmail(email);
            customer.setAddress(address);
            customer.setCreateTime(createTime);
            customer.setCreateId(creatorId);
            customer.setUserModelValue(userModel);
            String localhost = "192.168.1.99";

            try {
                localhost = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            String picName = urlStr.substring(urlStr.lastIndexOf("/") + 1,urlStr.length());
            String url = "http://" + localhost + ":8090/yy-face/images/" + picName;
            customer.setImgUrl(url);
            customer.setFaceId(faceId);

            customerList.add(customer);
        }

        return customerList;
    }

    @SuppressWarnings({ "resource", "unused" })
	public static List<Product> importProductExcel(String fileName, MultipartFile file) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @SuppressWarnings("unused")
		boolean notNull = false;
        List<Product> productList = new ArrayList<>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new FaceException("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null) {
            notNull = true;
        }
        Product product;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }

            product = new Product();
//            row.getCell(0).setCellType(CellType.STRING);
//            Integer id = Integer.parseInt(row.getCell(0).getStringCellValue());
//
//            if (id == null) {
//                throw new FaceException("导入失败(第" + (r + 1) + "行,编号未填写)");
//            }
            row.getCell(0).setCellType(CellType.STRING);
            String code = row.getCell(0).getStringCellValue();

            if (StringUtils.isEmpty(code)) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,商品编码未填写)");
            }
            row.getCell(1).setCellType(CellType.STRING);
            String name = row.getCell(1).getStringCellValue();

            if (StringUtils.isEmpty(name)) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,商品名称未填写)");
            }
            row.getCell(2).setCellType(CellType.STRING);
            String type = row.getCell(2).getStringCellValue();
            if (StringUtils.isEmpty(type)) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,类型未填写)");
            }
            row.getCell(3).setCellType(CellType.STRING);
            Double price = Double.valueOf(row.getCell(3).getStringCellValue());
            if (price == null) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,价格未填写)");
            }
            row.getCell(4).setCellType(CellType.STRING);
            String manufacturers = row.getCell(4).getStringCellValue();
            if (StringUtils.isEmpty(manufacturers)) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,制造商未填写)");
            }
            row.getCell(5).setCellType(CellType.STRING);
            String dateOfProduction = row.getCell(5).getStringCellValue();
            if (StringUtils.isEmpty(dateOfProduction)) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,生产日期未填写)");
            }
            row.getCell(6).setCellType(CellType.STRING);
            String description = row.getCell(6).getStringCellValue();
//            if (description == null) {
//                throw new FaceException("导入失败(第" + (r + 1) + "行,描述未填写)");
//            }
            row.getCell(7).setCellType(CellType.STRING);
            String remark = row.getCell(7).getStringCellValue();
//            if (StringUtils.isNotEmpty(remark)) {
//                throw new FaceException("导入失败(第" + (r + 1) + "行,备注未填写)");
//            }
            row.getCell(8).setCellType(CellType.STRING);
            Date createTime = simpleDateFormat.parse(row.getCell(8).getStringCellValue());
            if (createTime == null) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,创建时间未填写)");
            }
            row.getCell(9).setCellType(CellType.STRING);
            Integer createId = Integer.parseInt(row.getCell(9).getStringCellValue());
            if (createId == null) {
                throw new FaceException("导入失败(第" + (r + 1) + "行,创建人未填写)");
            }
            row.getCell(10).setCellType(CellType.STRING);
            String imgUrl = row.getCell(10).getStringCellValue();
//            if (StringUtils.isEmpty(imgUrl)) {
//                throw new FaceException("导入失败(第" + (r + 1) + "行,商品图片未填写)");
//            }

            product.setCode(code);
            product.setName(name);
            product.setType(type);
            product.setPrice(price);
            product.setManufacturers(manufacturers);
            product.setDateOfProduction(dateOfProduction);
            product.setDescription(description);
            product.setRemark(remark);
            product.setCreateTime(createTime);
            product.setCreateId(createId);
            product.setImgUrl(imgUrl);

            productList.add(product);

        }

        return productList;
    }


    //生成excel文件
    protected static void buildExcelFile(String filename, XSSFWorkbook workbook, String url) throws Exception {
        File targetFile = new File(url);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(url + filename);
        workbook.write(fos);
        fos.flush();
        fos.close();
    }

    //生成excel文件
    protected static File buildExcelFile2(String filename, XSSFWorkbook workbook, String url) throws Exception {
        File targetFile = new File(url);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(url + filename);
        workbook.write(fos);
        fos.flush();
        fos.close();
        return targetFile;
    }

    //浏览器下载excel
    protected static void buildExcelDocument(String filename, XSSFWorkbook workbook, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    public static List<List<String>> importData(MultipartFile file) {
        Workbook wb = null;
        List<List<String>> list = new ArrayList<>();
        try {
            if (isExcel2007(file.getOriginalFilename())) {
                wb = new XSSFWorkbook(file.getInputStream());
            } else {
                wb = new HSSFWorkbook(file.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }

        Sheet sheet = wb.getSheetAt(0);//获取第一张表
        System.out.println(sheet.getLastRowNum());
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);//获取索引为i的行，以0开始
            List<String> strList = new ArrayList<>();
            for (int j = 0; j < row.getLastCellNum(); j++) {
                row.getCell(j).setCellType(CellType.STRING);
                ;
                String str = row.getCell(j).getStringCellValue();
                strList.add(str);
            }
            list.add(strList);
        }
        try {
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
