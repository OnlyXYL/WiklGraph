package top.wikl.entity.graph.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.wikl.entity.graph.input.vo.PrefixInputParamVo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author XYL
 * @title: PrefixInput
 * @description: The Input of Prefix
 * @date 2019/10/12 15:13
 * @return
 * @since V1.0
 */
@ApiModel(value = "The Input of Prefix")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrefixInput {

    /**
     * Prefix 输入参数
     * <value, type></value>
     */
    @ApiModelProperty(value = "输入参数", dataType = "List")
    private List<PrefixInputParamVo> input;

    /**
     * 需要搜索的节点对应的class
     */
    @NotNull(message = "需要搜索的节点对应的class不能为空")
    @ApiModelProperty(value = "需要搜索的节点对应的class", required = true, dataType = "String")
    private String label;

    /**
     * 查询条件
     * <key,value></>
     */
    @ApiModelProperty(value = "查询条件", dataType = "Map")
    private Map<String, String> conditions;

    /**
     * 查询个数
     */
    @Min(value = 1, message = "查询个数必须大于0")
    @ApiModelProperty(name = "limit", value = "查询个数", notes = "查询个数", dataType = "String")
    private Integer limit = 1;
}
