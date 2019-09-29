package top.wikl.entity.graph.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 根据实例属性获取概念中对应的中文属性结果对象
 *
 * @param
 * @author XYL
 * @date 2019/9/27 14:17
 * @return
 * @since V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "根据实例属性获取概念中对应的中文属性结果对象")
@Data
public class OriginFieldResultData {

    /**
     * 原始字段
     */
    @ApiModelProperty(value = "原始字段")
    private String originField;

    /**
     * 属性原始字段和真实字段map
     * <p>
     * <原始字段，真实字段>
     */
    @ApiModelProperty(value = "属性原始字段和真实字段map")
    private Map<String, String> propertyKeyMap;

}
