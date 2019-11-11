package top.wikl.entity.graph.input.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XYL
 * @title: ToUpperCaseInputParamVo
 * @description: toUpperCase 的查询参数
 * @date 2019/10/12 16:05
 * @return
 * @since V1.0
 */
@ApiModel(value = "toUpperCase 的查询参数", description = "toUpperCase 的查询参数")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ToUpperCaseInputParamVo {

    /**
     * 字段
     */
    @ApiModelProperty(value = "字段", dataType = "String")
    private String field;

}
