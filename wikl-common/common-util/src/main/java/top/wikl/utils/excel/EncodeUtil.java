package top.wikl.utils.excel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.BitSet;

/**
 * 文件编码工具类
 *
 * @param
 * @author XYL
 * @date 2019/11/6 11:51
 * @return
 * @since V1.0
 */
@Slf4j
public class EncodeUtil {

    private static int BYTE_SIZE = 8;
    public static String CODE_UTF8 = "UTF-8";
    public static String CODE_UTF8_BOM = "UTF-8_BOM";
    public static String CODE_GBK = "GBK";

    /**
     * 通过文件全名称获取编码集名称
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/11/6 11:51
     * @since V1.0
     */
    public static String getEncode(String fullFileName, boolean ignoreBom) throws Exception {
        log.debug("fullFileName ; {}", fullFileName);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fullFileName));
        return getEncode(bis, ignoreBom);
    }

    /**
     * 根据MultipartFile 获取文件编码
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/11/6 11:51
     * @since V1.0
     */
    public static String getEncode(MultipartFile file, boolean ignoreBom) throws Exception {
        log.debug("fullFileName ; {}", file.getOriginalFilename());
        BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
        return getEncode(bis, ignoreBom);
    }

    /**
     * 通过文件缓存流获取编码集名称，文件流必须为未曾
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/11/6 11:51
     * @since V1.0
     */
    public static String getEncode(BufferedInputStream bis, boolean ignoreBom) throws Exception {
        bis.mark(0);

        String encodeType = "未识别";
        byte[] head = new byte[3];
        bis.read(head);
        if (head[0] == -1 && head[1] == -2) {
            encodeType = "UTF-16";
        } else if (head[0] == -2 && head[1] == -1) {
            encodeType = "Unicode";
        } else if (head[0] == -17 && head[1] == -69 && head[2] == -65) {
            //带BOM
            if (ignoreBom) {
                encodeType = CODE_UTF8;
            } else {
                encodeType = CODE_UTF8_BOM;
            }
        } else if ("Unicode".equals(encodeType)) {
            encodeType = "UTF-16";
        } else if (isUTF8(bis)) {
            encodeType = CODE_UTF8;
        } else {
            encodeType = CODE_GBK;
        }
        log.info("result encode type : " + encodeType);
        return encodeType;
    }

    /**
     * 是否是无BOM的UTF8格式，不判断常规场景，只区分无BOM UTF8和GBK
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/11/6 11:54
     * @since V1.0
     */
    private static boolean isUTF8(BufferedInputStream bis) throws Exception {
        bis.reset();

        //读取第一个字节
        int code = bis.read();
        do {
            BitSet bitSet = convert2BitSet(code);
            //判断是否为单字节
            if (bitSet.get(0)) {
                //多字节时，再读取N个字节
                if (!checkMultiByte(bis, bitSet)) {
                    //未检测通过,直接返回
                    return false;
                }
            } else {
                //单字节时什么都不用做，再次读取字节
            }
            code = bis.read();
        } while (code != -1);
        return true;
    }

    /**
     * 检测多字节，判断是否为utf8，已经读取了一个字节
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/11/6 11:55
     * @since V1.0
     */
    private static boolean checkMultiByte(BufferedInputStream bis, BitSet bitSet) throws Exception {
        int count = getCountOfSequential(bitSet);
        //已经读取了一个字节，不能再读取
        byte[] bytes = new byte[count - 1];
        bis.read(bytes);
        for (byte b : bytes) {
            if (!checkUtf8Byte(b)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检测单字节，判断是否为utf8
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/11/6 11:55
     * @since V1.0
     */
    private static boolean checkUtf8Byte(byte b) throws Exception {
        BitSet bitSet = convert2BitSet(b);
        return bitSet.get(0) && !bitSet.get(1);
    }

    /**
     * 检测bitSet中从开始有多少个连续的1
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/11/6 11:55
     * @since V1.0
     */
    private static int getCountOfSequential(BitSet bitSet) {
        int count = 0;
        for (int i = 0; i < BYTE_SIZE; i++) {
            if (bitSet.get(i)) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    /**
     * 将整形转为BitSet
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/11/6 11:54
     * @since V1.0
     */
    private static BitSet convert2BitSet(int code) {
        BitSet bitSet = new BitSet(BYTE_SIZE);

        for (int i = 0; i < BYTE_SIZE; i++) {
            int tmp3 = code >> (BYTE_SIZE - i - 1);
            int tmp2 = 0x1 & tmp3;
            if (tmp2 == 1) {
                bitSet.set(i);
            }
        }
        return bitSet;
    }

    /**
     * 将一指定编码的文件转换为另一编码的文件
     *
     * @param oldFullFileName
     * @param oldCharsetName
     * @param newFullFileName
     * @param newCharsetName
     * @return
     * @author XYL
     * @date 2019/11/6 11:54
     * @since V1.0
     */
    public static void convert(String oldFullFileName, String oldCharsetName, String newFullFileName, String newCharsetName) throws Exception {
        log.info("the old file name is : {}, The oldCharsetName is : {}", oldFullFileName, oldCharsetName);
        log.info("the new file name is : {}, The newCharsetName is : {}", newFullFileName, newCharsetName);

        StringBuffer content = new StringBuffer();

        BufferedReader bin = new BufferedReader(new InputStreamReader(new FileInputStream(oldFullFileName), oldCharsetName));
        String line;
        while ((line = bin.readLine()) != null) {
            content.append(line);
            content.append(System.getProperty("line.separator"));
        }
        newFullFileName = newFullFileName.replace("\\", "/");
        File dir = new File(newFullFileName.substring(0, newFullFileName.lastIndexOf("/")));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Writer out = new OutputStreamWriter(new FileOutputStream(newFullFileName), newCharsetName);
        out.write(content.toString());
    }

}