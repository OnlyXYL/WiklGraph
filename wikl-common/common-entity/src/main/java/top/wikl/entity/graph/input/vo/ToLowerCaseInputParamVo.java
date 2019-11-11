package top.wikl.entity.graph.input.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XYL
 * @title: ToLowerCaseInputParamVo
 * @description: toLowerCase 的查询参数
 * @date 2019/10/12 15:58
 * @return
 * @since V1.0
 */
@ApiModel(value = "toLowerCase 的查询参数", description = "toLowerCase 的查询参数")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ToLowerCaseInputParamVo {

    /**
     * 字段
     */
    @ApiModelProperty(value = "字段", dataType = "String")
    private String field;

}
