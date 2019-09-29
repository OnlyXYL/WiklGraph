package top.wikl.entity.graph.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询线详情
 *
 * @param
 * @author XYL
 * @date 2019/9/27 14:04
 * @return
 * @since V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "查询线详情")
public class SearchInstanceEdgeDetailsVo {

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
     * 对应概念节点名称
     */
    @ApiModelProperty(value = "对应概念节点名称")
    private String conceptName;

}
