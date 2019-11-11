package top.wikl.orientdb.utils;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.record.ODirection;
import com.orientechnologies.orient.core.record.OEdge;
import com.orientechnologies.orient.core.record.OElement;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.record.impl.OEdgeDocument;
import com.orientechnologies.orient.core.record.impl.OVertexDocument;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultInternal;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tinkerpop.gremlin.orientdb.OrientEdge;
import org.apache.tinkerpop.gremlin.orientdb.OrientVertex;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import top.wikl.constant.SysConstant;
import top.wikl.entity.graph.output.*;
import top.wikl.orientdb.enums.WiklGraphType;
import top.wikl.utils.date.WiklDateUtil;
import top.wikl.utils.list.WiklSortListUtil;
import top.wikl.utils.system.WiklGraphNodeUtils;

import java.util.*;

/**
 *
 * @param
 * @author XYL
 * @date 2019/9/27 13:46
 * @return
 * @since  V1.0
 */
@Slf4j
public class WiklGraphResultUtil {

    public static WiklResultData InitInstanceResult(OResultSet rs,  String label) {
        //结果对象
        WiklResultData resultData = new WiklResultData();

        //点集合
        List<WiklNodeInfo> nodes = new ArrayList<>();

        //线集合
        List<WiklRelationInfo> edges = new ArrayList<>();

        //单个属性结果集合
        Map<String, Object> others = new HashMap<>();

        while (rs.hasNext()) {
            OResult item = rs.next();

            //获取元素
            Optional<OElement> optional = item.getElement();

            //match 方式结果处理(结果在变量中)
            if (Optional.empty().equals(optional)) {

                OResultInternal oResultInternal = (OResultInternal) item;

                //获取返回结果key
                Set<String> propertyNames = oResultInternal.getPropertyNames();

                /**
                 * 由于无法返回的结果中，哪个字段代表关系，哪个代表点，因此，用 key ,分别去调用线的方法，点的方法，哪个有结果就说明当前 key 代表的数据是线还是点，再做针对的处理
                 */
                for (String name : propertyNames) {

                    Object property = oResultInternal.getProperty(name);

                    //根据 key 去查询线
                    OEdge oEdge = oResultInternal.getEdgeProperty(name);

                    //根据 key 去查询点
                    OVertex oVertex = oResultInternal.getVertexProperty(name);

                    //结果是点
                    if (!Objects.isNull(oVertex)) {

                        setNode(nodes, oVertex, label);

                    } else if (!Objects.isNull(oEdge)) {
                        //结果是线
                        //处理起点
                        setNode(nodes, oEdge.getFrom(), label);

                        //处理终点
                        setNode(nodes, oEdge.getTo(), label);

                        setRelationShip(edges, oEdge);

                    } else {

                        if (property instanceof OResultInternal) {

                            OResultInternal content = (OResultInternal) property;

                            //处理关系
                            WiklRelationInfo relationship = new WiklRelationInfo();

                            relationship.setLabel(content.getProperty("label").toString());
                            relationship.setSource(content.getProperty("source").toString());
                            relationship.setTarget(content.getProperty("target").toString());
                            relationship.setText(content.getProperty("text").toString());
                            relationship.setType(content.getProperty("type").toString());
                            relationship.setId(content.getProperty("id").toString());
                            edges.add(relationship);

                        }

                        //结果是单个属性
                        others.put(name, property);
                    }
                }
            }

            //结果不在变量中
            if (!Optional.empty().equals(optional)) {

                //select 方式查询结果处理
                Optional<OElement> element = item.getElement();

                OElement oElement = element.get();

                //判断元素是否是线
                boolean edge = oElement.isEdge();

                //元素是线
                if (edge) {
                    Optional<OEdge> optionalOEdge = oElement.asEdge();
                    OEdge oEdge = optionalOEdge.get();

                    setNode(nodes, oEdge.getFrom(), label);

                    //处理终点
                    setNode(nodes, oEdge.getTo(), label);

                    setRelationShip(edges, oEdge);
                }

                //判断元素是否是节点
                boolean vertex = oElement.isVertex();

                //元素是点
                if (vertex) {
                    Optional<OVertex> optionalOVertex = oElement.asVertex();

                    OVertex oVertex = optionalOVertex.get();

                    setNode(nodes, oVertex, label);

                }
            }

        }

        //节点去重
        nodes = RemoveRepeatDataUtil.removeDuplicateUser(nodes);

        //关系去重
        edges = RemoveRepeatDataUtil.RemoveRepeatRelationShipList(edges);

        resultData.setNodes(nodes);
        resultData.setEdges(edges);

        log.info("节点数：" + nodes.size() + "++++关系数：" + edges.size());

        return resultData;

    }


    /**
     * 处理单个字符串结果
     *
     * @param rs
     * @return
     */
    public static String dealSingleResult(OResultSet rs) {

        String result = "";

        while (rs.hasNext()) {
            OResult item = rs.next();

            //获取元素
            Optional<OElement> optional = item.getElement();

            //match 方式结果处理(结果在变量中)
            if (Optional.empty().equals(optional)) {

                OResultInternal oResultInternal = (OResultInternal) item;

                //获取返回结果key
                Set<String> propertyNames = oResultInternal.getPropertyNames();

                /**
                 * 由于无法返回的结果中，哪个字段代表关系，哪个代表点，因此，用 key ,分别去调用线的方法，点的方法，哪个有结果就说明当前 key 代表的数据是线还是点，再做针对的处理
                 */
                for (String name : propertyNames) {

                    result = oResultInternal.getProperty(name).toString();

                }
            }
        }

        return result;
    }


    public static WiklConceptResultData InitConceptResult(OResultSet rs, int limit, String startName, String endName, WiklConceptResultData resultData) {
        //点集合
        List<WiklNodeInfo> nodes = new ArrayList<>();

        //线集合
        List<WiklRelationInfo> edges = new ArrayList<>();

        //单个属性结果集合
        Map<String, Object> others = new HashMap<>();

        while (rs.hasNext()) {
            OResult item = rs.next();

            //获取元素
            Optional<OElement> optional = item.getElement();

            //match 方式结果处理(结果在变量中)
            if (Optional.empty().equals(optional)) {

                OResultInternal oResultInternal = (OResultInternal) item;

                //获取返回结果key
                Set<String> propertyNames = oResultInternal.getPropertyNames();

                /**
                 * 由于无法返回的结果中，哪个字段代表关系，哪个代表点，因此，用 key ,分别去调用线的方法，点的方法，哪个有结果就说明当前 key 代表的数据是线还是点，再做针对的处理
                 */
                for (String name : propertyNames) {

                    Object property = oResultInternal.getProperty(name);

                    //根据 key 去查询线
                    OEdge oEdge = oResultInternal.getEdgeProperty(name);

                    //根据 key 去查询点
                    OVertex oVertex = oResultInternal.getVertexProperty(name);

                    //结果是点
                    if (!Objects.isNull(oVertex)) {

                        setConceptNode(nodes, oVertex, startName, endName, resultData);

                    } else if (!Objects.isNull(oEdge)) {
                        //结果是线
                        //处理起点
                        setConceptNode(nodes, oEdge.getFrom(), startName, endName, resultData);

                        //处理终点
                        setConceptNode(nodes, oEdge.getTo(), startName, endName, resultData);

                        setConceptRelationShip(edges, oEdge);

                    } else {

                        if (property instanceof OResultInternal) {

                            OResultInternal content = (OResultInternal) property;

                            //处理关系
                            WiklRelationInfo relationship = new WiklRelationInfo();

                            relationship.setLabel(content.getProperty("label") + "");
                            relationship.setSource(content.getProperty("source") + "");
                            relationship.setTarget(content.getProperty("target") + "");
                            relationship.setText(content.getProperty("text") + "");
                            relationship.setType(content.getProperty("type") + "");
                            relationship.setId(content.getProperty("id") + "");
                            edges.add(relationship);

                        }

                        //结果是单个属性
                        others.put(name, property);
                    }
                }
            }

            //结果不在变量中
            if (!Optional.empty().equals(optional)) {

                //select 方式查询结果处理
                Optional<OElement> element = item.getElement();

                OElement oElement = element.get();

                //判断元素是否是线
                boolean edge = oElement.isEdge();

                //元素是线
                if (edge) {
                    Optional<OEdge> optionalOEdge = oElement.asEdge();
                    OEdge oEdge = optionalOEdge.get();

                    //处理起点
                    setConceptNode(nodes, oEdge.getFrom(), startName, endName, resultData);

                    //处理终点
                    setConceptNode(nodes, oEdge.getTo(), startName, endName, resultData);

                    setConceptRelationShip(edges, oEdge);

                }

                //判断元素是否是节点
                boolean vertex = oElement.isVertex();

                //元素是点
                if (vertex) {
                    Optional<OVertex> optionalOVertex = oElement.asVertex();

                    OVertex oVertex = optionalOVertex.get();

                    setConceptNode(nodes, oVertex, startName, endName, resultData);

                }
            }
        }

        //节点去重
        nodes = RemoveRepeatDataUtil.removeDuplicateUser(nodes);

        //关系去重
        edges = RemoveRepeatDataUtil.RemoveRepeatRelationShipList(edges);

        if (limit != 0 && nodes.size() > limit) {

            List<WiklNodeInfo> nodes1 = get50Nodes(nodes, limit);

            List<String> nodesList = getId(nodes1);

            //获取符合条件的关系
            List<WiklRelationInfo> edge = getEdge(edges, nodesList);
            resultData.setNodes(nodes1);
            resultData.setEdges(edge);
        } else {
            resultData.setNodes(nodes);
            resultData.setEdges(edges);
        }

        System.out.println("节点数：" + nodes.size() + "++++关系数：" + edges.size());

        return resultData;

    }

    /**
     * jdbc方式结果集封装
     *
     * @param rs
     * @return
     */
    public static WiklResultData JdbcResult(OResultSet rs, int limit) {

        //结果对象
        WiklResultData resultData = new WiklResultData();

        //点集合
        List<WiklNodeInfo> nodes = new ArrayList<>();

        //线集合
        List<WiklRelationInfo> edges = new ArrayList<>();

        //单个属性结果集合
        Map<String, Object> others = new HashMap<>();

        while (rs.hasNext()) {
            OResult item = rs.next();

            //获取元素
            Optional<OElement> optional = item.getElement();

            //match 方式结果处理(结果在变量中)
            if (Optional.empty().equals(optional)) {

                OResultInternal oResultInternal = (OResultInternal) item;

                //获取返回结果key
                Set<String> propertyNames = oResultInternal.getPropertyNames();

                /**
                 * 由于无法返回的结果中，哪个字段代表关系，哪个代表点，因此，用 key ,分别去调用线的方法，点的方法，哪个有结果就说明当前 key 代表的数据是线还是点，再做针对的处理
                 */
                for (String name : propertyNames) {

                    Object property = oResultInternal.getProperty(name);

                    //根据 key 去查询线
                    OEdge oEdge = oResultInternal.getEdgeProperty(name);

                    //根据 key 去查询点
                    OVertex oVertex = oResultInternal.getVertexProperty(name);

                    //结果是点
                    if (!Objects.isNull(oVertex)) {

                        setNode(nodes, oVertex);

                    } else if (!Objects.isNull(oEdge)) {
                        //结果是线
                        //处理起点
                        setNode(nodes, oEdge.getFrom());

                        //处理终点
                        setNode(nodes, oEdge.getTo());

                        setRelationShip(edges, oEdge);
                    } else {
                        //结果是单个属性
                        others.put(name, property);
                    }
                }
            }

            //结果不在变量中
            if (!Optional.empty().equals(optional)) {

                //select 方式查询结果处理
                Optional<OElement> element = item.getElement();

                OElement oElement = element.get();

                //判断元素是否是线
                boolean edge = oElement.isEdge();

                //元素是线
                if (edge) {
                    Optional<OEdge> optionalOEdge = oElement.asEdge();
                    OEdge oEdge = optionalOEdge.get();

                    //处理起点
                    setNode(nodes, oEdge.getFrom());

                    //处理终点
                    setNode(nodes, oEdge.getTo());

                    setRelationShip(edges, oEdge);

                }

                //判断元素是否是节点
                boolean vertex = oElement.isVertex();

                //元素是点
                if (vertex) {
                    Optional<OVertex> optionalOVertex = oElement.asVertex();

                    OVertex oVertex = optionalOVertex.get();

                    setNode(nodes, oVertex);
                }
            }
        }

        //节点去重
        nodes = RemoveRepeatDataUtil.removeDuplicateUser(nodes);

        //关系去重
        edges = RemoveRepeatDataUtil.RemoveRepeatRelationShipList(edges);

        if (limit != 0 && nodes.size() > limit) {

            List<WiklNodeInfo> nodes1 = get50Nodes(nodes, limit);

            List<String> nodesList = getId(nodes1);

            //获取符合条件的关系
            List<WiklRelationInfo> edge = getEdge(edges, nodesList);
            resultData.setNodes(nodes1);
            resultData.setEdges(edge);
        } else {
            resultData.setNodes(nodes);
            resultData.setEdges(edges);
        }

        //封装其他数据信息
        resultData.setOthers(others);

        return resultData;
    }

    /**
     * 模式匹配方式处理根据文件名筛选数据
     *
     * @param list
     * @param limit
     * @return
     */
    public static WiklResultData matchSearchResult(List<Object> list, int limit, String condition) {

        WiklResultData resultData = new WiklResultData();

        List<WiklNodeInfo> nodes = new ArrayList<>();

        List<WiklRelationInfo> edges = new ArrayList<>();

        for (Object object : list) {

            if (object instanceof Map.Entry) {
                Map.Entry<String, Object> map = (Map.Entry) object;

                Object value = map.getValue();

                if (value instanceof OrientVertex) {
                    OrientVertex vertex = (OrientVertex) value;

                    //节点来源
                    List<String> comeFrom = vertex.value("comeFrom");

                    if (comeFrom.contains(condition)) {
                        //处理节点
                        setNode(nodes, vertex);
                    }
                }

                //处理线
                if (value instanceof OrientEdge) {

                    OrientEdge orientEdge = (OrientEdge) value;

                    //起点
                    Vertex inVertex = orientEdge.inVertex();
                    String inVertexType = inVertex.value("type");

                    //终点
                    Vertex outVertex = orientEdge.outVertex();
                    String outVertexType = outVertex.value("type");

                    //处理节点
                    if (WiklGraphType.instance.getKey().equals(inVertexType) && WiklGraphType.instance.getKey().equals(outVertexType)) {

                        List<String> inComeFrom = inVertex.value("comeFrom");
                        List<String> outComeFrom = outVertex.value("comeFrom");
                        if (inComeFrom.contains(condition) && outComeFrom.contains(condition)) {
                            //处理节点
                            setNode(nodes, inVertex);

                            setNode(nodes, outVertex);

                            //处理关系
                            setRelationShip(edges, orientEdge);
                        }
                    }
                }
            }

            if (object instanceof OrientVertex) {
                OrientVertex vertex = (OrientVertex) object;

                //节点来源
                List<String> comeFrom = vertex.value("comeFrom");

                if (comeFrom.contains(condition)) {
                    //处理节点
                    setNode(nodes, vertex);
                }
            }

            //处理线
            if (object instanceof OrientEdge) {

                OrientEdge orientEdge = (OrientEdge) object;

                //起点
                Vertex inVertex = orientEdge.inVertex();
                String inVertexType = inVertex.value("type");

                //终点
                Vertex outVertex = orientEdge.outVertex();
                String outVertexType = outVertex.value("type");

                //处理节点
                if (WiklGraphType.instance.getKey().equals(inVertexType) && WiklGraphType.instance.getKey().equals(outVertexType)) {

                    List<String> inComeFrom = inVertex.value("comeFrom");
                    List<String> outComeFrom = outVertex.value("comeFrom");
                    if (inComeFrom.contains(condition) && outComeFrom.contains(condition)) {
                        //处理节点
                        setNode(nodes, inVertex);

                        setNode(nodes, outVertex);

                        //处理关系
                        setRelationShip(edges, orientEdge);
                    }
                }
            }

        }

        //节点去重
        nodes = RemoveRepeatDataUtil.removeDuplicateUser(nodes);

        //关系去重
        edges = RemoveRepeatDataUtil.RemoveRepeatRelationShipList(edges);

        if (limit != 0 && nodes.size() > limit) {

            List<WiklNodeInfo> nodes1 = get50Nodes(nodes, limit);

            List<String> nodesList = getId(nodes1);

            //获取符合条件的关系
            List<WiklRelationInfo> edge = getEdge(edges, nodesList);
            resultData.setNodes(nodes1);
            resultData.setEdges(edge);
        } else {
            resultData.setNodes(nodes);
            resultData.setEdges(edges);
        }

        System.out.println("节点数：" + nodes.size() + "++++关系数：" + edges.size());
        return resultData;
    }

    /**
     * 模式匹配方式处理含有概念图谱的数据
     *
     * @param list
     * @param limit
     * @return
     */
    public static WiklConceptResultData matchConceptResult(List<Object> list, int limit, String startName, String endName) {
        WiklConceptResultData resultData = new WiklConceptResultData();

        List<WiklNodeInfo> nodes = new ArrayList<>();
        List<WiklRelationInfo> edges = new ArrayList<>();
        for (Object object : list) {

            if (object instanceof Map.Entry) {
                Map.Entry<String, Object> map = (Map.Entry) object;

                Object value = map.getValue();

                if (value instanceof OrientVertex) {
                    OrientVertex vertex = (OrientVertex) map.getValue();

                    String label = vertex.label();
                    if (SysConstant.CONCEPT_LABEL.equals(label)) {
                        //处理节点
                        setConceptNode(nodes, vertex, startName, endName, resultData);
                    }
                }

                if (value instanceof OrientEdge) {
                    OrientEdge edge = (OrientEdge) map.getValue();

                    //起点
                    Vertex inVertex = edge.inVertex();

                    String inLabel = inVertex.label();

                    //终点
                    Vertex outVertex = edge.outVertex();

                    String outLabel = outVertex.label();

                    if (SysConstant.CONCEPT_LABEL.equals(inLabel) && SysConstant.CONCEPT_LABEL.equals(outLabel)) {
                        //处理节点
                        setConceptNode(nodes, inVertex, startName, endName, resultData);

                        setConceptNode(nodes, outVertex, startName, endName, resultData);

                        //处理关系
                        setRelationShip(edges, edge);
                    }
                }
            }

            if (object instanceof OrientEdge) {

                OrientEdge orientEdge = (OrientEdge) object;

                Iterator<Vertex> vertexIterator = orientEdge.bothVertices();

                while (vertexIterator.hasNext()) {

                    Vertex vertex = vertexIterator.next();

                    //处理节点
                    setConceptNode(nodes, vertex, startName, endName, resultData);
                }

                //处理关系
                setRelationShip(edges, orientEdge);

            }

            //当前元素是节点
            if (object instanceof OrientVertex) {

                OrientVertex orientVertex = (OrientVertex) object;

                //处理节点
                setConceptNode(nodes, orientVertex, startName, endName, resultData);
            }
        }

        //节点去重
        nodes = RemoveRepeatDataUtil.removeDuplicateUser(nodes);

        //关系去重
        edges = RemoveRepeatDataUtil.RemoveRepeatRelationShipList(edges);

        if (limit != 0 && nodes.size() > limit) {

            List<WiklNodeInfo> nodes1 = get50Nodes(nodes, limit);

            List<String> nodesList = getId(nodes1);

            //获取符合条件的关系
            List<WiklRelationInfo> edge = getEdge(edges, nodesList);
            resultData.setNodes(nodes1);
            resultData.setEdges(edge);
        } else {
            resultData.setNodes(nodes);
            resultData.setEdges(edges);
        }

        System.out.println("节点数：" + nodes.size() + "++++关系数：" + edges.size());

        return resultData;
    }

    /**
     * 模式匹配方式处理含有概念图谱的数据
     *
     * @param list
     * @param limit
     * @return
     */
    public static WiklResultData matchConceptResult(List<Object> list, int limit) {
        WiklResultData resultData = new WiklResultData();

        List<WiklNodeInfo> nodes = new ArrayList<>();
        List<WiklRelationInfo> edges = new ArrayList<>();
        for (Object object : list) {

            if (object instanceof Map.Entry) {
                Map.Entry<String, Object> map = (Map.Entry) object;

                Object value = map.getValue();

                if (value instanceof OrientVertex) {
                    OrientVertex vertex = (OrientVertex) map.getValue();

                    String label = vertex.label();
                    if (SysConstant.CONCEPT_LABEL.equals(label)) {
                        //处理节点
                        setNode(nodes, vertex);
                    }
                }

                if (value instanceof OrientEdge) {
                    OrientEdge edge = (OrientEdge) map.getValue();

                    //起点
                    Vertex inVertex = edge.inVertex();

                    String inLabel = inVertex.label();

                    //终点
                    Vertex outVertex = edge.outVertex();

                    String outLabel = outVertex.label();

                    if (SysConstant.CONCEPT_LABEL.equals(inLabel) && SysConstant.CONCEPT_LABEL.equals(outLabel)) {
                        //处理节点
                        setNode(nodes, inVertex);

                        setNode(nodes, outVertex);

                        //处理关系
                        setRelationShip(edges, edge);
                    }
                }
            }
        }

        //节点去重
        nodes = RemoveRepeatDataUtil.removeDuplicateUser(nodes);

        //关系去重
        edges = RemoveRepeatDataUtil.RemoveRepeatRelationShipList(edges);

        if (limit != 0 && nodes.size() > limit) {

            List<WiklNodeInfo> nodes1 = get50Nodes(nodes, limit);

            List<String> nodesList = getId(nodes1);

            //获取符合条件的关系
            List<WiklRelationInfo> edge = getEdge(edges, nodesList);
            resultData.setNodes(nodes1);
            resultData.setEdges(edge);
        } else {
            resultData.setNodes(nodes);
            resultData.setEdges(edges);
        }

        System.out.println("节点数：" + nodes.size() + "++++关系数：" + edges.size());

        return resultData;
    }

    /**
     * 模式匹配方式结果实例结果处理
     *
     * @param list
     * @param limit
     * @return
     */
    public static WiklResultData matchInstanceResult(List<Object> list, int limit) {
        WiklResultData resultData = new WiklResultData();

        List<WiklNodeInfo> nodes = new ArrayList<>();
        List<WiklRelationInfo> edges = new ArrayList<>();
        for (Object object : list) {

            if (object instanceof Map.Entry) {
                Map.Entry<String, Object> map = (Map.Entry) object;

                Object value = map.getValue();

                if (value instanceof OrientVertex) {
                    OrientVertex vertex = (OrientVertex) map.getValue();
                    //处理节点
                    setNode(nodes, vertex);
                }

                if (value instanceof OrientEdge) {
                    OrientEdge edge = (OrientEdge) map.getValue();

                    //起点
                    Vertex inVertex = edge.inVertex();
                    String inVertexType = inVertex.value("type");

                    //终点
                    Vertex outVertex = edge.outVertex();

                    String outVertexType = outVertex.value("type");

                    if (WiklGraphType.instance.getKey().equals(inVertexType) && WiklGraphType.instance.getKey().equals(outVertexType)) {
                        //处理节点
                        setNode(nodes, inVertex);

                        setNode(nodes, outVertex);

                        //处理关系
                        setRelationShip(edges, edge);
                    }
                }
            }
        }

        //节点去重
        nodes = RemoveRepeatDataUtil.removeDuplicateUser(nodes);

        //关系去重
        edges = RemoveRepeatDataUtil.RemoveRepeatRelationShipList(edges);

        if (limit != 0 && nodes.size() > limit) {

            List<WiklNodeInfo> nodes1 = get50Nodes(nodes, limit);

            List<String> nodesList = getId(nodes1);

            //获取符合条件的关系
            List<WiklRelationInfo> edge = getEdge(edges, nodesList);
            resultData.setNodes(nodes1);
            resultData.setEdges(edge);
        } else {
            resultData.setNodes(nodes);
            resultData.setEdges(edges);
        }

        System.out.println("节点数：" + nodes.size() + "++++关系数：" + edges.size());

        return resultData;
    }

    /**
     * @param list
     * @param limit
     * @return
     */
    public static WiklResultData dealResultWithColorAndSize(List<Object> list, int limit, String label) {
        WiklResultData resultData = new WiklResultData();

        List<WiklNodeInfo> nodes = new ArrayList<>();
        List<WiklRelationInfo> edges = new ArrayList<>();

        for (Object object : list) {

            if (object instanceof Map.Entry) {
                Map.Entry<String, Object> map = (Map.Entry) object;

                Object value = map.getValue();

                if (value instanceof OrientVertex) {
                    OrientVertex vertex = (OrientVertex) map.getValue();
                    //处理节点
                    setNode(nodes, vertex, label);
                }

                if (value instanceof OrientEdge) {
                    OrientEdge edge = (OrientEdge) map.getValue();

                    //起点
                    Vertex inVertex = edge.inVertex();
                    String inVertexType = inVertex.value("type");

                    //终点
                    Vertex outVertex = edge.outVertex();

                    String outVertexType = outVertex.value("type");

                    if (WiklGraphType.instance.getKey().equals(inVertexType) && WiklGraphType.instance.getKey().equals(outVertexType)) {
                        //处理节点
                        setNode(nodes, inVertex, label);

                        setNode(nodes, outVertex, label);

                        //处理关系
                        setRelationShip(edges, edge);
                    }
                }
            }

            //当前元素是关系
            if (object instanceof OrientEdge) {

                OrientEdge orientEdge = (OrientEdge) object;

                Iterator<Vertex> vertexIterator = orientEdge.bothVertices();

                while (vertexIterator.hasNext()) {

                    Vertex vertex = vertexIterator.next();

                    //处理节点
                    setNode(nodes, vertex, label);
                }

                //处理关系
                setRelationShip(edges, orientEdge);

            }

            //当前元素是节点
            if (object instanceof OrientVertex) {

                OrientVertex orientVertex = (OrientVertex) object;

                //处理节点
                setNode(nodes, orientVertex, label);
            }
        }

        //节点去重
        nodes = RemoveRepeatDataUtil.removeDuplicateUser(nodes);


        //关系去重
        edges = RemoveRepeatDataUtil.RemoveRepeatRelationShipList(edges);

        if (limit != 0 && nodes.size() > limit) {

            List<WiklNodeInfo> nodes1 = get50Nodes(nodes, limit);

            List<String> nodesList = getId(nodes1);

            //获取符合条件的关系
            List<WiklRelationInfo> edge = getEdge(edges, nodesList);
            resultData.setNodes(nodes1);
            resultData.setEdges(edge);
        } else {
            resultData.setNodes(nodes);
            resultData.setEdges(edges);
        }


        System.out.println("节点数：" + nodes.size() + "++++关系数：" + edges.size());

        return resultData;

    }

    /**
     * @param list
     * @param limit
     * @return
     */
    public static WiklResultData dealWithResult(List<Object> list, int limit) {
        WiklResultData resultData = new WiklResultData();

        List<WiklNodeInfo> nodes = new ArrayList<>();
        List<WiklRelationInfo> edges = new ArrayList<>();

        for (Object object : list) {

            if (object instanceof Map.Entry) {
                Map.Entry<String, Object> map = (Map.Entry) object;

                Object value = map.getValue();

                if (value instanceof OrientVertex) {
                    OrientVertex vertex = (OrientVertex) map.getValue();
                    //处理节点
                    setNode(nodes, vertex);
                }

                if (value instanceof OrientEdge) {
                    OrientEdge edge = (OrientEdge) map.getValue();

                    //起点
                    Vertex inVertex = edge.inVertex();
                    String inVertexType = inVertex.value("type");

                    //终点
                    Vertex outVertex = edge.outVertex();

                    String outVertexType = outVertex.value("type");

                    if (WiklGraphType.instance.getKey().equals(inVertexType) && WiklGraphType.instance.getKey().equals(outVertexType)) {
                        //处理节点
                        setNode(nodes, inVertex);

                        setNode(nodes, outVertex);

                        //处理关系
                        setRelationShip(edges, edge);
                    }
                }
            }

            //当前元素是关系
            if (object instanceof OrientEdge) {

                OrientEdge orientEdge = (OrientEdge) object;

                Iterator<Vertex> vertexIterator = orientEdge.bothVertices();

                while (vertexIterator.hasNext()) {

                    Vertex vertex = vertexIterator.next();

                    //处理节点
                    setNode(nodes, vertex);
                }

                //处理关系
                setRelationShip(edges, orientEdge);

            }

            //当前元素是节点
            if (object instanceof OrientVertex) {

                OrientVertex orientVertex = (OrientVertex) object;

                //处理节点
                setNode(nodes, orientVertex);
            }
        }

        //节点去重
        nodes = RemoveRepeatDataUtil.removeDuplicateUser(nodes);


        //关系去重
        edges = RemoveRepeatDataUtil.RemoveRepeatRelationShipList(edges);

        resultData.setNodes(nodes);
        resultData.setEdges(edges);

        System.out.println("节点数：" + nodes.size() + "++++关系数：" + edges.size());

        return resultData;

    }

    /**
     * 查询单个点和单个线的属性
     *
     * @param rs
     * @return
     */
    public static SingleRecordInfoResultData dealWithPropertyResult(OResultSet rs) {

        SingleRecordInfoResultData resultData = new SingleRecordInfoResultData();

        while (rs.hasNext()) {
            OResult item = rs.next();

            //获取元素
            Optional<OElement> optional = item.getElement();

            //match 方式结果处理(结果在变量中)
            if (Optional.empty().equals(optional)) {

                OResultInternal oResultInternal = (OResultInternal) item;

                //获取返回结果key
                Set<String> propertyNames = oResultInternal.getPropertyNames();

                /**
                 * 由于无法返回的结果中，哪个字段代表关系，哪个代表点，因此，用 key ,分别去调用线的方法，点的方法，哪个有结果就说明当前 key 代表的数据是线还是点，再做针对的处理
                 */
                for (String name : propertyNames) {

                    //根据 key 去查询线
                    OEdge oEdge = oResultInternal.getEdgeProperty(name);

                    //根据 key 去查询点
                    OVertex oVertex = oResultInternal.getVertexProperty(name);

                    //结果是点
                    if (!Objects.isNull(oVertex)) {

                        setProperties(oVertex, resultData);

                    } else if (!Objects.isNull(oEdge)) {
                        //结果是线
                        OVertex outVertex = oEdge.getFrom();

                        OVertex inVertex = oEdge.getTo();

                        resultData.setStartLabel(outVertex.getProperty("name").toString());

                        resultData.setEndLabel(inVertex.getProperty("name").toString());

                        setProperties(oEdge, resultData);

                    }

                }
            }

            //结果不在变量中
            if (!Optional.empty().equals(optional)) {

                //select 方式查询结果处理
                Optional<OElement> element = item.getElement();

                OElement oElement = element.get();

                //判断元素是否是线
                boolean edge = oElement.isEdge();

                //元素是线
                if (edge) {
                    Optional<OEdge> optionalOEdge = oElement.asEdge();
                    OEdge oEdge = optionalOEdge.get();

                    //结果是线
                    OVertex outVertex = oEdge.getFrom();

                    OVertex inVertex = oEdge.getTo();

                    resultData.setStartLabel(outVertex.getProperty("name").toString());

                    resultData.setEndLabel(inVertex.getProperty("name").toString());

                    setProperties(oEdge, resultData);

                }

                //判断元素是否是节点
                boolean vertex = oElement.isVertex();

                //元素是点
                if (vertex) {
                    Optional<OVertex> optionalOVertex = oElement.asVertex();

                    OVertex oVertex = optionalOVertex.get();

                    setProperties(oVertex, resultData);
                }
            }

        }

        return resultData;

    }

    /**
     * 查询单个点和单个线的属性
     *
     * @param list
     * @return
     */
    public static SingleRecordInfoResultData dealWithPropertyResult(List<Object> list) {

        SingleRecordInfoResultData resultData = new SingleRecordInfoResultData();

        for (Object object : list) {
            //当前元素是关系
            if (object instanceof OrientEdge) {

                OrientEdge orientEdge = (OrientEdge) object;

                Vertex outVertex = orientEdge.outVertex();

                Vertex inVertex = orientEdge.inVertex();

                resultData.setStartLabel(outVertex.property("name").value().toString());

                resultData.setEndLabel(inVertex.property("name").value().toString());

                setProperties(orientEdge, resultData);

            }

            //当前元素是节点
            if (object instanceof OrientVertex) {

                OrientVertex orientVertex = (OrientVertex) object;

                //处理节点
                setProperties(orientVertex, resultData);
            }
        }

        return resultData;

    }

    /**
     * 获取符合条件的关系
     *
     * @param relationInfos
     * @param nodeIds
     * @return
     */
    public static List<WiklRelationInfo> getEdge(List<WiklRelationInfo> relationInfos, List<String> nodeIds) {

        List<WiklRelationInfo> edges = new ArrayList<>();

        for (WiklRelationInfo relationInfo : relationInfos) {

            if (nodeIds.contains(relationInfo.getSource()) && nodeIds.contains(relationInfo.getTarget())) {
                edges.add(relationInfo);

            }
        }

        return edges;
    }

    /**
     * 获取节点 id 集合
     *
     * @param list
     * @return
     */
    public static List<String> getId(List<WiklNodeInfo> list) {

        List<String> ids = new ArrayList<>();

        for (WiklNodeInfo nodeInfo : list) {
            ids.add(nodeInfo.getId());
        }

        return ids;
    }


    /**
     * 节点排序，获取前 limit 个
     *
     * @param nodes
     * @param limit
     * @return
     */
    public static List<WiklNodeInfo> get50Nodes(List<WiklNodeInfo> nodes, int limit) {

        WiklSortListUtil<WiklNodeInfo> sortList = new WiklSortListUtil<>();

        sortList.sortByMethod(nodes, "getCreateDate", false);

        List<WiklNodeInfo> nodeInfos = nodes.subList(0, limit);

        return nodeInfos;
    }

    /**
     * 处理输出结果
     *
     * @param traversal
     * @return
     */
    public static WiklResultData dealWithResult(GraphTraversal traversal) {

        WiklResultData resultData = new WiklResultData();

        List<WiklNodeInfo> nodes = new ArrayList<>();
        List<WiklRelationInfo> edges = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();

        boolean flag = traversal.hasNext();

        while (flag) {

            Optional optional = traversal.tryNext();

            if (!Optional.empty().equals(optional)) {
                //获取当前元素
                Object object = optional.get();

                //当前元素是关系
                if (object instanceof OrientEdge) {

                    OrientEdge orientEdge = (OrientEdge) object;

                    Iterator<Vertex> vertexIterator = orientEdge.bothVertices();

                    while (vertexIterator.hasNext()) {

                        Vertex vertex = vertexIterator.next();

                        //处理节点
                        setNode(nodes, vertex);
                    }

                    //处理关系
                    setRelationShip(edges, orientEdge);

                } else if (object instanceof OrientVertex) {
                    //当前元素是节点

                    OrientVertex orientVertex = (OrientVertex) object;

                    //处理节点
                    setNode(nodes, orientVertex);
                } else {
//                    map.put()
                }
            }

        }

        //节点去重
        nodes = RemoveRepeatDataUtil.removeDuplicateUser(nodes);
        //关系去重
        edges = RemoveRepeatDataUtil.RemoveRepeatRelationShipList(edges);

        resultData.setNodes(nodes);
        resultData.setEdges(edges);

        System.out.println("节点数：" + nodes.size() + "++++关系数：" + edges.size());

        return resultData;

    }

    /**
     * 处理节点
     *
     * @param nodes
     * @param vertex
     */
    public static void setNode(List<WiklNodeInfo> nodes, Vertex vertex) {

        WiklNodeInfo start = new WiklNodeInfo();
        Object date = vertex.property("date").value();
        Date date1 = (Date) date;

        start.setId(vertex.id().toString());
        start.setNodeName(vertex.value("name") + "");
        start.setLabel(vertex.label());
        start.setCreateDate(date1);

        nodes.add(start);
    }

    /**
     * 处理节点
     *
     * @param nodes
     * @param vertex
     */
    public static void setNode(List<WiklNodeInfo> nodes, Vertex vertex, String label) {

        WiklNodeInfo start = new WiklNodeInfo();
        Object date = vertex.property("date").value();
        Date date1 = (Date) date;

        start.setId(vertex.id().toString());
        start.setNodeName(vertex.value("name") + "");
        start.setLabel(vertex.label());
        start.setCreateDate(date1);

        //节点颜色
        Integer integer = WiklGraphNodeUtils.nodeColor(vertex.label());

        //封装节点颜色级别
        start.setNodeColor(integer.longValue());

        if (StringUtils.isBlank(label)) {
            start.setNodeSize(1);
        }
        if (label.equals(vertex.label())) {
            start.setNodeSize(2);
        }

        nodes.add(start);
    }

    /**
     * 处理节点
     *
     * @param nodes
     * @param vertex
     */
    public static void setConceptNode(List<WiklNodeInfo> nodes, Vertex vertex, String startName, String endName, WiklConceptResultData resultData) {

        WiklNodeInfo start = new WiklNodeInfo();
        Object date = vertex.property("date").value();

        boolean parentNode = (boolean) vertex.property("parentNode").value();

        Date date1 = (Date) date;

        String name = vertex.value("name");

        start.setId(vertex.id().toString());
        start.setNodeName(vertex.value("name"));
        start.setLabel(vertex.label());
        start.setInstanceLabel(vertex.value("instanceLabel"));
        start.setCreateDate(date1);
        start.setParentNode(parentNode);

        //节点颜色
        Integer integer = WiklGraphNodeUtils.nodeColor(vertex.label());

        //封装节点颜色级别
        start.setNodeColor(integer.longValue());

        nodes.add(start);

        if (name.equals(startName)) {
            resultData.setStartLabel(vertex.value("instanceLabel"));
        }
        if (name.equals(endName)) {

            resultData.setEndLabel(vertex.value("instanceLabel"));
        }
    }

    /**
     * 处理节点
     *
     * @param nodes
     * @param vertex
     */
    public static void setConceptNode(List<WiklNodeInfo> nodes, OVertex vertex, String startName, String endName, WiklConceptResultData resultData) {

        WiklNodeInfo start = new WiklNodeInfo();
        String name = vertex.getProperty("name").toString();

        boolean parentNode = vertex.getProperty("parentNode");

        Optional<OClass> schemaType = vertex.getSchemaType();
        String nodeLabel = schemaType.get().toString();

        start.setId(vertex.getIdentity().toString());
        start.setNodeName(name);
        start.setLabel(nodeLabel);
        start.setInstanceLabel(vertex.getProperty("instanceLabel"));
        start.setParentNode(parentNode);

        nodes.add(start);

        if (name.equals(startName)) {
            resultData.setStartLabel(vertex.getProperty("instanceLabel"));
        }
        if (name.equals(endName)) {

            resultData.setEndLabel(vertex.getProperty("instanceLabel"));
        }
    }


    /**
     * 处理节点（orientdb 原生查询结果时使用）
     *
     * @param nodes
     * @param oVertex
     */
    public static void setNode(List<WiklNodeInfo> nodes, OVertex oVertex) {

        WiklNodeInfo start = new WiklNodeInfo();
        Object date = oVertex.getProperty("date");
        String name = oVertex.getProperty("name").toString();

        Optional<OClass> schemaType = oVertex.getSchemaType();
        String label = schemaType.get().toString();

        Date date1 = (Date) date;

        start.setId(oVertex.getIdentity().toString());
        start.setNodeName(name);
        start.setLabel(label);
        start.setCreateDate(date1);
        nodes.add(start);
    }

    /**
     * 处理节点（orientdb 原生查询结果时使用）
     *
     * @param nodes
     * @param oVertex
     */
    public static void setNode(List<WiklNodeInfo> nodes, OVertex oVertex, String label) {

        Map<String, Object> propertyMap = new HashMap<>();

        //图谱默认属性（非业务属性，详情时不展示）
        List<String> strings = Arrays.asList("frequency", "type", "version", "kngraphId", "comeFrom", "date");

        WiklNodeInfo start = new WiklNodeInfo();

        Set<String> propertyNames = oVertex.getPropertyNames();

        Iterator<String> iterator = propertyNames.iterator();

        while (iterator.hasNext()) {
            //属性key
            String propertyKey = iterator.next();

            if (!strings.contains(propertyKey) && !propertyKey.startsWith("out_") && !propertyKey.startsWith("in_")) {

                Object value = oVertex.getProperty(propertyKey);
                propertyMap.put(propertyKey, value);

                if ("date".equals(propertyKey)) {
                    start.setCreateDate((Date) value);
                    value = WiklDateUtil.getDay((Date) value);
                }

                if ("name".equals(propertyKey)) {
                    start.setNodeName(value + "");
                }

                if ("parentNode".equals(propertyKey)) {
                    if (!Objects.isNull(value)) {
                        start.setParentNode((boolean) value);
                    }
                }
            }
        }

        Optional<OClass> schemaType = oVertex.getSchemaType();
        String nodeLabel = schemaType.get().toString();

        start.setId(oVertex.getIdentity().toString());
        start.setLabel(nodeLabel);

        //节点颜色
        Integer integer = WiklGraphNodeUtils.nodeColor(nodeLabel);

        //封装节点颜色级别
        start.setNodeColor(integer.longValue());

        if (StringUtils.isBlank(label)) {
            start.setNodeSize(1);
        }
        if (label.equals(nodeLabel)) {
            start.setNodeSize(2);
        }

        start.setProperties(propertyMap);

        nodes.add(start);
    }

    /**
     * 处理属性
     *
     * @param object
     * @param resultData
     */
    public static void setProperties(Object object, WiklResultData resultData) {

        //图谱默认属性（非业务属性，详情时不展示）
        List<String> strings = Arrays.asList("frequency", "type", "version", "kngraphId", "comeFrom", "date");

        Map<String, Object> propertyMap = new HashMap<>();

        if (object instanceof Edge) {
            Edge edge = (Edge) object;

            Set<String> keys = edge.keys();

            Iterator<String> iterator = keys.iterator();

            while (iterator.hasNext()) {

                //属性key
                String propertyKey = iterator.next();

                if (!strings.contains(propertyKey)) {

                    Object value = edge.value(propertyKey);

                    if ("date".equals(propertyKey)) {
                        value = WiklDateUtil.getDay((Date) value);
                    }

                    propertyMap.put(propertyKey, value);
                }
            }
        }

        if (object instanceof Vertex) {
            Vertex vertex = (Vertex) object;

            Set<String> keys = vertex.keys();

            Iterator<String> iterator = keys.iterator();

            while (iterator.hasNext()) {
                //属性key
                String propertyKey = iterator.next();

                if (!strings.contains(propertyKey)) {

                    Object value = vertex.value(propertyKey);

                    if ("date".equals(propertyKey)) {
                        value = WiklDateUtil.getDay((Date) value);
                    }

                    propertyMap.put(propertyKey, value);

                }

            }

        }

        if (object instanceof OVertexDocument) {
            OVertexDocument oVertexDocument = (OVertexDocument) object;

            Set<String> propertyNames = oVertexDocument.getPropertyNames();

            Iterator<String> iterator = propertyNames.iterator();

            while (iterator.hasNext()) {
                //属性key
                String propertyKey = iterator.next();

                if (!strings.contains(propertyKey) && !propertyKey.startsWith("out_") && !propertyKey.startsWith("in_")) {

                    Object value = oVertexDocument.getProperty(propertyKey);

                    if ("date".equals(propertyKey)) {
                        value = WiklDateUtil.getDay((Date) value);
                    }

                    propertyMap.put(propertyKey, value);

                }
            }
        }

        if (object instanceof OEdgeDocument) {

            OEdgeDocument oEdgeDocument = (OEdgeDocument) object;

            Set<String> propertyNames = oEdgeDocument.getPropertyNames();

            Iterator<String> iterator = propertyNames.iterator();

            while (iterator.hasNext()) {
                //属性key
                String propertyKey = iterator.next();

                if (!strings.contains(propertyKey) && !propertyKey.startsWith("out_") && !propertyKey.startsWith("in_")) {

                    Object value = oEdgeDocument.getProperty(propertyKey);

                    if ("date".equals(propertyKey)) {
                        value = WiklDateUtil.getDay((Date) value);
                    }

                    propertyMap.put(propertyKey, value);

                }
            }
        }

        resultData.setOthers(propertyMap);
    }

    /**
     * 处理属性
     *
     * @param object
     * @param resultData
     */
    public static void setProperties(Object object, SingleRecordInfoResultData resultData) {

        //图谱默认属性（非业务属性，详情时不展示）
        List<String> strings = Arrays.asList("frequency", "type", "version", "kngraphId", "comeFrom", "date");

        Map<String, Object> propertyMap = new HashMap<>();

        if (object instanceof Edge) {
            Edge edge = (Edge) object;

            Set<String> keys = edge.keys();

            Iterator<String> iterator = keys.iterator();

            while (iterator.hasNext()) {

                //属性key
                String propertyKey = iterator.next();

                if (!strings.contains(propertyKey)) {

                    Object value = edge.value(propertyKey);

                    if ("date".equals(propertyKey)) {
                        value = WiklDateUtil.getDay((Date) value);
                    }

                    propertyMap.put(propertyKey, value);
                }
            }
        }

        if (object instanceof OEdge) {

            OEdge edge = (OEdge) object;

            Set<String> keys = edge.getPropertyNames();

            Iterator<String> iterator = keys.iterator();

            while (iterator.hasNext()) {

                //属性key
                String propertyKey = iterator.next();

                if (!strings.contains(propertyKey)) {

                    Object value = edge.getProperty(propertyKey);

                    if ("date".equals(propertyKey)) {
                        value = WiklDateUtil.getDay((Date) value);
                    }

                    propertyMap.put(propertyKey, value);
                }
            }
        }

        if (object instanceof Vertex) {
            Vertex vertex = (Vertex) object;

            Set<String> keys = vertex.keys();

            Iterator<String> iterator = keys.iterator();

            while (iterator.hasNext()) {
                //属性key
                String propertyKey = iterator.next();

                if (!strings.contains(propertyKey)) {

                    Object value = vertex.value(propertyKey);

                    if ("date".equals(propertyKey)) {
                        value = WiklDateUtil.getDay((Date) value);
                    }

                    propertyMap.put(propertyKey, value);
                }
            }
        }

        if (object instanceof OVertex) {
            OVertex vertex = (OVertex) object;

            Set<String> keys = vertex.getPropertyNames();

            Iterator<String> iterator = keys.iterator();

            while (iterator.hasNext()) {
                //属性key
                String propertyKey = iterator.next();

                if (!strings.contains(propertyKey)) {

                    Object value = vertex.getProperty(propertyKey);

                    if ("date".equals(propertyKey)) {
                        value = WiklDateUtil.getDay((Date) value);
                    }

                    propertyMap.put(propertyKey, value);

                }
            }
        }

        resultData.setOthers(propertyMap);
    }

    /**
     * 处理关系
     *
     * @param edges
     * @param edge
     */
    public static void setRelationShip(List<WiklRelationInfo> edges, Edge edge) {

        Object name = edge.property("name").value();

        Object type = edge.property("type").value();

        Object date = edge.property("date").value();
        Date date1 = (Date) date;
        //处理关系
        WiklRelationInfo relationship = new WiklRelationInfo();

        relationship.setLabel(edge.label());
        relationship.setSource(edge.outVertex().id().toString());
        relationship.setTarget(edge.inVertex().id().toString());
        relationship.setText(name.toString());
        relationship.setType(type.toString());
        relationship.setCreateDate(date1);
        relationship.setId(edge.id().toString());
        edges.add(relationship);
    }

    /**
     * 处理关系（orientdb 原生查询结果时使用）
     *
     * @param edges
     * @param oEdge
     */
    /**
     * 处理关系（orientdb 原生查询结果时使用）
     *
     * @param edges
     * @param oEdge
     */
    public static void setConceptRelationShip(List<WiklRelationInfo> edges, OEdge oEdge) {

        //获取名称
        String name = oEdge.getProperty("name").toString();

        String type = oEdge.getProperty("type").toString();

        Object date = oEdge.getProperty("date");
        Date date1 = (Date) date;

        //获取label
        Optional<OClass> schemaType = oEdge.getSchemaType();
        String label = schemaType.get().toString();

        //获取起点
        OVertex source = oEdge.getVertex(ODirection.OUT);

        //获取终点
        OVertex target = oEdge.getVertex(ODirection.IN);

        //处理关系
        WiklRelationInfo relationship = new WiklRelationInfo();

        relationship.setLabel(label);
        relationship.setId(oEdge.getIdentity().toString());
        relationship.setSource(source.getIdentity().toString());
        relationship.setTarget(target.getIdentity().toString());
        relationship.setText(name);
        relationship.setCreateDate(date1);
        relationship.setType(type);

        edges.add(relationship);
    }

    /**
     * 处理关系（orientdb 原生查询结果时使用）
     *
     * @param edges
     * @param oEdge
     */
    public static void setRelationShip(List<WiklRelationInfo> edges, OEdge oEdge) {

        //图谱默认属性（非业务属性，详情时不展示）
        List<String> strings = Arrays.asList("frequency", "type", "version", "kngraphId", "comeFrom", "date");

        Map<String, Object> propertyMap = new HashMap<>();

        //处理关系
        WiklRelationInfo relationship = new WiklRelationInfo();

        Set<String> propertyNames = oEdge.getPropertyNames();

        Iterator<String> iterator = propertyNames.iterator();

        while (iterator.hasNext()) {
            //属性key
            String propertyKey = iterator.next();

            if (!strings.contains(propertyKey) && !("out").equals(propertyKey)&& !("in").equals(propertyKey)) {

                Object value = oEdge.getProperty(propertyKey);

                if ("date".equals(propertyKey)) {
                    relationship.setCreateDate((Date) value);
                    value = WiklDateUtil.getDay((Date) value);
                }

                propertyMap.put(propertyKey, value);

                //获取名称
                if ("name".equals(propertyKey)) {
                    relationship.setText(value + "");
                }

                //类型
                if ("type".equals(propertyKey)) {
                    relationship.setType(value + "");
                }

            }
        }

        relationship.setOthers(propertyMap);

        //获取label
        Optional<OClass> schemaType = oEdge.getSchemaType();
        String label = schemaType.get().toString();

        //获取起点
        OVertex source = oEdge.getVertex(ODirection.OUT);

        //获取终点
        OVertex target = oEdge.getVertex(ODirection.IN);

        relationship.setLabel(label);
        relationship.setId(oEdge.getIdentity().toString());
        relationship.setSource(source.getIdentity().toString());
        relationship.setTarget(target.getIdentity().toString());

        edges.add(relationship);
    }
}
