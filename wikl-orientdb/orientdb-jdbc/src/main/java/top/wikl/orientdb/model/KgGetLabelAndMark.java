package top.wikl.orientdb.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "KgGetLabelAndMark",description = "实例标签和显示属性")
public class KgGetLabelAndMark {

    @ApiModelProperty("实例标签")
    private String label;

    @ApiModelProperty("显示属性")
    private String markProperty;

    @ApiModelProperty("实例颜色")
    private Integer nodeColor;

    @ApiModelProperty("实例大小")
    private Integer nodeSize;
}
