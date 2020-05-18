package top.wikl.component.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import top.wikl.component.service.PocService;
import top.wikl.component.utils.ExcelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * @author XYL
 * @title: PocServiceImpl
 * @description: TODO
 * @date 2020/5/9 11:31
 * @return
 * @since V1.0
 */
@Slf4j
@Service
public class PocServiceImpl implements PocService {

    @Override
    public Map<String, String> gw_bd(String filePath, String Scolumn, String Ecolumn) {

        Map<String, String> map = new HashMap<>();

        //调用方法，获取数据
        Map<String, Set<String>> stringSetMap = this.analyze(filePath, Scolumn, Ecolumn);

        //处理结果
        for (Map.Entry<String, Set<String>> entry : stringSetMap.entrySet()) {

            String key = entry.getKey();

            ArrayList<String> list = new ArrayList<>(entry.getValue());

            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < list.size(); i++) {

                String value = list.get(i);

                if (i>0) {
                    builder.append(";");
                    builder.append(value);
                }else{

                    builder.append(value);
                }

            }

            map.put(key, builder.toString());

        }

        return map;
    }

    /**
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/9 15:49
     * @since V1.1
     */
    private Map<String, Set<String>> analyze(String filePath, String Scolumn, String Ecolumn) {

        //已经创建过的概念
        Map<String, Set<String>> map = new HashMap<>();

        byte[] zip = null;

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

                //所有sheet
                int sheets = wb.getNumberOfSheets();

                for (int i = 0; i < sheets; i++) {

                    //开始解析
                    Sheet sheet = wb.getSheetAt(i);

                    //表头
                    Row heads = sheet.getRow(0);

                    //1. 表头转换为map,用来根据名称获取索引；然后根据索引获取具体的列
                    Map<Object, Object> headsMap = ExcelUtils.getRowDataToMap(heads, true);

                    //第一行是列名，所以不读
                    int firstRowIndex = sheet.getFirstRowNum() + 1;
                    int lastRowIndex = sheet.getLastRowNum();

                    System.out.println("+++++++++++++++++ 开始解析Excel +++++++++++++++++");
                    System.out.println("");

                    System.out.printf("【总行数】: %d \n", lastRowIndex);

                    //遍历行
                    for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
                        Row row = sheet.getRow(rIndex);
                        if (row != null) {

                            //获取指定列下标
                            Integer ScolumnIndex = (Integer) headsMap.get(Scolumn);

                            //获取指定列数据
                            String stringCellValue = row.getCell(ScolumnIndex).getStringCellValue();

                            //判断map中是否包含当前设备
                            Set<String> strings = map.get(stringCellValue);

                            //获取指定列
                            Integer eColumnIndex = (Integer) headsMap.get(Ecolumn);

                            //关联列的值
                            String cellValue = row.getCell(eColumnIndex).getStringCellValue();

                            //不包含，说明是新的数据项，加入集合
                            if (Objects.isNull(strings) || strings.isEmpty()) {

                                //关联数据
                                Set<String> set = new HashSet<>();

                                if (StringUtils.isNotBlank(cellValue)) {
                                    set.add(cellValue);
                                    map.put(stringCellValue, set);
                                }

                            } else {

                                //判断数据集合中是否包含当前数据
                                if (!strings.contains(cellValue)&&!strings.contains(stringCellValue)) {
                                    if (StringUtils.isNotBlank(cellValue)) {
                                        strings.add(cellValue);
                                        map.put(stringCellValue, strings);
                                    }
                                }
                            }
                            System.out.printf("当前行号 【%d】，当前列【%s】，当前值【%s】\n", rIndex, ScolumnIndex, stringCellValue);
                        }
                    }
                }

                System.out.println("+++++++++++++++++ 解析结束 +++++++++++++++++");

            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

}
