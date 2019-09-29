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
 * 关系对象
 * edges": [
 * {"data": {"relationship": "ACTED_IN", "source": "174", "target": "327"}},
 * {"data": {"relationship": "ACTED_IN", "source": "174", "target": "273"}},
 * ]
 *
 * @param
 * @author XYL
 * @date 2019/9/27 13:06
 * @return
 * @since V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "关系对象")
@Data
public class WiklRelationInfo implements Serializable {

    /**
     * 关系id
     */
    @ApiModelProperty(value = "关系id")
    private String id;

    /**
     * 关系信息
     */
    @ApiModelProperty(value = "关系信息")
    private String text;

    /**
     * 关系起点
     */
    @ApiModelProperty(value = "关系起点")
    private String source;

    /**
     * 关系终点
     */
    @ApiModelProperty(value = "关系终点")
    private String target;

    /**
     * 关系label
     */
    @ApiModelProperty(value = "关系label")
    private String label;

    /**
     * 关系类型
     */
    @ApiModelProperty(value = "关系类型")
    private String type;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    /**
     * 其他数据，eg 单个属性值
     */
    private Map<String, Object> others;

    /**
     * 重写equals,用于比较对象属性是否包含
     *
     * @param obj
     * @return boolean
     * @author XiaYaLing
     * @date 2018/4/17
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        WiklRelationInfo relationship = (WiklRelationInfo) obj;
        //多重逻辑处理
        if (this.getSource().compareTo(relationship.getSource()) == 0
                && this.getTarget().equals(relationship.getTarget())
                && this.getLabel().compareTo(relationship.getLabel()) == 0) {
            return true;
        }
        return false;
    }
}
