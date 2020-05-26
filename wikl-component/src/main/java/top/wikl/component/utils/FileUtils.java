package top.wikl.component.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

/**
 * 文件读取工具类
 *
 * @author XYL
 * @title: FileUtils
 * @description: TODO
 * @date 2020/5/20 11:03
 * @return
 * @since V1.0
 */
@Slf4j
public class FileUtils {

    public static void main(String[] args) {

        String filePath = "/file/test.txt";

        try {
            readFileFromResource(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 打印信息
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/20 16:25
     * @since V1.1
     */
    public void printInfo(InputStream inputStream) throws Exception {

        InputStreamReader reader = new InputStreamReader(inputStream, "utf8");

        BufferedReader buffer = new BufferedReader(reader);

        String readLine;
        try {
            while ((readLine = buffer.readLine()) != null) {

                System.out.println(readLine);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            inputStream.close();
        }

    }

    /**
     * 文件路径
     *
     * @param path 文件路径  example :  excel/test.xlsx
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/20 11:03
     * @since V1.1
     */
    public static InputStream readFileFromResource(String path) throws Exception {

        ClassPathResource classPathResource = new ClassPathResource(path);

        InputStream inputStream = classPathResource.getInputStream();

        return inputStream;

    }

    /**
     * 文件写入resource 目录下
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/20 14:23
     * @since V1.1
     */
    public static void writeFileToResource(Object object, String filePath) throws IOException {

        HSSFWorkbook wk = null;

        OutputStream outputStream = null;

        try {
            Class<?> aClass = object.getClass();

            //路径不为空，写入文件
            if (StringUtils.isNotBlank(filePath)) {

                outputStream = new FileOutputStream(filePath);
            } else {
                outputStream = new ByteArrayOutputStream();
            }

            if (aClass == HSSFWorkbook.class) {
                wk = (HSSFWorkbook) object;

                wk.write(outputStream);

                outputStream.close();

                wk.close();

            }
        } catch (IOException e) {
            e.printStackTrace();

            log.error("写入文件报错");

        } finally {

            if (outputStream != null) {

                outputStream.close();
            }

            if (wk != null) {

                wk.close();
            }
        }

    }

}
