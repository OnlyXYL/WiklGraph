package top.wikl.orientdb.error.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * 实例点信息
 * @param
 * @author XYL
 * @date 2020/3/21 17:53
 * @return
 * @since  V2.0
 */
@Data
public class KgInstanceNode {

    @ApiModelProperty("唯一属性值")
    private String id;

    @ApiModelProperty("rid")
    private String rid;

    @ApiModelProperty("实例节点标签")
    private String label;

    @ApiModelProperty("属性")
    private Map<String, Object> properties;

}
