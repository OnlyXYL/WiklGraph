package top.wikl.entity.graph.input.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XYL
 * @title: BothInputParamVo
 * @description: both() 的查询参数
 * @date 2019/10/14 10:47
 * @return
 * @since V1.0
 */
@ApiModel(value = "both() 的查询参数", description = "both() 的查询参数")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BothInputParamVo {

    /**
     * 关系类型
     */
    @ApiModelProperty(value = "关系类型", notes = "关系类型", dataType = "String")
    private String relationType;
}
