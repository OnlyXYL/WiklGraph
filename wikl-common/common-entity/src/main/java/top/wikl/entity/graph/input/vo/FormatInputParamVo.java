package top.wikl.entity.graph.input.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XYL
 * @title: FormatInputParamVo
 * @description: format 的查询参数
 * @date 2019/10/12 16:26
 * @return
 * @since V1.0
 */
@ApiModel(value = "format 的查询参数", description = "format 的查询参数")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormatInputParamVo {
    /**
     * 字段
     */
    @ApiModelProperty(value = "字段", dataType = "String")
    private String field;

    /**
     * 格式化规则
     */
    @ApiModelProperty(value = "格式化规则", notes = "格式化规则", dataType = "String")
    private String rule;
}
