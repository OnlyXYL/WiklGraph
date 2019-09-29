package top.wikl.entity.graph.input;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.wikl.entity.graph.fusion.PropertyValue;

import java.util.List;

/**
 * 图谱关系节点
 *
 * @param
 * @author XYL
 * @date 2019/9/27 13:22
 * @return
 * @since V1.0
 */
@ApiModel(value = "图谱关系节点")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Slf4j
public class WiklRelationNode {

    private String label;

    private String name;

    /**
     * 关系中节点来源
     */
    private List<PropertyValue> comeFrom;

    private long x;

    private long y;
}
