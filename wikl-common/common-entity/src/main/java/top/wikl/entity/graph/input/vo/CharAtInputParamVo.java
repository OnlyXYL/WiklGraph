package top.wikl.entity.graph.input.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XYL
 * @title: CharAtInputParamVo
 * @description: charAt 的查询参数
 * @date 2019/10/11 9:50
 * @return
 * @since V1.0
 */
@ApiModel(value = "charAt 的查询参数", description = "charAt 的查询参数")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CharAtInputParamVo {

    /**
     * 字段
     */
    @ApiModelProperty(value = "字段", dataType = "String")
    private String field;

    /**
     * 元素下标
     */
    @ApiModelProperty(value = "元素下标", notes = "元素下标", dataType = "Integer")
    private Integer position;
}
