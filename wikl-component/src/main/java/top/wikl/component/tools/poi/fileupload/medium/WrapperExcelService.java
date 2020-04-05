package top.wikl.component.tools.poi.fileupload.medium;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.wikl.component.tools.poi.fileupload.file.excel.ExcelInterface;
import top.wikl.entity.file.UpLoadInput;
import top.wikl.utils.excel.FileUpload;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author XYL
 * @title: WrapperExcelService
 * @description: 【文件上传】excel方式文件
 * @date 2019/10/22 17:51
 * @return
 * @since V1.0
 */
@Slf4j
public class WrapperExcelService implements MediumInterface {

    private static final String EXCEL = "excel";

    /**
     * excel 相关的所有服务
     */
    private List<ExcelInterface> interfaces;

    @Override
    public boolean support(String medium) {

        if (medium.equals(EXCEL)) {

            return true;
        }

        return false;
    }

    @Override
    public String excute(UpLoadInput upLoadInput) {


        //所有验证通过的实例配置数据
        Map<String, Set<String>> conceptConfigs = new HashMap<>();

        //获取文件
        MultipartFile[] files = upLoadInput.getFiles();

        //图谱id
        String kngraphId = upLoadInput.getKngraphId();

        for (MultipartFile multipartFile : files) {

            //文件后缀
            String extensionName = FileUpload.getExtensionName(multipartFile.getOriginalFilename());

            /**
             * 遍历服务
             */
            for (ExcelInterface excelInterface : interfaces) {

                //判断服务是否支持
                if (excelInterface.support(extensionName, upLoadInput.getGraphType())) {

                    excelInterface.excute(multipartFile, kngraphId, upLoadInput.getType(), upLoadInput.getGraphType());

                }
            }
        }

        return "success";
    }
}
