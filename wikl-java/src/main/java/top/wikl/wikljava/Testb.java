package top.wikl.wikljava;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/5/17 0017 15:48
 */
public class Testb {

    public static void main(String[] args) {

        String a = "group1/M00/00/25/CgAugmCLtBCAQ87nAAAAIYEjMBM0.model";

        final String formatFileSize = formatFileSize(10485760);

        final String substring = formatFileSize.substring(0,formatFileSize.length() - 2);

        final double parseInt = Double.parseDouble("222");

        System.out.println(parseInt);

        if (parseInt>3) {
            System.out.println("文件过大");
        }

        System.out.println(formatFileSize);
    }

    public static String formatFileSize(long size) {
        //定义GB的计算常量
        int GB = 1024 * 1024 * 1024;

        //定义MB的计算常量
        int MB = 1024 * 1024;

        //定义KB的计算常量
        int KB = 1024;

        //格式化小数
        DecimalFormat df = new DecimalFormat("0.00");
        String resultSize = "";
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = df.format(size / (float) GB) + "GB";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = df.format(size / (float) MB) + "MB";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = df.format(size / (float) KB) + "KB";
        } else {
            resultSize = size + "B   ";
        }
        return resultSize;
    }
}
