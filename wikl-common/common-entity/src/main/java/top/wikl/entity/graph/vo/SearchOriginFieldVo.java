package top.wikl.entity.graph.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据实例属性获取概念中对应的中文属性
 *
 * @param
 * @author XYL
 * @date 2019/9/27 14:16
 * @return
 * @since V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "根据实例属性获取概念中对应的中文属性")
@Data
public class SearchOriginFieldVo {

    /**
     * 图谱id
     */
    @ApiModelProperty(value = "图谱id")
    private String kngraphId;

    /**
     * 属性字段
     */
    @ApiModelProperty(value = "属性字段")
    private String field;

    /**
     * 节点名称
     */
    @ApiModelProperty(value = "节点名称")
    private String name;

    /**
     * 是否是节点
     */
    @ApiModelProperty(value = "是否是节点")
    private boolean node;
}
