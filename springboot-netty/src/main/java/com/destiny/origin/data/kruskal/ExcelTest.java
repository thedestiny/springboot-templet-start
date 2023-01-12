package com.destiny.origin.data.kruskal;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.cell.CellEditor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.util.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

/**
 * @Description
 * @Author destiny
 * @Date 2022-11-07 5:44 PM
 */
public class ExcelTest {

    public static void main(String[] args) throws IOException {

        File file = new File("/Users/admin/Desktop/1234.xlsx");
        FileInputStream input = new FileInputStream(file);
        // 1.获取上传文件输入流
        InputStream inputStream = null;

        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));

        try {
            inputStream = multipartFile.getInputStream();
        } catch (Exception e) {
        }

        // 2.应用HUtool ExcelUtil获取ExcelReader指定输入流和sheet
        ExcelReader excelReader = ExcelUtil.getReader(inputStream,0);
        excelReader.setCellEditor(new CellEditor() {
            @Override
            public Object edit(Cell cell, Object value) {
                String stringCellValue = cell.getStringCellValue();
                System.out.println(stringCellValue);
                return value;
            }
        });
        // 可以加上表头验证
        // 3.读取第二行到最后一行数据
        List<List<Object>> read = excelReader.read(0, excelReader.getRowCount());
        for (List<Object> objects : read) {
            for (Object object : objects) {
                System.out.println(object);
            }

        }


    }
}
