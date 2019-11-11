package top.wikl.entity.graph.input.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XYL
 * @title: RightInputParamVo
 * @description: Right 的查询参数
 * @date 2019/10/11 18:33
 * @return
 * @since V1.0
 */
@ApiModel(value = "Right 的查询参数", description = "Right 的查询参数")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RightInputParamVo {

    /**
     * 字段
     */
    @ApiModelProperty(value = "字段", dataType = "String")
    private String field;

    /**
     * 字符串中指定的长度
     */
    @ApiModelProperty(value = "字符串中指定的长度", notes = "字符串中指定的长度", dataType = "String")
    private Integer length;
}
