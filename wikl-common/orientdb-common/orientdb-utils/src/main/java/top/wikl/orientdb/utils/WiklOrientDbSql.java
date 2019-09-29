package top.wikl.orientdb.utils;

import cn.hutool.core.date.DateTime;
import top.wikl.orientdb.enums.WiklOrientDbDataTypeEnums;

import java.util.Date;
import java.util.Map;

/**
 * @author XYL
 * @title: WiklOrientDbSql
 * @description: Orientdb sql工具类
 * @date 2019/9/27 14:55
 * @return
 * @since V1.0
 */
public class WiklOrientDbSql {

    /**
     * 返回一个新建 class 的语句
     *
     * @param label
     * @return java.lang.String
     * @author XYL
     * @date 2019/9/27 14:56
     * @since V1.0
     */
    public static String createClass(String label) {

        StringBuilder sql = new StringBuilder();

        sql.append("create class if not exits " + label);

        return sql.toString();
    }

    /**
     * 返回一个创建属性的语句
     * <p>
     * <"name","张三"></name,"">
     * <p>
     * <"age",18></"age",18>
     * <p>
     * <"birthday", new Date></"birthday",>
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 15:05
     * @since V1.0
     */
    public static String createProperties(String label, Map<String, Object> properties) {

        StringBuilder sql = new StringBuilder();

        sql.append("create property");

        for (Map.Entry<String, Object> entry : properties.entrySet()) {

            String key = entry.getKey();

            Object value = entry.getValue();

            if (value instanceof Boolean) {

                sql.append(label + "." + key + WiklOrientDbDataTypeEnums.BOOLEAN.getValue());

            } else if (value instanceof Short) {

                sql.append(label + "." + key + WiklOrientDbDataTypeEnums.SHORT.getValue());

            } else if (value instanceof Date) {

                sql.append(label + "." + key + WiklOrientDbDataTypeEnums.DATE.getValue());

            } else if (value instanceof DateTime) {

                sql.append(label + "." + key + WiklOrientDbDataTypeEnums.DATETIME.getValue());

            } else if (value instanceof Byte) {

                sql.append(label + "." + key + WiklOrientDbDataTypeEnums.BYTE.getValue());

            } else if (value instanceof Integer) {

                sql.append(label + "." + key + WiklOrientDbDataTypeEnums.INTEGER.getValue());

            } else if (value instanceof Long) {

                sql.append(label + "." + key + WiklOrientDbDataTypeEnums.LONG.getValue());

            } else if (value instanceof String) {

                sql.append(label + "." + key + WiklOrientDbDataTypeEnums.STRING.getValue());

            } else if (value instanceof Float) {

                sql.append(label + "." + key + WiklOrientDbDataTypeEnums.FLOAT.getValue());

            }

        }

        return sql.toString();

    }

}
