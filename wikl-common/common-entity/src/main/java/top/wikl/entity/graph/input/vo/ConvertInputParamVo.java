package top.wikl.entity.graph.input.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XYL
 * @title: ConvertInputParamVo
 * @description: OrientDb 的方法：Convert 的查询参数
 * @date 2019/10/10 15:08
 * @return
 * @since V1.0
 */
@ApiModel(value = "Convert 的查询参数", description = "Convert 的查询参数")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConvertInputParamVo {

    /**
     * 字段
     */
    @ApiModelProperty(value = "字段", notes = "字段", dataType = "String")
    private String field;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", notes = "类型", dataType = "String")
    private String type;
}
