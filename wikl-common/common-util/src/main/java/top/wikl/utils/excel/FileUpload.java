package top.wikl.utils.excel;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * 上传文件 创建时间：2014年12月23日
 */
public class FileUpload {

    /**
     * @param file     //文件对象
     * @param filePath //上传路径
     * @param fileName //文件名
     * @return 文件名
     */
    public static String fileUp(MultipartFile file, String filePath, String fileName) {
        String extName = ""; // 扩展名格式：
        try {
            if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
                extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }
            copyFile(file.getInputStream(), filePath, fileName + extName).replaceAll("-", "");
        } catch (IOException e) {
            System.out.println(e);
        }
        return fileName + extName;
    }

    /**
     * @param file     //文件对象
     * @param filePath //上传路径
     * @param fileName //文件名
     * @return 文件名
     */
    public static String fileUp(File file, String filePath, String fileName) {
        String extName = ""; // 扩展名格式：
        try {
            if (file.getName().lastIndexOf(".") >= 0) {
                extName = file.getName().substring(file.getName().lastIndexOf("."));
            }
            InputStream in = new FileInputStream(file);
            copyFile(in, filePath, fileName + extName).replaceAll("-", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName + extName;
    }

    /**
     * 写文件到当前目录的upload目录中
     *
     * @param in
     * @param realName
     * @throws IOException
     */
    private static String copyFile(InputStream in, String dir, String realName) throws IOException {
        File file = new File(dir, realName);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        FileUtils.copyInputStreamToFile(in, file);
        return realName;
    }

    /**
     * 获取项目路径
     *
     * @return
     */
    public static String getClasspath() {
        String path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "")
                .replaceAll("file:/", "").replaceAll("%20", " ").trim();

        if (path.indexOf(":") != 1) {
            path = File.separator + path;
        }
        return path;
    }

    /**
     * Java文件操作 获取文件扩展名
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 判断文件编码格式
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String getCharset(InputStream fileName) throws IOException {

        BufferedInputStream bin = null;
        int bom = 0;
        String str = " ";
        String str2 = "";
        try {
            bin = new BufferedInputStream(fileName);
            bom = (bin.read() << 8) + bin.read();
            // 获取两个字节内容，如果文件无BOM信息，则通过判断字的字节长度区分编码格式
            byte bs[] = new byte[10];
            while (str.matches("\\s+\\w*")) {
                bin.read(bs);
                str = new String(bs, "UTF-8");
            }
            str2 = new String(bs, "GBK");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if ( bin != null){
                bin.close();
            }
        }


        String code = null;
        // 有BOM
        switch (bom) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                // 无BOM
                if (str.length() <= str2.length()) {
                    code = "UTF-8";
                } else {
                    code = "GBK";
                }
        }

        return code;

    }
}
