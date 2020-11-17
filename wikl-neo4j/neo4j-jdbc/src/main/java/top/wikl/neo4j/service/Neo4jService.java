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
     * @param params
     * @return void
     * @apiNote 新增单个点
     * @author XYL
     * @since 13:55 2020/10/14
     **/
    void createNode(Map<String, Object> params);

    /**
     * @param nodes
     * @return void
     * @apiNote 批量新增点
     * @author XYL
     * @since 13:55 2020/10/14
     **/
    void createNodesUnwind(List<Map<String,Object>> nodes,String label);

    /**
     * @param params
     * @return void
     * @apiNote 新增关系
     * @author XYL
     * @since 13:55 2020/10/14
     **/
    void createRelationShip(Map<String, Object> params);

    /**
     * 创建约束
     *
     * @param constraint_name
     * @param label
     * @return void
     * @author XYL
     * @since 19:50 2020/10/20
     **/
    void createConstraint(String constraint_name,String label);

    void createBatch();
}
