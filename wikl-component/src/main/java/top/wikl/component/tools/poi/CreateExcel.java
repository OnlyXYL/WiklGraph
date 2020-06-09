package top.wikl.component.tools.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author XYL
 * @title: CreateExcel
 * @description: TODO
 * @date 2020/4/1 17:31
 * @return
 * @since V1.0
 */
public class CreateExcel {
    public static void main(String[] args) {

        //创建表格
        HSSFWorkbook wk = new HSSFWorkbook();

        //sheetName
        String sheetName = "";

        //创建概念名称为指定名称的Sheet
        HSSFSheet sheet = wk.createSheet(sheetName);

    }

    public void readExcel(){

    }

    /**
     * 创建概念一行数据
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/10/17 18:10
     * @since V1.0
     */
    public void createConceptRow(HSSFSheet sheet, int rowNumber) {

        HSSFRow row1 = sheet.createRow(rowNumber);

        HSSFCell propertyName = row1.createCell(0);
        propertyName.setCellValue("属性名称");

        HSSFCell dataType = row1.createCell(1);
        dataType.setCellValue("数据类型");

        HSSFCell showPro = row1.createCell(2);
        showPro.setCellValue("显示属性");

        HSSFCell uniquePro = row1.createCell(3);
        uniquePro.setCellValue("唯一属性");

        HSSFCell EOF = row1.createCell(4);
        EOF.setCellValue("#EOF#");
    }


}
