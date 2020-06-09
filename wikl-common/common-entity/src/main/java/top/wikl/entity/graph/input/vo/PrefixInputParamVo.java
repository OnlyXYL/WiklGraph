package top.wikl.entity.graph.input.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XYL
 * @title: PrefixInputParamVo
 * @description: Prefix 的查询参数
 * @date 2019/10/12 15:14
 * @return
 * @since V1.0
 */
@ApiModel(value = "Prefix 的查询参数", description = "Prefix 的查询参数")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrefixInputParamVo {

    /**
     * 字段
     */
    @ApiModelProperty(value = "字段", dataType = "String")
    private String field;

    /**
     * 最为前缀的字符串
     */
    @ApiModelProperty(value = "最为前缀的字符串", notes = "最为前缀的字符串", dataType = "String")
    private String prefix;
}
