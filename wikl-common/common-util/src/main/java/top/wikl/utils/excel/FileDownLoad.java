package top.wikl.utils.excel;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URLEncoder;

/**
 * @author XYL
 * @title: FileDownLoad
 * @description: TODO
 * @date 2020/4/1 19:07
 * @return
 * @since V1.0
 */
public class FileDownLoad {


    /**
     * 文件下载
     *
     * @param bytes
     * @return
     */
    public static ResponseEntity<byte[]> downloadFile(byte[] bytes, String fileName) throws Exception {

        HttpHeaders headers = new HttpHeaders();

        //处理IE下载文件的中文名称乱码的问题
        if (fileName.contains("ie")) {
            fileName = StringUtils.remove(fileName, "ie");
            fileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        }
        if (fileName.contains("other")) {
            fileName = StringUtils.remove(fileName, "other");
            fileName = new String(fileName.getBytes(), "iso-8859-1");
        }
        headers.add("Content-Disposition", "attachment;filename=" + fileName);

        headers.add("fileName", fileName);

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        return responseEntity;
    }


}
