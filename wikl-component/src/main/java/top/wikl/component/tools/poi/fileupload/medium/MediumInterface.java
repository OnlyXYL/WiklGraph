package top.wikl.component.tools.poi.fileupload.medium;

import top.wikl.entity.file.UpLoadInput;

/**
 * @author XYL
 * @title: MediumInterface
 * @description: 【文件上传】文件媒介服务
 * @date 2019/10/22 17:51
 * @return
 * @since V1.0
 */
public interface MediumInterface {

    /**
     * 判断是否支持
     *
     * @param medium 媒介
     * @return
     * @author XYL
     * @date 2019/10/22 17:59
     * @since V1.0
     */
    boolean support(String medium);

    /**
     * 执行逻辑
     *
     * @param upLoadInput
     * @return
     * @author XYL
     * @date 2019/10/22 17:59
     * @since V1.0
     */
    String excute(UpLoadInput upLoadInput);
}
