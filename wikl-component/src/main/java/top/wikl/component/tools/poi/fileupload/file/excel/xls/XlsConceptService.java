package top.wikl.component.tools.poi.fileupload.file.excel.xls;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import top.wikl.component.tools.poi.fileupload.file.excel.csv.CsvInterface;

/**
 * @author XYL
 * @title: XlsService
 * @description: 【excel方式构建】xls文件
 * @date 2019/10/22 17:33
 * @return
 * @since V1.0
 */
@Slf4j
public class XlsConceptService implements CsvInterface {

    private static final String XLS = "xls";

    @Override
    public boolean support(String type, String graphType) {

        if (XLS.equals(type)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkRule(MultipartFile multipartFile, String graphType) {

        return true;

    }

    @Override
    public void checkData(MultipartFile multipartFile, String kngraphId) {
    }

    @Override
    public String excute(MultipartFile file, String kngraphId, String operateType, String graphType) {

        return "";

    }
}
