package top.wikl.entity.graph.input.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XYL
 * @title: SubStringInputParamVo
 * @description: substring 的查询参数
 * @date 2019/10/12 15:49
 * @return
 * @since V1.0
 */
@ApiModel(value = "substring 的查询参数", description = "substring 的查询参数")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubStringInputParamVo {

    /**
     * 字段
     */
    @ApiModelProperty(value = "字段", dataType = "String")
    private String field;

    /**
     * 开始索引
     */
    @ApiModelProperty(value = "开始索引", notes = "开始索引", dataType = "String")
    private Integer startIndex;

    /**
     * 结束索引
     */
    @ApiModelProperty(value = "结束索引", notes = "结束索引", dataType = "String")
    private Integer endIndex;
}
