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

        byte[] bytes = readExcel(filePath);

        return bytes;
    }

    @Override
    public byte[] instanceTemplate(String filePath) {

        //1. 需要概念列表【根据概念列表生成实例模板的每个sheet】

        //2. 需要每个概念的属性【根据每个概念的属性，生成实例模板每个sheet中的列】

        //3. 实例数据规则【根据规则处理实例数据】

        //3. 往实例模板中插入数据

        //4. 数据跳过规则【不满足条件的数据，不插入实例模板中】

        //+++++++++++++++++++++++

        //1. 研究对象
        HashMap<String, String> map = new HashMap<>(15);
        map.put("57d104b5095b124816eb374a", "盆地");
        map.put("57d104b5095b124816eb374b", "油气田");
        map.put("57d104b5095b124816eb374c", "井");
        map.put("57d104b5095b124816eb374e", "地层");
        map.put("57d104b5095b124816eb374d", "矿权区");
        map.put("57d104b5095b124816eb374f", "构造单元");
        map.put("57d104b5095b124816eb3750", "油气藏");
        map.put("57d104b5095b124816eb3751", "物化探工区");
        map.put("5a7d65bc3335733ac5a06d8f", "政治区域");
        map.put("5a7d65cf3335733ac5a06d90", "开发单元_区块");
        map.put("5a7d65f63335733ac5a06d91", "工艺_技术");
        map.put("5a7d660e3335733ac5a06d92", "设备");
        map.put("5a7d661f3335733ac5a06d93", "工具_仪器");
        map.put("5a7d66323335733ac5a06d94", "材料");
        map.put("5a7d663c3335733ac5a06d95", "行政单位");

        return new byte[0];
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
    private static byte[] readExcel(String filePath) {

        Map<String, byte[]> downloadMap = new HashMap<>();

        //已经创建过的概念
        ArrayList<String> used = new ArrayList<>();

        byte[] zip = null;

        //创建表格
        HSSFWorkbook wk = new HSSFWorkbook();

        HSSFSheet wSheet;

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

                        //获取第二列数据，作为sheet名
                        Cell rowCell = row.getCell(1);

                        if (rowCell != null) {

                            //获取当前行的值
                            String cellValue = rowCell.toString();

                            System.out.printf("当前行号 【%d】，当前列【%s】，当前值【%s】\n", rIndex, "指标id", cellValue);

                            //如果当前分类已经创建，新增一行到当前sheet
                            if (!used.contains(cellValue)) {

                                //使用分类名称作为sheet名
                                wSheet = wk.createSheet(cellValue);

                                used.add(cellValue);

                                //写入文件
                                createRow(wSheet, row);

                            }

                        }

                        //15行，新创建一个文件
                        if (rowNumber == 15) {

                            rowNumber = 0;

                            //写入新文件
                            writeExcel(wk, downloadMap);

                            wk = new HSSFWorkbook();
                        }

                    }

                    rowNumber++;

                }

                if (rowNumber > 0) {

                    writeExcel(wk, downloadMap);
                }

                //开始写入excel
                zip = FileZip.zip(downloadMap);

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
     * @param rowdata
     * @return
     * @author XYL
     * @date 2020/4/1 18:35
     * @since V2.0
     */
    public static void createRow(HSSFSheet sheet, Row rowdata) {

        List<String> properties = Arrays.asList(
                "主键ID",
                "分类名称",
                "父分类ID",
                "发布状态",
                "序号",
                "是否包含子节点",
                "作用对象",
                "参与对象"
        );

        HSSFRow head = sheet.createRow(0);

        HSSFRow row = sheet.createRow(1);

        for (int i = 0; i <= properties.size() - 1; i++) {

            HSSFCell cell = head.createCell(i);
            cell.setCellValue(properties.get(i));

            HSSFCell rowCell = row.createCell(i);

            Cell cell1 = rowdata.getCell(i);

            if (Objects.isNull(cell1)) {
                rowCell.setCellValue("");
            } else {
                rowCell.setCellValue(cell1.toString());
            }
        }

        //列边界
        HSSFCell eof = head.createCell(properties.size());
        eof.setCellValue("#EOF#");

        //行边界
        HSSFRow reof = sheet.createRow(2);

        HSSFCell cell = reof.createCell(0);

        cell.setCellValue("#EOF#");
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
    public static void writeExcel(HSSFWorkbook wk, Map<String, byte[]> downloadMap) {

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

        downloadMap.put("知识分类实例_" + System.currentTimeMillis() + ".xls", bytes);

    }

}
