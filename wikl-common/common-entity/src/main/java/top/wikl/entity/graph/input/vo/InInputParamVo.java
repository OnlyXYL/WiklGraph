package top.wikl.entity.graph.input.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XYL
 * @title: InInputParamVo
 * @description: in() 的查询参数
 * @date 2019/10/14 10:42
 * @return
 * @since V1.0
 */
@ApiModel(value = "in() 的查询参数", description = "in() 的查询参数")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InInputParamVo {

    /**
     * Label类型
     */
    @ApiModelProperty(value = "Label类型", notes = "Label类型", dataType = "String")
    private String labelType;
}
