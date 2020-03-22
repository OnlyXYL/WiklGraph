package top.wikl.janusgraph.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import top.wikl.utils.date.WiklDateUtil;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author XYL
 * @title: AutoBuildGrpah
 * @description: TODO
 * @date 2020/1/3 14:23
 * @return
 * @since V1.0
 */
@Slf4j
@Component
public class AutoBuildGrpah {

    public static void main(String[] args) {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMd");


        String format1 = format.format(new Date());

        System.out.println(format1);

        //当前时间
        String day = WiklDateUtil.getDays();

        String dayDate = WiklDateUtil.getAfterDayDate("-1", WiklDateUtil.YEAR_MONTH_DAY_FORMAT);

        log.info("当前日期 --> {}，{} 天之后日期是 --> {}", day, -1, dayDate);


        try {
            MultipartFile fileItem = transferTo("E:\\概念_1577763976628.xls");

            MultipartFile fileItem1 = transferTo1("E:\\概念_1577763976628.xls");

            long size1 = fileItem1.getSize();

            byte[] bytes = fileItem1.getBytes();

            long size = fileItem.getSize();


            String originalFilename = fileItem.getOriginalFilename();

            System.out.println(originalFilename);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 转换 file to MultipartFile
     *
     * @param
     * @return
     * @author XYL
     * @date 2020/1/3 16:09
     * @since V1.0
     */
    public static MultipartFile transferTo(String filePath) throws Exception {
        File file = new File(filePath);
        DiskFileItem fileItem = new DiskFileItem("file", "text/plain", false, file.getName(), (int) file.length(), file.getParentFile());
        fileItem.getOutputStream();
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

        return multipartFile;
    }

    public static MultipartFile transferTo1(String filePath) throws Exception {
        File file = new File(filePath);
        DiskFileItem fileItem = new DiskFileItem("file", "text/plain", false, file.getName(), (int) file.length(), file.getParentFile());
        try {
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
        } catch (IOException ex) {
        }

        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

        return multipartFile;

    }

}
