package top.wikl.entity.graph.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 概念图谱返回结果
 *
 * @param
 * @author XYL
 * @date 2019/9/27 13:11
 * @return
 * @since V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "概念图谱返回结果")
public class WiklConceptResultData implements Serializable {

    /**
     * 节点信息
     */
    @ApiModelProperty(value = "节点信息")
    private List<WiklNodeInfo> nodes;

    /**
     * 线信息
     */
    @ApiModelProperty(value = "线信息")
    private List<WiklRelationInfo> edges;

    /**
     * 搜索框中，起点的 label，没有时为空
     */
    @ApiModelProperty(value = "搜索框中，起点的 label，没有时为空")
    private String startLabel = "";

    /**
     * 搜索框中，终点的 label,没有时为空
     */
    @ApiModelProperty(value = "搜索框中，终点的 label,没有时为空")
    private String endLabel = "";

}
