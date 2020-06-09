package top.wikl.component.tools.poi.fileupload.file.excel;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author XYL
 * @title: ExcelInterface
 * @description: excel方式
 * @date 2019/10/22 17:31
 * @return
 * @since V1.0
 */
public interface ExcelInterface {

    /**
     * 判断是否支持
     *
     * @param type      类型
     * @param graphType
     * @return
     * @author XYL
     * @date 2019/10/22 17:41
     * @since V1.0
     */
    boolean support(String type, String graphType);

    /**
     * 校验文件规则
     * <p>
     * 1）	导入格式：xls、xlsx、csv
     * 2）	单词导入最多5个文档
     * 3）	单个文档内sheet页数量不超过10个
     * 4）	单个导入文档不得超过200个表头
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/10/22 17:43
     * @since V1.0
     */
    boolean checkRule(MultipartFile multipartFile, String graphType);

    /**
     * 校验数据
     *
     * @param multipartFile
     * @param kngraphId
     * @return
     * @author XYL
     * @date 2019/10/23 17:03
     * @since V1.0
     */
    void checkData(MultipartFile multipartFile, String kngraphId);

    /**
     * 数据处理服务
     *
     * @param kngraphId   图谱id
     * @param file        文件
     * @param operateType 0：校验  1：上传
     * @param graphType   1: 概念 2：实例
     * @return
     * @author XYL
     * @date 2019/10/22 17:44
     * @since V1.0
     */
    String excute(MultipartFile file, String kngraphId, String operateType, String graphType);


}
