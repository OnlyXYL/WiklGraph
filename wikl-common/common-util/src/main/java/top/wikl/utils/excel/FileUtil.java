package top.wikl.utils.excel;

import java.io.*;

/**
 * @author XYL
 * @title: FileUtil
 * @description: TODO
 * @date 2020/5/14 10:39
 * @return
 * @since V1.0
 */
public class FileUtil {

    /**
     * 字符串写入文件
     *
     * @param strs     字符串数据
     * @param filePath 文件全路径
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/14 10:40
     * @since V1.1
     */
    public static void writeStrToFile(String[] strs, String filePath) throws IOException {

        FileOutputStream fos = new FileOutputStream(new File(filePath));

        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");

        BufferedWriter bw = new BufferedWriter(osw);

        for (String arr : strs) {

            bw.write(arr + "\t\n");

        }

        //注意关闭的先后顺序，先打开的后关闭，后打开的先关闭
        bw.close();

        osw.close();

        fos.close();
    }

}
