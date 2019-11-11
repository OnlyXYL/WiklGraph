package top.wikl.entity.graph.input.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XYL
 * @title: IndexOfInputParamVo
 * @description: IndexOf 的查询参数
 * @date 2019/10/11 18:02
 * @return
 * @since V1.0
 */
@ApiModel(value = "IndexOf 的查询参数", description = "IndexOf 的查询参数")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IndexOfInputParamVo {

    /**
     * 字段
     */
    @ApiModelProperty(value = "字段", dataType = "String")
    private String field;

    /**
     * 字符：字段对应值中的字符
     */
    @ApiModelProperty(value = "字段对应值中的字符", notes = "字段对应值中的字符", dataType = "String")
    private String character;

}
