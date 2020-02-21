package top.wikl.janusgraph.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.PropertyKey;
import org.janusgraph.core.VertexLabel;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.core.schema.SchemaAction;
import org.janusgraph.graphdb.database.management.ManagementSystem;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.wikl.janusgraph.model.JanusCreateNode;
import top.wikl.janusgraph.model.JanusSearchParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author XYL
 * @title: JanusGraphController
 * @description: TODO
 * @date 2019/12/31 15:56
 * @return
 * @since V1.0
 */
@Slf4j
@RequestMapping("/janus")
@RestController
public class JanusGraphController {

    @Resource
    private JanusGraph graph;

    /**
     * 新建点
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/12/31 16:34
     * @since V1.0
     */
    @PostMapping("/create")
    public void node(@RequestBody JanusCreateNode janusCreateNode) {

        String label = janusCreateNode.getLabel();

        List<String> property = janusCreateNode.getProperty();

        Map<String, String> properties = janusCreateNode.getProperties();

        //创建schema
        this.schema(label, property);

        //创建索引
        this.index(label, property);

        //新增数据
        this.addV(label, properties);
    }

    /**
     * 根据条件查询点
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/12/31 16:43
     * @since V1.0
     */
    @PostMapping("/search")
    public void search(@RequestBody JanusSearchParam janusSearchParam) {

        String label = janusSearchParam.getLabel();

        Map<String, String> conditions = janusSearchParam.getConditions();

        Vertex vertex;

        GraphTraversalSource g = graph.traversal();

        GraphTraversal<Vertex, Vertex> traversal = g.V(label);

        if (Objects.nonNull(conditions)) {

            for (Map.Entry<String, String> entry : conditions.entrySet()) {

                String key = entry.getKey();

                String value = entry.getValue();

                traversal.property(key, value);
            }
            vertex = traversal.next();

        } else {
            vertex = traversal.next();
        }

        String label1 = vertex.label();

        Object id = vertex.id();

        log.info("节点id --> {}，节点label --> {}，节点信息 --> {}", id, label1, vertex.toString());

    }


    /**
     * 新建schema
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/12/31 16:12
     * @since V1.0
     */
    public void schema(String label, List<String> properties) {

        graph.tx().rollback();

        JanusGraphManagement mgmt = graph.openManagement();

        //节点label
        VertexLabel vertexLabel = mgmt.makeVertexLabel(label).make();

        //处理属性
        for (String property : properties) {
            PropertyKey make = mgmt.makePropertyKey(property).dataType(String.class).make();
        }

        //事务提交
        mgmt.commit();

        log.info("节点label --> {}，属性集合 --> {}", vertexLabel, properties.toString());

    }

    /**
     * 创建索引
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/12/31 16:19
     * @since V1.0
     */
    public void index(String label, List<String> properties) {

        try {
            graph.tx().rollback();

            JanusGraphManagement mgmt = graph.openManagement();

            VertexLabel vertexLabel = mgmt.getVertexLabel(label);

            JanusGraphManagement.IndexBuilder builder = mgmt.buildIndex("labelAndProperty", Vertex.class);

            for (String property : properties) {

                PropertyKey propertyKey = mgmt.getPropertyKey(property);

                builder.addKey(propertyKey);
            }

            builder.indexOnly(vertexLabel).buildCompositeIndex();

            mgmt.commit();

            //Wait for the index to become available
            ManagementSystem.awaitGraphIndexStatus(graph, "labelAndProperty").call();

            //Reindex the existing data
            mgmt.updateIndex(mgmt.getGraphIndex("labelAndProperty"), SchemaAction.REINDEX).get();

            mgmt.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 新增点
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/12/31 16:24
     * @since V1.0
     */
    public void addV(String label, Map<String, String> properties) {

        try {
            GraphTraversalSource g = graph.traversal();

            Vertex vertex = g.addV(label).next();

            for (Map.Entry<String, String> entry : properties.entrySet()) {

                String key = entry.getKey();

                String value = entry.getValue();

                vertex.property(key, value);
            }

            g.close();

            graph.tx().commit();

            Object id = vertex.id();

            String nodeLabel = vertex.label();

            log.info("节点ID --> {}，节点label --> {}", id, nodeLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
