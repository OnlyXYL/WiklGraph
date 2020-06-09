package top.wikl.component.tools.poi.fileupload.file.excel.csv;

import org.springframework.web.multipart.MultipartFile;
import top.wikl.utils.excel.FileUpload;

/**
 * @author XYL
 * @title: CsvRootService
 * @description: TODO
 * @date 2019/10/29 18:09
 * @return
 * @since V1.0
 */
public class CsvRootService {

    /**
     * 校验csv文件规则
     *
     * @param multipartFile, suffix
     * @return cn.com.bmsmart.entity.kngraph.FileError
     * @author XYL
     * @date 2019/10/29 18:10
     * @since V1.0
     */
    public boolean checkRule(MultipartFile multipartFile, String suffix) {

        //文件后缀
        String extensionName = FileUpload.getExtensionName(multipartFile.getOriginalFilename());

        //判断文件格式
        if (!suffix.equals(extensionName)) {

            //todo:  "文件格式错误，请重新上传！";
            return false;
        }

        return true;

    }
}