package top.wikl.entity.graph.input.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XYL
 * @title: AppendInputParamVo
 * @description: Append 的查询参数
 * @date 2019/10/10 17:31
 * @return
 * @since V1.0
 */
@ApiModel(value = "Append 的查询参数", description = "Append 的查询参数")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppendInputParamVo {

    /**
     * 字段
     */
    @ApiModelProperty(value = "字段", dataType = "String")
    private String field;

    /**
     * 拼接的字符串
     */
    @ApiModelProperty(value = "拼接的字符串", notes = "拼接的字符串", dataType = "String")
    private String appendStr;
}
