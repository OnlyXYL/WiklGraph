package top.wikl.component.service.impl;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import top.wikl.component.service.PoiService;
import top.wikl.utils.excel.FileZip;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author XYL
 * @title: PoiServiceImpl
 * @description: TODO
 * @date 2020/4/1 19:27
 * @return
 * @since V1.0
 */
@Service
public class PoiServiceImpl implements PoiService {

    @Override
    public byte[] excel(String filePath, String sheetName) {

        byte[] bytes = readExcel(filePath, true, sheetName);

        return bytes;
    }

    /**
     * /**
     * 读取excel
     *
     * @param
     * @return
     * @author XYL
     * @date 2020/4/1 18:27
     * @since V2.0
     */
    private static byte[] readExcel(String filePath, boolean wirteExcel, String sheetName) {

        byte[] zip = null;

        HSSFWorkbook wk = null;

        HSSFSheet wSheet = null;

        if (wirteExcel) {
            //创建表格
            wk = new HSSFWorkbook();

            wSheet = wk.createSheet(sheetName);
        }

        try {
            //String encoding = "GBK";
            File excel = new File(filePath);
            //判断文件是否存在
            if (excel.isFile() && excel.exists()) {

                //.是特殊字符，需要转义！！！！！
                String[] split = excel.getName().split("\\.");
                Workbook wb;
                //根据文件后缀（xls/xlsx）进行判断
                if ("xls".equals(split[1])) {
                    //文件流对象
                    FileInputStream fis = new FileInputStream(excel);
                    wb = new HSSFWorkbook(fis);
                } else if ("xlsx".equals(split[1])) {
                    wb = new XSSFWorkbook(excel);
                } else {
                    System.out.println("文件类型错误!");
                    throw new Exception("文件类型错误");
                }

                //开始解析
                //读取sheet 0
                Sheet sheet = wb.getSheetAt(0);

                //第一行是列名，所以不读
                int firstRowIndex = sheet.getFirstRowNum() + 1;
                int lastRowIndex = sheet.getLastRowNum();

                System.out.println("+++++++++++++++++ 开始解析Excel +++++++++++++++++");
                System.out.println("");

                System.out.println("【总行数】: %d \n\t" + lastRowIndex);

                int rowNumber = 1;

                //遍历行
                for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {

                        //获取第三列
                        Cell cell = row.getCell(2);
                        if (cell != null) {

                            //获取当前行的值
                            String cellValue = cell.toString();

                            System.out.printf("当前行号 【%d】，当前列【%s】，当前值【%s】\n", rIndex, "指标id", cellValue);

                            //写入文件
                            Integer integer = createRow(wSheet, cellValue, rowNumber);

                            rowNumber = integer;
                        }
                    }
                }

                //开始写入excel
                if (wirteExcel) {
                    Map<String, byte[]> stringMap = writeExcel(wk);

                    zip = FileZip.zip(stringMap);
                }

                System.out.println("+++++++++++++++++ 解析结束 +++++++++++++++++");

            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(wk)) {
                try {
                    wk.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return zip;
    }


    /**
     * 设置行
     * <p>
     * <p>
     * 1. 写标识
     * <p>
     * 2. 写新信息值
     * <p>
     * 3. 写所属指标id
     * <p>
     * 4. 写月份
     * <p>
     * 5. 写年份
     *
     * @param sheet
     * @param value
     * @param count
     * @return
     * @author XYL
     * @date 2020/4/1 18:35
     * @since V2.0
     */
    public static Integer createRow(HSSFSheet sheet, String value, int count) {

        int rowNum = count;

        //年纪和
        List<String> yearList = Arrays.asList("2018年", "2019年");

        //月集合
        List<String> monthList = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月");

        //处理年
        for (int i = 0; i <= yearList.size() - 1; i++) {

            //获取年
            String year = yearList.get(i);

            //月
            for (int j = 0; j <= monthList.size() - 1; j++) {

                HSSFRow row = sheet.createRow(count);

                //获取月
                String month = monthList.get(j);

                HSSFCell name = row.createCell(0);
                name.setCellValue(value + "_value" + rowNum);

                HSSFCell type = row.createCell(1);
                type.setCellValue(count);

                HSSFCell sign = row.createCell(2);
                sign.setCellValue(value);

                HSSFCell month_ = row.createCell(3);
                month_.setCellValue(month);

                HSSFCell year_ = row.createCell(4);
                year_.setCellValue(year);

                count++;

                rowNum++;
            }

        }

        return count;

    }


    /**
     * 写excel
     *
     * @param
     * @return
     * @author XYL
     * @date 2020/4/1 19:05
     * @since V2.0
     */
    public static Map<String, byte[]> writeExcel(HSSFWorkbook wk) {

        Map<String, byte[]> downloadMap = new HashMap<>();

        byte[] bytes = new byte[0];

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            wk.write(outputStream);
            bytes = outputStream.toByteArray();
            outputStream.close();
            wk.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        downloadMap.put("" + System.currentTimeMillis() + ".xls", bytes);

        return downloadMap;
    }

}
