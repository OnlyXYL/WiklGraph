package top.wikl.entity.graph.input.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XYL
 * @title: ReplaceInputParamVo
 * @description: replace 的查询参数
 * @date 2019/10/12 15:26
 * @return
 * @since V1.0
 */
@ApiModel(value = "replace 的查询参数", description = "replace 的查询参数")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReplaceInputParamVo {

    /**
     * 字段
     */
    @ApiModelProperty(value = "字段", dataType = "String")
    private String field;

    /**
     * 原始字符
     */
    @ApiModelProperty(value = "原始字符", notes = "原始字符", dataType = "String")
    private String originString;

    /**
     * 指定字符
     */
    @ApiModelProperty(value = "指定字符", notes = "指定字符", dataType = "String")
    private String replace;
}
