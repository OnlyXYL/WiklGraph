package top.wikl.entity.graph.input;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.wikl.entity.graph.fusion.PropertyValue;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 图谱关系
 *
 * @param
 * @author XYL
 * @date 2019/9/27 13:21
 * @return
 * @since V1.0
 */
@ApiModel(value = "图谱关系")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
public class WiklRelation {

    /**
     * 关系起点
     */
    private WiklRelationNode start;

    //关系终点

    /**
     * 关系起点
     */
    private WiklRelationNode end;

    /**
     * 关系标签
     */
    private String label;

    /**
     * 关系名
     */
    private String relationName;

    /**
     * 关系类型
     */
    private String type;

    /**
     * 关系来源
     */
    private List<PropertyValue> comeFrom;

    /**
     * 关系属性
     */
    private Map<String, Object> propertyMap;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof WiklRelation)) {
            return false;
        }
        WiklRelation relation = (WiklRelation) object;
        return Objects.equals(start, relation.start) &&
                Objects.equals(end, relation.end) &&
                Objects.equals(label, relation.label) &&
                Objects.equals(relationName, relation.relationName) &&
                Objects.equals(type, relation.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, label, relationName, type);
    }
}
