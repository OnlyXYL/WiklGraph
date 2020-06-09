package top.wikl.component.tools.poi.fileupload.file.excel.xlsx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author XYL
 * @title: XlsxService
 * @description: 【excel方式构建】xlsx文件
 * @date 2019/10/22 17:32
 * @return
 * @since V1.0
 */
@Slf4j
public class XlsxConceptService implements XlsxInterface {

    private static final String XLSX = "xlsx";

    @Override
    public boolean support(String type, String graphType) {

        if (XLSX.equals(type)) {
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
