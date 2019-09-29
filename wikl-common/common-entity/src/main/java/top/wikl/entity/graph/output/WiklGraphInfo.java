package top.wikl.entity.graph.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图谱信息（实例个数，关系个数）
 *
 * @param
 * @author XYL
 * @date 2019/9/27 14:05
 * @return
 * @since V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "图谱信息（实例个数，关系个数）")
@Data
public class WiklGraphInfo {

    /**
     * 图谱实例个数
     */
    @ApiModelProperty(value = "图谱实例个数")
    private long instanceNumber = 0;

    /**
     * 图谱关系个数
     */
    @ApiModelProperty(value = "图谱关系个数")
    private long edgeNumber = 0;

}
