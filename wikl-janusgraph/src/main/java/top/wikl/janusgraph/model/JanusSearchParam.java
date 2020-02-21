package top.wikl.janusgraph.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author XYL
 * @title: JanusSearchParam
 * @description: TODO
 * @date 2019/12/31 16:35
 * @return
 * @since V1.0
 */
@Data
public class JanusSearchParam implements Serializable {

    /**
     * 节点label
     */
    private String label;

    /**
     * 查询条件
     */
    private Map<String, String> conditions;
}
