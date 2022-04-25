package top.wikl.wikljava.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author XYL
 * @version 1.2
 * @date 2022/03/30 15:55
 */
public class Testb {

    public static void main(String[] args) {

        try {
            File outFile = new File("D:\\file\\数据元模型tree.txt");//记录结果文件

            if (outFile.exists()) {

                outFile.delete();

                outFile.createNewFile();
            }

            final List<String> strings = Arrays.asList("aaa1", "bbb2", "bbb3", "bbb4", "bbb5");

            final int size = strings.size();

            for (int i = 0; i < size; i++) {

                final String s = strings.get(i);

                final String string = rowString(i, s);

                System.out.println(string);

                saveRecordInFile(outFile, string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static String rowString(int length, String value) {

        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++) {

            builder.append(" ");
        }
        builder.append(value).append("\t");

        final String string = builder.toString();

        return string;
    }

    public static void saveRecordInFile(File file, String str) {
        try {

            FileWriter writer = null;
            try {
                // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
                writer = new FileWriter(file, true);
                writer.write(str);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("记录保存失败");
        }
    }
}
