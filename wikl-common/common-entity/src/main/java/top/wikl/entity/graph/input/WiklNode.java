package top.wikl.entity.graph.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.wikl.entity.graph.fusion.PropertyValue;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 图谱节点信息
 *
 * @param
 * @author XYL
 * @date 2019/9/27 13:47
 * @return
 * @since V1.0
 */
@ApiModel(value = "图谱节点信息")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Slf4j
public class WiklNode {

    /**
     * 节点id
     */
    @ApiModelProperty(value = "节点id")
    private String id;

    /**
     * 节点名称
     */
    @ApiModelProperty(value = "节点名称")
    private String nodeName;

    /**
     * 节点类型
     */
    @ApiModelProperty(value = "节点类型")
    private String type;

    /**
     * 节点label
     */
    @ApiModelProperty(value = "节点label")
    private String label;

    /**
     * 节点来源
     */
    @ApiModelProperty(value = "节点来源")
    private List<PropertyValue> comeFrom;

    /**
     * 节点属性
     */
    @ApiModelProperty(value = "节点属性")
    private Map<String, Object> propertyMap;

    private long x;

    private long y;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof WiklNode)) {
            return false;
        }
        WiklNode wiklNode = (WiklNode) object;
        return Objects.equals(nodeName, wiklNode.nodeName) &&
                Objects.equals(type, wiklNode.type) &&
                Objects.equals(label, wiklNode.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeName, type, label);
    }
}
