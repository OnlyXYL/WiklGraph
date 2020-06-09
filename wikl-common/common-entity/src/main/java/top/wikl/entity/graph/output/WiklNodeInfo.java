package top.wikl.entity.graph.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 图谱节点返回数据
 *
 * @param
 * @author XYL
 * @date 2019/9/27 13:04
 * @return
 * @since V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "图谱节点返回数据")
@Data
public class WiklNodeInfo implements Serializable {

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点标签
     */
    private String label;

    /**
     * 实例节点标签
     */
    private String instanceLabel;

    private Integer nodeSize;

    private Long nodeColor;

    private Boolean parentNode;

    /**
     * labels
     */
    @ApiModelProperty(value = "标签集合")
    private List<String> labels;

    /**
     * 节点id
     */
    @ApiModelProperty(value = "节点id")
    private String id;

    /**
     * 属性集合
     */
    @ApiModelProperty(value = "属性集合")
    private Map<String, Object> properties;

    private Date createDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WiklNodeInfo node = (WiklNodeInfo) o;

        if (!id.equals(node.id)) {
            return false;
        }
        return id.equals(node.id);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
