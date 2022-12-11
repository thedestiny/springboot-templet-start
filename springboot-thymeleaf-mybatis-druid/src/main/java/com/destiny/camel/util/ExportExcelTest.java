package com.destiny.camel.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import cn.hutool.poi.excel.cell.CellUtil;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class ExportExcelTest {




    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        // List<Map<String, Object>> maps = new ArrayList<>();
	    String property = System.getProperty("user.dir");
	    String s = RandomUtil.randomNumbers(30);
	
	    StringJoiner path = new StringJoiner("\\").add(property).add(s + ".xlsx");
	    System.out.println(path.toString());
	
	    ExcelWriter writer = ExcelUtil.getWriter(path.toString());
        writer.setColumnWidth(-1, 20);

        layout(writer, new Object(), new ArrayList<>());
       //  writer.write(maps, true);
        // 关闭writer，释放内存
        writer.close();
        Long entTime = System.currentTimeMillis();
        System.out.println(entTime-start);

    }
    

    public static void layout(ExcelWriter writer, Object object, List<List<T>> lists) {

        //数字从0开始算       前面两个数字是第几行到第几行合并    后面两个数字是第几列到第几列合并
        //第一行
	    
	    // 页面布局设置纸张方向
	    PrintSetup printSetup = writer.getSheet().getPrintSetup();
	    printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
	    printSetup.setLandscape(true); // 打印方向，true：横向，false：纵向(默认)
	    
	    StyleSet style = writer.getStyleSet();
	
	    Workbook workbook = writer.getWorkbook();
	    // 标题字体1
	    Font font = workbook.createFont();
	    font.setFontHeightInPoints((short) 18);
	    font.setBold(Boolean.TRUE);
	    font.setFontName("华文宋体");
	    // 正文字体2
	    Font font1 = workbook.createFont();
	    font1.setFontHeightInPoints((short) 9);
	    font.setFontName("宋体");
	    // font1.setBold(Boolean.TRUE);
	
	    // 表头样式
	    CellStyle headStyle = workbook.createCellStyle();
	    headStyle.setWrapText(Boolean.TRUE);
	    headStyle.setFont(font);
	    headStyle.setBorderTop(BorderStyle.THIN);
	    headStyle.setBorderRight(BorderStyle.THIN);
	    headStyle.setBorderLeft(BorderStyle.THIN);
	    headStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.index);
	    headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    headStyle.setAlignment(HorizontalAlignment.CENTER);
	
	    // 正文样式白色背景
	    CellStyle normalStyleW = workbook.createCellStyle();
	    normalStyleW.setWrapText(Boolean.TRUE);
	    normalStyleW.setFont(font1);
	    normalStyleW.setBorderTop(BorderStyle.THIN);
	    normalStyleW.setBorderBottom(BorderStyle.THIN);
	    normalStyleW.setBorderLeft(BorderStyle.THIN);
	    normalStyleW.setBorderRight(BorderStyle.THIN);
	    normalStyleW.setVerticalAlignment(VerticalAlignment.CENTER);
	    normalStyleW.setAlignment(HorizontalAlignment.CENTER);
	    normalStyleW.setFillForegroundColor(IndexedColors.WHITE.index);
	    normalStyleW.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	
	    // 正文样式灰色背景
	    CellStyle normalStyleB = workbook.createCellStyle();
	    normalStyleB.setWrapText(Boolean.TRUE);
	    normalStyleB.setFont(font1);
	    normalStyleB.setVerticalAlignment(VerticalAlignment.CENTER);
	    normalStyleB.setAlignment(HorizontalAlignment.CENTER);
	    normalStyleB.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
	    normalStyleB.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    normalStyleB.setBorderTop(BorderStyle.THIN);
	    normalStyleB.setBorderBottom(BorderStyle.THIN);
	    normalStyleB.setBorderLeft(BorderStyle.THIN);
	    normalStyleB.setBorderRight(BorderStyle.THIN);
	
	    // 正文样式灰色背景左侧对齐
	    CellStyle normalStyleLeft = workbook.createCellStyle();
	    normalStyleLeft.setWrapText(Boolean.TRUE);
	    normalStyleLeft.setFont(font1);
	    normalStyleLeft.setVerticalAlignment(VerticalAlignment.CENTER);
	    normalStyleLeft.setAlignment(HorizontalAlignment.LEFT);
	    normalStyleLeft.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
	    normalStyleLeft.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    normalStyleLeft.setBorderTop(BorderStyle.THIN);
	    normalStyleLeft.setBorderBottom(BorderStyle.THIN);
	    normalStyleLeft.setBorderLeft(BorderStyle.THIN);
	    normalStyleLeft.setBorderRight(BorderStyle.THIN);
	
	    // 设置单元格宽度和高度
	    writer.setDefaultRowHeight(24);
	    writer.setColumnWidth(0,11);
	    writer.setColumnWidth(1,9);
	    writer.setColumnWidth(2,11);
	    writer.setColumnWidth(3,10);
	    writer.setColumnWidth(4,11);
	    writer.setColumnWidth(5,10);
	    writer.setColumnWidth(6,11);
	    writer.setColumnWidth(7,10);
	    
	    
	    
	    writeCell(writer, 0, 0, false, null, "附录3");
	
	
	    Cell ccc = writeCell(writer, 1, 1, false, null, "采集信息");
	    
	    // ccc.();
	
	    //第二行
        writer.merge(1, 1, 0, 7, "信息采集表",true);


        //第三行
	    Cell cell = writeCell(writer, 0, 2, false, style, "省份：");
	    
	    
	    writeCell(writer, 1, 2, false,style, "222");
        writeCell(writer, 2, 2, false, style, "年度：");
        writeCell(writer, 3, 2, false, style, "2020");
        writeCell(writer, 4, 2, false, style, "填表联系人：");
        writeCell(writer, 3, 2, false, style, "xxxx");
        writer.merge(2, 2, 5, 7, "xxxx", false);



        //第4行
        writer.merge(3, 3, 0, 7, "一、xxxx",true);
        //第5行
        writer.merge(4, 4, 0, 2, "xxxx", true);
        writer.merge(4, 4, 3, 4, "xxxx", true);
        writer.merge(4, 4, 5, 7, "xxxx", true);

        //第6行
        writer.merge(5, 5, 0, 2, "xxxx", false);
        writer.merge(5, 5, 3, 4, "xxxx", false);
        writer.merge(5, 5, 5, 7, "xxxx", false);


        //第7行
        writer.merge(6, 6, 0, 1, "xxxx", true);
        writer.merge(6, 6, 2, 4, "xxxx", true);
        writer.merge(6, 6, 5, 7, "xxxx", true);


        //第8行
        writer.merge(7, 7, 0, 1, "xxxx", false);
        writer.merge(7, 7, 2, 4, "xxxx", false);
        writer.merge(7, 7, 5, 7, "xxxx", false);

        //第9行
        writer.merge(8, 8, 0, 7, "二、xxxx",true);

        //第10行
        writer.merge(9, 9, 0, 1, "xxxx", true);
        writer.merge(9, 9, 2, 3, "xxxx", true);
        writer.merge(9, 9, 4, 5, "xxxx", true);
        writer.merge(9, 9, 6, 7, "xxxx", true);

        //第11行
        writer.merge(10, 10, 0, 1, "1", false);
        writer.merge(10, 10, 2, 3, "2", false);
        writer.merge(10, 10, 4, 5, "3", false);
        writer.merge(10, 10, 6, 7, "4", false);
        

        //第12行
        writer.merge(11, 11, 0, 7, "三、xxxx",true);

        int i = 12, j=5, k=4;
        for(int m = 1; m <= j; m++){
            writer.merge(i, i, 0, 7, "（" + m + "）xxxx"+m,true);
            i++;
            writer.merge(i, i, 0, 1, "xxxx", true);
            writer.merge(i, i, 2, 3, "xxxx", true);
            writer.merge(i, i, 4, 5, "xxxx", true);
            writer.merge(i, i, 6, 7, "xxxx", true);
            i++;

            writer.merge(i, i, 0, 1, "a"+m, false);
            writer.merge(i, i, 2, 3, "a"+m, false);
            writer.merge(i, i, 4, 5, "a"+m, false);
            writer.merge(i, i, 6, 7, "a"+m, false);
            i++;
            for(int n = 1 ; n <= k; n++){
                writer.merge(i, i, 0, 7, n + "、xxxx（"+n+"）",true);
                i++;
                writeCell(writer, 0, i, true, style, "xxxx：");
                writeCell(writer, 1, i, false, style, "tym");
                writeCell(writer, 2, i, true, style, "xxxx：");
                writeCell(writer, 3, i, false, style, "spmc");
                writeCell(writer, 4, i, true, style, "xxxx/xxxx：");
                writeCell(writer, 5, i, false, style, "gcjk");
                writeCell(writer, 6, i, true, style, "xxxx：");
                writeCell(writer, 7, i, false, style, "cpbm");
                i++;

                writeCell(writer, 0, i, true, style, "xxxx：");
                writeCell(writer, 1, i, false, style, "sjje");
                writeCell(writer, 2, i, true, style, "xxxx：");
                writeCell(writer, 3, i, false, style, "spmc");
                writeCell(writer, 4, i, true, style, "xxxx：");
                writeCell(writer, 5, i, false, style, "gcjk");
                writeCell(writer, 6, i, true, style, "xxxx：");
                writeCell(writer, 7, i, false, style, "cpbm");
                i++;

                writeCell(writer, 0, i, true, style, "xxxx：");
                writeCell(writer, 1, i, false, style, "sjje");
                writeCell(writer, 2, i, true, style, "xxxx：");
                writeCell(writer, 3, i, false, style, "spmc");
                writeCell(writer, 4, i, true, style, "xxxx：");
                writeCell(writer, 5, i, false, style, "gcjk");
                writeCell(writer, 6, i, true, style, "xxxx：");
                writeCell(writer, 7, i, false, style, "cpbm");

                i++;

                writeCell(writer, 0, i, true, style, "xxxx：");
                writeCell(writer, 1, i, false, style, "sjje");
                writeCell(writer, 2, i, true, style, "xxxx：");
                writeCell(writer, 3, i, false, style, "spmc");
                writeCell(writer, 4, i, true, style, "xxxx：");
                writeCell(writer, 5, i, false, style, "gcjk");
                writeCell(writer, 6, i, true, style, "xxxx：");
                writeCell(writer, 7, i, false, style, "cpbm");

                i++;

            }

        }

    }

    /**
     * 写入但与那个
     * @param writer
     * @param x     X坐标，从0计数，即列号
     * @param y     Y坐标，从0计数，即行号
     * @param value 值
     * @param isWriteStyle 是否写入样式
     * @param style  样式内容
     */
    public static Cell writeCell(ExcelWriter writer, int x, int y, boolean isWriteStyle, StyleSet style, Object value) {

        //数字从0开始算       前面两个数字是第几行到第几行合并    后面两个数字是第几列到第几列合并
        //第一行
	
	    Cell cell = writer.getOrCreateCell(x, y);
	    // writer.writeCellValue(x, y, value);
	    if(isWriteStyle){
		    CellUtil.setCellValue(cell, value, writer.getStyleSet(), false);
		    // writer.setStyle(style, x, y);
	    } else {
		    CellUtil.setCellValue(cell, value, writer.getStyleSet(), false);
	    }
	    return cell;

    }
	
	
	/**
	 *
	 * 重写 merge 方法
	 * */
	private static Cell merge(ExcelWriter writer, int firstRow, int lastRow, int firstColumn, int lastColumn, Object content, boolean isSetHeaderStyle) {
		// Assert.isFalse(this.isClosed, "ExcelWriter has been closed!", new Object[0]);
		CellStyle style = null;
		StyleSet styleSet = writer.getStyleSet();
		if (null != styleSet) {
			style = isSetHeaderStyle && null != styleSet.getHeadCellStyle() ? styleSet.getHeadCellStyle() : styleSet.getCellStyle();
		}
		
		CellUtil.mergingCells(writer.getSheet(), firstRow, lastRow, firstColumn, lastColumn, style);
		
		Cell cell = writer.getOrCreateCell(firstColumn, firstRow);
		CellUtil.setCellValue(cell, content, styleSet, isSetHeaderStyle);
		return cell;
	}
 
}
