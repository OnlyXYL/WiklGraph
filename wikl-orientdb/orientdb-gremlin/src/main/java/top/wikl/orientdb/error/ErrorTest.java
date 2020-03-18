package top.wikl.orientdb.error;

import com.orientechnologies.orient.core.db.ODatabaseDocumentInternal;
import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.index.OIndexException;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import top.wikl.orientdb.error.model.KgConcept;
import top.wikl.orientdb.error.model.KgInstanceNode;
import top.wikl.orientdb.error.model.KgProperty;
import top.wikl.orientdb.error.model.KgPropertyDataType;
import top.wikl.orientdb.error.utils.KgGraphUtils;

import java.util.*;

/**
 * README:
 * <p>
 * you should have some data in those list
 * <p>
 * kgConcepts
 * <p>
 * instanceNodes
 *
 * @author XYL
 * @title: ErrorTest
 * @description: TODO
 * @date 2020/3/13 18:09
 * @return
 * @since V1.0
 */
public class ErrorTest {

    public static void main(String[] args) {

        //should have data
        ArrayList<KgConcept> kgConcepts = new ArrayList<>();

        //should have data
        ArrayList<KgInstanceNode> instanceNodes = new ArrayList<>();

        int graphId = 1;

        //create schema
        createInstanceNodeSchema(kgConcepts, null);

        //create node
        insertInstanceNodes(instanceNodes, graphId, null);

    }

    /**
     * create nodes
     *
     * @param nodes
     * @param graphId
     * @param session
     * @return
     * @author XYL
     * @date 2020/3/13 18:25
     * @since V2.0
     */
    public static void insertInstanceNodes(List<KgInstanceNode> nodes, int graphId, ODatabaseSession session) {
        try {
            // 用来拼接 sql 语句
            StringBuffer buffer = new StringBuffer();
            List<Object> params = new ArrayList<>();

            for (KgInstanceNode node : nodes) {
                // 节点属性
                Map<String, Object> propertyMap = node.getProperties();

                String sql = "UPDATE " + node.getLabel() + " SET graphId = ?, id = ?, label = '" + node.getLabel() + "', date = ? UPSERT WHERE id = ?;";

                params.add(graphId);
                params.add(node.getId());

                if (!Objects.isNull(propertyMap)) {

                    // 对属性进行拼接
                    List<String> props = new ArrayList<>(propertyMap.size());

                    for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {

                        if (entry.getValue() != null) {

                            props.add(entry.getKey() + " = ? ");

                            params.add(entry.getValue());

                        }
                    }
                    sql = "UPDATE " + node.getLabel() + " SET graphId = ?, id = ?, label = '" + node.getLabel() + "', " + String.join(",", props) + ", date = ? UPSERT WHERE id = ?;";
                }
                params.add(new Date());
                params.add(node.getId());

                buffer.append(sql);
            }

            String script = "begin;" + buffer.toString() + "commit;";

            ODatabaseRecordThreadLocal.instance().set((ODatabaseDocumentInternal) session);

            OResultSet resultSet = session.execute("sql", script, params.toArray());

            resultSet.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * create schema
     *
     * @param concepts
     * @param session
     * @return
     * @author XYL
     * @date 2020/3/13 18:24
     * @since V2.0
     */
    public static void createInstanceNodeSchema(List<KgConcept> concepts, ODatabaseSession session) {
        if (concepts != null) {
            ODatabaseRecordThreadLocal.instance().set((ODatabaseDocumentInternal) session);
            session.getMetadata().reload();
            OSchema schema = session.getMetadata().getSchema();
            // 遍历概念，创建概念对应的实例
            for (KgConcept concept : concepts) {

                boolean exists = schema.existsClass(concept.getLabel());

                if (!exists) {
                    OClass instance = session.createClass(concept.getLabel(), KgGraphUtils.getInstanceSchema(concept.getGraphId()));
                    instance.createProperty("id", OType.STRING);
                    instance.createIndex("INDEX_ID_" + concept.getLabel(), OClass.INDEX_TYPE.UNIQUE_HASH_INDEX, "id");

                    for (KgProperty property : concept.getProperties()) {
                        String name = property.getLabel();
                        OType oType = getOType(property.getDataType());
                        instance.createProperty(name, oType);
                        try {
                            instance.createIndex("INDEX_" + concept.getLabel() + "_" + name, OClass.INDEX_TYPE.NOTUNIQUE_HASH_INDEX, name);
                        } catch (OIndexException e) {
                            e.printStackTrace();
                            System.out.println("create index error");
                        }
                    }
                    System.out.println("orientdb ==> create instance node class:" + concept.getLabel());
                }
            }
        }
    }

    private static OType getOType(KgPropertyDataType type) {
        switch (type) {
            case DATE:
                return OType.DATETIME;
            case DIGIT:
                return OType.DOUBLE;
            default:
                return OType.STRING;
        }
    }

}
