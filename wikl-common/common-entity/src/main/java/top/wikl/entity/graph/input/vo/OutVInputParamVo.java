package top.wikl.entity.graph.input.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XYL
 * @title: OutVInputParamVo
 * @description: outV() 的查询参数
 * @date 2019/10/14 17:15
 * @return
 * @since V1.0
 */
@ApiModel(value = "outV() 的查询参数", description = "outV() 的查询参数")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OutVInputParamVo {

    /**
     * 关系类型
     */
    @ApiModelProperty(value = "关系类型", notes = "关系类型", dataType = "String")
    private String type;
}
