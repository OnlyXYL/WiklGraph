package top.wikl.component.utils;

import org.apache.poi.ss.usermodel.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.poi.ss.usermodel.DateUtil.isADateFormat;

/**
 * @author XYL
 * @title: ExcelUtils
 * @description: TODO
 * @date 2019/12/5 11:11
 * @return
 * @since V1.0
 */
public class ExcelUtils {

    /**
     * 获取一行的内容,Map存储,存储方式由参数定义
     *
     * @param row        行对象
     * @param isValueKey * 是否以单元格内容作为Key?key为单元格内容, value为下标索引：key为下标索引, value为单元格内容
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/9 11:45
     * @since V1.1
     */
    public static Map<Object, Object> getRowDataToMap(Row row, boolean isValueKey) {
        Map<Object, Object> headDatas = new LinkedHashMap<>();
        short countCellNum = row.getLastCellNum();
        // 在外面判断isValueKey是为了提高效率,放在循环体中降低效率
        if (isValueKey) {
            for (int j = 0; j < countCellNum; j++) {
                Cell cell = row.getCell(j);
                if (isExistCell(cell)) {
                    // Key=单元格内容, Value=下标索引
                    headDatas.put(getCellValue(cell), j);
                }
            }
        } else {
            for (int j = 0; j < countCellNum; j++) {
                Cell cell = row.getCell(j);
                if (isExistCell(cell)) {
                    // Key=下标索引, Value=单元格内容
                    headDatas.put(j, getCellValue(cell));
                }
            }
        }
        return headDatas;
    }

    /**
     * 获取一行指定列的数据集
     *
     * @param headDataMap 表列头Map数据,key=单元格内容,value为下标索引号
     * @param row         需获取的指定行
     * @param attributes  需获取的指定单元格
     * @return 一行指定列的数据集
     * @author XYL
     * @date 2019/12/5 17:41
     * @since V1.0
     */
    public static List<Object> getByGivenAttributeAndRowValue(Map<Object, Object> headDataMap, Row row, String[] attributes) {
        List<Object> datas = new ArrayList<>();
        for (int i = 0; i < attributes.length; i++) {
            Integer index = (Integer) headDataMap.get(attributes[i]);
            if (index == null) {
                System.out.println("查询列：" + attributes[i] + "失败！");
            } else {
                Cell cell = row.getCell(index);
                Object cellValue = getCellValue(cell);
                if (cellValue == null) {
                    cellValue = "";
                }
                datas.add(cellValue);
            }

        }
        return datas;
    }

    /**
     * 校验表头是否符合模板
     *
     * @param headDataMap 表头数据
     * @param attributes  模板数据
     * @return
     * @author XYL
     * @date 2019/12/5 17:40
     * @since V1.0
     */
    public static boolean checkHead(Map<Object, Object> headDataMap, String[] attributes) {

        for (int i = 0; i < attributes.length; i++) {

            Object object = headDataMap.get(attributes[i]);

            if (Objects.isNull(object)) {

                return false;
            }

            String head = object.toString();

            int parseInt = Integer.parseInt(head);

            //判断表头数据是否和模板相等
            if (parseInt != i) {
                return false;
            }
        }
        return true;
    }

    /**
     * * 验证指定Sheet表下的下标索引行是否存在,存在则返回原行对象,不存在则创建Row对象后返回 * * @param sheet * 指定的Sheet对象 * @param rowIndex * 指定的下标索引行号 * @return Row行(存在就返回,不存在就新建)
     */
    public static Row isExistRow(Sheet sheet, int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            return sheet.createRow(rowIndex);
        } else {
            // System.out.println("警告提示: 您设置的Sheet表名称【"+sheet.getSheetName()+"】中的第【"+rowIndex+"】行已存在!");
            return row;
        }
    }

    public static boolean isCellDateFormatted(Cell cell) {
        if (cell == null) {
            return false;
        }
        boolean bDate = false;

        double d = cell.getNumericCellValue();
        if (DateUtil.isValidExcelDate(d)) {
            CellStyle style = cell.getCellStyle();
            if (style == null) {
                return false;
            }
            int i = style.getDataFormat();
            String f = style.getDataFormatString();
            bDate = isADateFormat(i, f);
        }
        return bDate;
    }

    /**
     * 获取单元格各类型值，返回字符串类型
     *
     * @param cell
     * @return
     * @author XYL
     * @date 2020/1/10 18:22
     * @since V1.0
     */
    public static String getCellValue(Cell cell) {
        //判断是否为null或空串
        if (cell == null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        int cellType = cell.getCellType();
        switch (cellType) {
            // 数字
            case Cell.CELL_TYPE_NUMERIC:
                short format = cell.getCellStyle().getDataFormat();
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = null;
                    if (format == 20 || format == 32) {
                        sdf = new SimpleDateFormat("HH:mm");
                    } else if (format == 14 || format == 31 || format == 57 || format == 58) {
                        // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        double value = cell.getNumericCellValue();
                        Date date = DateUtil
                                .getJavaDate(value);
                        cellValue = sdf.format(date);
                    } else {
                        // 日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    }
                    try {
                        // 日期
                        cellValue = sdf.format(cell.getDateCellValue());
                    } catch (Exception e) {
                        try {
                            throw new Exception("exception on get date data !".concat(e.toString()));
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } finally {
                        sdf = null;
                    }
                } else {
                    BigDecimal bd = new BigDecimal(cell.getNumericCellValue());
                    // 数值 这种用BigDecimal包装再获取plainString，可以防止获取到科学计数值
                    cellValue = bd.toPlainString();
                }
                break;
            // 字符串
            case Cell.CELL_TYPE_STRING:
                cellValue = cell.getStringCellValue();
                break;
            // Boolean
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = cell.getBooleanCellValue() + "";
                ;
                break;
            // 公式
            case Cell.CELL_TYPE_FORMULA:
                cellValue = cell.getCellFormula();
                break;
            // 空值
            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            // 故障
            case Cell.CELL_TYPE_ERROR:
                cellValue = "ERROR VALUE";
                break;
            default:
                cellValue = "UNKNOW VALUE";
                break;
        }
        return cellValue;
    }


    /**
     * * 验证Sheet表对象是否存在,返回Boolean结果 * * @param sheet * 验证的Sheet表对象 * @return 是否存在Sheet表的Boolean值
     */
    protected static boolean isExistSheet(Sheet sheet) {
        return isExist(sheet);
    }

    /**
     * * 验证一行对象是否存在,返回Boolean结果 * * @param row * 验证的行对象 * @return 是否存在行的Boolean值
     */
    protected static boolean isExistRow(Row row) {
        return isExist(row);
    }

    /**
     * * 验证一单元格是否存在 ,返回Boolean结果 * * @param cell * 验证的单元格对象 * @return 是否存在列的Boolean值
     */
    protected static boolean isExistCell(Cell cell) {
        return isExist(cell);
    }

    /**
     * 对象是否为空对象 * * @param object * 验证的对象 * @return 对象是否为空
     */
    protected static boolean isExist(Object object) {
        if (object == null) {
            return false;
        }
        return true;
    }

}
