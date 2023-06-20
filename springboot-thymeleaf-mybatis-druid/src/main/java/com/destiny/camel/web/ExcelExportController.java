package com.destiny.camel.web;


import cn.hutool.core.net.URLDecoder;
import com.destiny.camel.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class ExcelExportController {


    /**
     * 导出 excel xlsx
     * @param response
     * @param request
     * @throws UnsupportedEncodingException
     */
    @GetMapping(value = "excel/xlsx")
    public void export(HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {

        List<User> userList = new ArrayList<>();

        write(response, "生产计划.xlsx", "数据", userList);


    }

    private void write(HttpServletResponse response, String filename, String name, List<User> userList) throws UnsupportedEncodingException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        // sheet 名称
        XSSFSheet sheet = workbook.createSheet(name);

        XSSFRow firstRow = sheet.createRow(0);//第一行表头
        XSSFCell cell1 = firstRow.createCell(0);
        // title
        cell1.setCellValue("生产计划表");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 16));
        // 表头
        XSSFRow seconds = sheet.createRow(1);//第一行表头
        List<String> heads = new ArrayList<>();
        heads.add("序号");
        heads.add("姓名");
        heads.add("年龄");
        heads.add("地址");
        // 创建表头  循环设置表头信息
        for (int i = 0; i < heads.size(); i++) {
            XSSFCell cell = seconds.createCell(i);
            cell.setCellValue(heads.get(i));
        }

        // 填充数据

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            // 创建新的行
            XSSFRow row = sheet.createRow(i * 2 + 2);
            // 序号
            XSSFCell cell = row.createCell(0);
            cell.setCellValue(i + 1);
            // 姓名
            XSSFCell c1 = row.createCell(1);
            c1.setCellValue(user.getUsername());
            // 年龄
            XSSFCell c2 = row.createCell(2);
            c2.setCellValue(user.getAge());
            // 身份证
            XSSFCell c3 = row.createCell(3);
            c3.setCellValue(user.getIdCard());
            // 合并单元格 合并第一列 合并第二列
            sheet.addMergedRegion(new CellRangeAddress(i * 2 + 2, i * 2 + 3, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(i * 2 + 2, i * 2 + 3, 1, 1));

        }

        // 设置 header 和 contentType。写在最后的原因是，避免报错时，响应 contentType 已经被修改了
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        // response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setContentType("application/octet-stream;charset=UTF-8");
        // response.setContentType("application/vnd.ms-excel;charset=UTF-8");

        OutputStream out = null;
        try {
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
