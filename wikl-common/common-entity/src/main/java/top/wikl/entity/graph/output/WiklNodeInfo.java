package top.wikl.entity.graph.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
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
     * 节点label
     */
    @ApiModelProperty(value = "节点label")
    private String label;

    /**
     * 节点所属实例label
     */
    @ApiModelProperty(value = "节点所属实例label")
    private String instanceLabel;

    /**
     * 是否是父节点
     */
    @ApiModelProperty(value = "是否是父节点")
    private boolean parentNode;

    private long nodeSize = 1;

    private long nodeColor;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    /**
     * 其他数据，eg 单个属性值
     */
    private Map<String, Object> others;

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
