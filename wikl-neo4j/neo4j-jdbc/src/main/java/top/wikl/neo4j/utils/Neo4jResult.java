package top.wikl.neo4j.utils;

import top.wikl.neo4j.entity.Neo4jType;
import top.wikl.neo4j.entity.Node;
import top.wikl.neo4j.entity.Value;
import top.wikl.neo4j.entity.result.Result;
import top.wikl.neo4j.entity.result.ResultEnum;

import java.util.*;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/4/1 0001 22:33
 */
public class Neo4jResult {

    /**
     * 概念字典
     *
     * @param rs
     * @return java.util.Set<java.lang.String>
     * @author XYL
     * @since 22:34 2021/4/1 0001
     **/
    public static Set<String> conceptDic(Result<List<Map<String, Value>>> rs) {

        Set<String> set = new HashSet<>();

        List<Map<String, Value>> data = rs.getData();

        if (ResultEnum.SUCCESS.equals(rs.getStatus()) && !data.isEmpty()) {

            data.forEach(x -> {

                x.forEach((k, v) -> {

                    final String conceptName = v.asString();

                    set.add(conceptName + " " + conceptName);
                });
            });
        }

        return set;
    }

    /**
     * 概念图谱字典
     *
     * @param rs
     * @return java.util.Set<java.lang.String>
     * @author XYL
     * @since 22:39 2021/4/1 0001
     **/
    public static Set<String> conceptGraphDic(Result<List<Map<String, Value>>> rs, Integer graphId) {

        Set<String> set = new HashSet<>();

        List<Map<String, Value>> data = rs.getData();

        if (ResultEnum.SUCCESS.equals(rs.getStatus()) && !data.isEmpty()) {

            data.forEach(x -> {

                x.forEach((k, v) -> {

                    final String conceptName = v.asString();

                    set.add(conceptName + " " + graphId);
                });
            });
        }

        return set;
    }

    /**
     * 实例概念
     *
     * @param rs
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author XYL
     * @since 22:45 2021/4/1 0001
     **/
    public static Map<String, String> labelConcept(Result<List<Map<String, Value>>> rs) {

        Map<String, String> label_conceptName = new HashMap<>();

        List<Map<String, Value>> data = rs.getData();

        if (ResultEnum.SUCCESS.equals(rs.getStatus()) && !data.isEmpty()) {
            data.forEach(x -> x.forEach((k, v) -> {
                String type = v.getType();
                if (Neo4jType.NODE.equals(type)) {

                    Node node = v.asNode();

                    final Map<String, Object> properties = node.getProperties();

                    final String conceptName = properties.get("name").toString();

                    final String instanceLabel = properties.get("instanceLabel").toString();

                    label_conceptName.put(instanceLabel, conceptName);
                }
            }));
        }

        return label_conceptName;
    }

    /**
     * 实体概念字典
     *
     * @param rs
     * @param label_conceptName
     * @return java.util.Set<java.lang.String>
     * @author XYL
     * @since 22:48 2021/4/1 0001
     **/
    public static Set<String> entityConceptDic(Result<List<Map<String, Value>>> rs, Map<String, String> label_conceptName, Map<String, String> labelMarkMap) {

        Set<String> set = new HashSet<>();

        List<Map<String, Value>> data = rs.getData();

        if (ResultEnum.SUCCESS.equals(rs.getStatus()) && !data.isEmpty()) {
            data.forEach(x -> x.forEach((k, v) -> {
                String type = v.getType();
                if (Neo4jType.NODE.equals(type)) {

                    Node node = v.asNode();

                    final Map<String, Object> properties = node.getProperties();

                    final String instanceLabel = properties.get("label").toString();

                    //显示属性key
                    String markPro = labelMarkMap.get(instanceLabel);

                    //显示属性值
                    String markProValue = properties.get(markPro).toString();

                    set.add(markProValue + " " + label_conceptName.get(instanceLabel));
                }
            }));
        }
        return set;
    }

    /**
     * 实体字典
     *
     * @param rs
     * @return java.util.Set<java.lang.String>
     * @author XYL
     * @since 22:53 2021/4/1 0001
     **/
    public static Set<String> entityDic(Result<List<Map<String, Value>>> rs, Map<String, String> labelMarkMap) {

        Set<String> set = new HashSet<>();

        List<Map<String, Value>> data = rs.getData();

        if (ResultEnum.SUCCESS.equals(rs.getStatus()) && !data.isEmpty()) {
            data.forEach(x -> x.forEach((k, v) -> {
                String type = v.getType();
                if (Neo4jType.NODE.equals(type)) {

                    Node node = v.asNode();

                    final Map<String, Object> properties = node.getProperties();

                    final String instanceLabel = properties.get("label").toString();

                    //显示属性key
                    String markPro = labelMarkMap.get(instanceLabel);

                    //显示属性值
                    String markProValue = properties.get(markPro).toString();

                    set.add(markProValue + " " + markProValue);
                }
            }));
        }
        return set;
    }

    /**
     * 属性概念字典
     *
     * @param rs
     * @param labelMarkMap
     * @return java.util.Set<java.lang.String>
     * @author XYL
     * @since 22:59 2021/4/1 0001
     **/
    public static Set<String> propertyConceptDic(Result<List<Map<String, Value>>> rs, Map<String, String> labelMarkMap) {
        Set<String> set = new HashSet<>();

        List<Map<String, Value>> data = rs.getData();

        if (ResultEnum.SUCCESS.equals(rs.getStatus()) && !data.isEmpty()) {
            data.forEach(x -> x.forEach((k, v) -> {
                String type = v.getType();
                if (Neo4jType.NODE.equals(type)) {

                    Node node = v.asNode();

                    final Map<String, Object> properties = node.getProperties();

                    final String instanceLabel = properties.get("label").toString();

                    final String conceptName = properties.get("name").toString();

                    //显示属性key
                    String markPro = labelMarkMap.get(instanceLabel);

                    properties.forEach((key, value) -> {

                        if (key.startsWith("p_") && !key.equals(markPro)) {

                            set.add(value + " " + conceptName);
                        }
                    });
                }
            }));
        }
        return set;
    }

    /**
     * 属性字典
     *
     * @param rs
     * @param labelMarkMap
     * @return java.util.Set<java.lang.String>
     * @author XYL
     * @since 23:00 2021/4/1 0001
     **/
    public static Set<String> propertyDic(Result<List<Map<String, Value>>> rs, Map<String, String> labelMarkMap) {
        Set<String> set = new HashSet<>();

        List<Map<String, Value>> data = rs.getData();

        if (ResultEnum.SUCCESS.equals(rs.getStatus()) && !data.isEmpty()) {
            data.forEach(x -> x.forEach((k, v) -> {
                String type = v.getType();
                if (Neo4jType.NODE.equals(type)) {

                    Node node = v.asNode();

                    final Map<String, Object> properties = node.getProperties();

                    final String instanceLabel = properties.get("label").toString();

                    //显示属性key
                    String markPro = labelMarkMap.get(instanceLabel);

                    properties.forEach((key, value) -> {

                        if (key.startsWith("p_") && !key.equals(markPro)) {

                            set.add(value + " " + value);
                        }
                    });
                }
            }));
        }
        return set;
    }
}
