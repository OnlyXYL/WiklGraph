package top.wikl.component.tools.poi.fileupload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.wikl.component.tools.poi.fileupload.medium.MediumInterface;
import top.wikl.entity.file.UpLoadInput;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author XYL
 * @title: LocalUploadInterface
 * @description: 【图谱构建】本地文件上传服务入口
 * @date 2019/10/22 16:53
 * @return
 * @since V1.0
 */
@Slf4j
public class LocalUploadInterface {

    /**
     * 媒介服务
     */
    private List<MediumInterface> mediumInterface;

    /**
     * 文件上传服务
     *
     * @param upLoadInput
     * @return
     */
    public String excute(UpLoadInput upLoadInput) {

        //遍历服务
        for (MediumInterface mediumInterface : mediumInterface) {

            if (mediumInterface.support("")) {
                return mediumInterface.excute(upLoadInput);
            }
        }

        return null;
    }

}
