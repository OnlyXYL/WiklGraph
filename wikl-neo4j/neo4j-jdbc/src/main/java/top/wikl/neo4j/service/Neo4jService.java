package top.wikl.neo4j.service;

import top.wikl.entity.graph.input.InInput;
import top.wikl.entity.graph.output.WiklNodeInfo;
import top.wikl.entity.graph.output.WiklResultData;

import java.util.List;
import java.util.Map;

/**
 * @author XYL
 * @title: Neo4jService
 * @description: TODO
 * @date 2019/10/30 19:55
 * @return
 * @since V1.0
 */
public interface Neo4jService {

    /**
     * 查询单个点
     *
     * @param inInput
     * @return
     * @author XYL
     * @date 2019/10/31 11:09
     * @since V1.0
     */
    List<WiklNodeInfo> searchOneNode(InInput inInput);

    /**
     * 根据深度查询图谱数据
     *
     * @param inInput
     * @return
     * @author XYL
     * @date 2019/11/11 11:52
     * @since V1.0
     */
    WiklResultData searchByDepth(InInput inInput);

    /**
     * 创建点
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/8/23 14:04
     * @since V1.2
     */
    void createNode(Map<String, Object> params);

    /**
     * 批量创建
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/8/23 14:36
     * @since V1.2
     */
    void createNodes(List<Map> start,List<Map> end);

    /**
     * 创建关系
     * @param
     * @throws
     * @author XYL
     * @date 2020/8/23 14:49
     * @return
     * @since  V1.2
     */
    void createRelationShip(Map<String,Object> params);
}
