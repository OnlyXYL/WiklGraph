package top.wikl.entity.file;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author XYL
 * @title: UpLoadInput
 * @description: 图谱构建文件上传参数类
 * @date 2019/10/23 9:57
 * @return
 * @since V1.0
 */
@ApiModel(value = "图谱构建文件上传参数类",description = "图谱构建文件上传参数类")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpLoadInput {

    /**
     * 文件
     */
    @ApiModelProperty(value = "文件")
    private MultipartFile[] files;

    /**
     * 图谱id
     */
    @ApiModelProperty(value = "图谱id")
    private String kngraphId;

    /**
     * 操作类型
     * 0:校验  1 上传
     */
    @ApiModelProperty(value = "操作类型")
    private String type;

    /**
     * 图谱类型  {@link cn.com.bmsmart.enums.GraphType}
     */
    @ApiModelProperty(value = "图谱类型")
    private String graphType;
}
