package top.wikl.entity.graph.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 根据点，查询深度为1 的关系
 *
 * @param
 * @author XYL
 * @date 2019/9/27 14:06
 * @return
 * @since V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "根据点，查询深度为1 的关系")
public class SearchOneDepthDataVo implements Serializable {
    /**
     * 业务图谱id
     */
    @ApiModelProperty(value = "业务图谱id")
    private String kngraphId;

    /**
     * 节点id
     */
    @ApiModelProperty(value = "实例节点Id")
    private String id;

    /**
     * 节点label
     */
    @ApiModelProperty(value = "节点label")
    private String label;

    /**
     * 查询个数
     */
    @ApiModelProperty(value = "查询个数")
    private int limit;
}
