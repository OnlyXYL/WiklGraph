package top.wikl.orientdb.utils;

import io.swagger.annotations.Api;

@Api("获取图数据库使用的标签")
public final class WiklGraphLabelUtils {

    final private static String CONCEPT_EDGE_PREFIX = "ConceptEdge";         // 概念关系前缀
    final private static String CONCEPT_PREFIX = "Concept";                 // 概念节点前缀
    final private static String INSTANCE_EDGE_PREFIX = "InstanceEdge";       // 实例边 class 前缀
    final private static String INSTANCE_PREFIX = "Instance";               // 实例点 class 前缀


    /**
     * 功能描述: 获取概念边的class
     *
     * @param graphId 图谱id
     * @return java.lang.String
     * @author gaokai
     * @date 2019/10/31 15:37
     */
    public static String getConceptEdgeSchema(Integer graphId) {
        return CONCEPT_EDGE_PREFIX + graphId;
    }

    /**
     * 功能描述: 获取概念点的class
     *
     * @param graphId 图谱id
     * @return java.lang.String
     * @author gaokai
     * @date 2019/10/31 15:37
     */
    public static String getConceptSchema(Integer graphId) {
        return CONCEPT_PREFIX + graphId;
    }

    /**
     * 功能描述: 获取实例点class的父类
     *
     * @param graphId 图谱id
     * @return java.lang.String
     * @author gaokai
     * @date 2019/10/31 15:38
     */
    public static String getInstanceSchema(Integer graphId) {
        return INSTANCE_PREFIX + graphId;
    }

    /**
     * 功能描述: 获取实例边class的父类
     *
     * @param graphId 图谱id
     * @return java.lang.String
     * @author gaokai
     * @date 2019/10/31 15:39
     */
    public static String getInstanceEdgeSchema(Integer graphId) {
        return INSTANCE_EDGE_PREFIX + graphId;
    }
}
