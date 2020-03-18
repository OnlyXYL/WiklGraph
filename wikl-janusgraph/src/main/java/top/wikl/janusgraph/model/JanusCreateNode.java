package top.wikl.janusgraph.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author XYL
 * @title: JanusCreateNode
 * @description: TODO
 * @date 2019/12/31 16:30
 * @return
 * @since V1.0
 */
@Data
public class JanusCreateNode {

    /**
     * label
     */
    private String label;

    private List<String> property;

    /**
     * 属性，值
     */
    private Map<String, String> properties;
}
