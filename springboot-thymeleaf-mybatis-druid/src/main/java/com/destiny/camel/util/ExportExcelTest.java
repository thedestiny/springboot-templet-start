package com.destiny.camel.util;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class ExportExcelTest {




    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        // List<Map<String, Object>> maps = new ArrayList<>();
	    String property = System.getProperty("user.dir");
	    StringJoiner path = new StringJoiner("\\").add(property).add("123.xlsx");
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

        writeCell(writer, 0, 0, false, null, "附录3");
        //第二行
        writer.merge(1, 1, 0, 7, "信息采集表",true);


        //第三行
        writeCell(writer, 0, 2, true, writer.getHeadCellStyle(), "省份：");
        writeCell(writer, 1, 2, false, writer.getHeadCellStyle(), "222");
        writeCell(writer, 2, 2, true, writer.getHeadCellStyle(), "年度：");
        writeCell(writer, 3, 2, false, writer.getHeadCellStyle(), "2020");
        writeCell(writer, 4, 2, true, writer.getHeadCellStyle(), "填表联系人：");
        writeCell(writer, 3, 2, false, writer.getHeadCellStyle(), "xxxx");
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
                writeCell(writer, 0, i, true, writer.getHeadCellStyle(), "xxxx：");
                writeCell(writer, 1, i, false, writer.getHeadCellStyle(), "tym");
                writeCell(writer, 2, i, true, writer.getHeadCellStyle(), "xxxx：");
                writeCell(writer, 3, i, false, writer.getHeadCellStyle(), "spmc");
                writeCell(writer, 4, i, true, writer.getHeadCellStyle(), "xxxx/xxxx：");
                writeCell(writer, 5, i, false, writer.getHeadCellStyle(), "gcjk");
                writeCell(writer, 6, i, true, writer.getHeadCellStyle(), "xxxx：");
                writeCell(writer, 7, i, false, writer.getHeadCellStyle(), "cpbm");
                i++;

                writeCell(writer, 0, i, true, writer.getHeadCellStyle(), "xxxx：");
                writeCell(writer, 1, i, false, writer.getHeadCellStyle(), "sjje");
                writeCell(writer, 2, i, true, writer.getHeadCellStyle(), "xxxx：");
                writeCell(writer, 3, i, false, writer.getHeadCellStyle(), "spmc");
                writeCell(writer, 4, i, true, writer.getHeadCellStyle(), "xxxx：");
                writeCell(writer, 5, i, false, writer.getHeadCellStyle(), "gcjk");
                writeCell(writer, 6, i, true, writer.getHeadCellStyle(), "xxxx：");
                writeCell(writer, 7, i, false, writer.getHeadCellStyle(), "cpbm");
                i++;

                writeCell(writer, 0, i, true, writer.getHeadCellStyle(), "xxxx：");
                writeCell(writer, 1, i, false, writer.getHeadCellStyle(), "sjje");
                writeCell(writer, 2, i, true, writer.getHeadCellStyle(), "xxxx：");
                writeCell(writer, 3, i, false, writer.getHeadCellStyle(), "spmc");
                writeCell(writer, 4, i, true, writer.getHeadCellStyle(), "xxxx：");
                writeCell(writer, 5, i, false, writer.getHeadCellStyle(), "gcjk");
                writeCell(writer, 6, i, true, writer.getHeadCellStyle(), "xxxx：");
                writeCell(writer, 7, i, false, writer.getHeadCellStyle(), "cpbm");

                i++;

                writeCell(writer, 0, i, true, writer.getHeadCellStyle(), "xxxx：");
                writeCell(writer, 1, i, false, writer.getHeadCellStyle(), "sjje");
                writeCell(writer, 2, i, true, writer.getHeadCellStyle(), "xxxx：");
                writeCell(writer, 3, i, false, writer.getHeadCellStyle(), "spmc");
                writeCell(writer, 4, i, true, writer.getHeadCellStyle(), "xxxx：");
                writeCell(writer, 5, i, false, writer.getHeadCellStyle(), "gcjk");
                writeCell(writer, 6, i, true, writer.getHeadCellStyle(), "xxxx：");
                writeCell(writer, 7, i, false, writer.getHeadCellStyle(), "cpbm");

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
    public static void writeCell(ExcelWriter writer, int x, int y, boolean isWriteStyle, CellStyle style, Object value) {

        //数字从0开始算       前面两个数字是第几行到第几行合并    后面两个数字是第几列到第几列合并
        //第一行
        writer.writeCellValue(x, y, value);
        if(isWriteStyle){
            writer.setStyle(writer.getHeadCellStyle(), x, y);
        }

    }

}
