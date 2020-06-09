package top.wikl.component.tools.poi.fileupload.file.excel.csv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.wikl.enums.files.FileOperateType;

import javax.annotation.Resource;

/**
 * @author XYL
 * @title: CsvService
 * @description: 【excel方式构建】csv文件
 * @date 2019/10/22 17:32
 * @return
 * @since V1.0
 */
@Slf4j
public class CsvConceptService implements CsvInterface {

    private static final String CSV = "csv";

    @Resource
    private CsvRootService csvRootService;

    @Override
    public boolean support(String type, String graphType) {
        if (CSV.equals(type)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkRule(MultipartFile multipartFile, String graphType) {

        boolean checkRule = csvRootService.checkRule(multipartFile, CSV);

        return checkRule;
    }

    @Override
    public void checkData(MultipartFile multipartFile, String kngraphId) {
    }

    @Override
    public String excute(MultipartFile files, String kngraphId, String operateType, String graphTYpe) {

        boolean checkRule;

        //判断是上传还是校验规则
        if (FileOperateType.check.getKey().equals(operateType)) {

            checkRule = this.checkRule(files, null);

        } else {

            //调用公共服务处理方法
        }

        return "success";
    }
}
