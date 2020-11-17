package top.wikl.neo4j.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.internal.value.NodeValue;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.wikl.annotion.WiklMethodLog;
import top.wikl.entity.graph.input.InInput;
import top.wikl.entity.graph.output.WiklNodeInfo;
import top.wikl.entity.graph.output.WiklResultData;
import top.wikl.enums.log.LogLevel;
import top.wikl.neo4j.service.Neo4jService;
import top.wikl.properties.PropertiesUtil;

import javax.annotation.Resource;
import java.util.*;

/**
 * Map<String, Object> metadata = new HashMap<>();
 * metadata.put("type", "update name");
 * <p>
 * TransactionConfig config = TransactionConfig.builder()
 * .withTimeout(Duration.ofSeconds(3))
 * .withMetadata(metadata)
 * .build();
 * <p>
 * Map<String, Object> parameters = new HashMap<>();
 * parameters.put("myNameParam", "Bob");
 * <p>
 * StatementResult cursor = session.run("MATCH (n) WHERE n.name = {myNameParam} RETURN (n)", parameters, config);
 * <p>
 * Statement statement = new Statement("MATCH (n) WHERE n.name=$myNameParam RETURN n.age");
 * <p>
 * StatementResult cursor1 = session.run(statement.withParameters(Values.parameters("myNameParam", "Bob")));
 *
 * @author XYL
 * @title: Neo4jServiceImpl
 * @description: 【neo4j-jdbc】
 * @date 2019/10/30 19:55
 * @return
 * @since V1.0
 */
@Slf4j
@Service
public class Neo4jServiceImpl implements Neo4jService {

    @Resource
    private Driver driver;

    @WiklMethodLog(description = "查询单个点", level = LogLevel.DEBUG)
    @Override
    public List<WiklNodeInfo> searchOneNode(InInput inInput) {

        ArrayList<WiklNodeInfo> list = new ArrayList<>();

        Session session = driver.session();

        //label
        String label = inInput.getLabel();

        //查询条件
        Map<String, String> conditions = inInput.getConditions();

        List<WiklNodeInfo> stream = session.readTransaction(tx -> {

            StringBuilder cypherSql = new StringBuilder();

            cypherSql.append("match p = (start:" + label);

            if (Objects.nonNull(conditions)) {

                int index = 0;

                for (Map.Entry<String, String> entry : conditions.entrySet()) {

                    String key = entry.getKey();

                    String value = entry.getValue();

                    if (index == 0) {
                        cypherSql.append("{ " + key + ":\"" + value + "\"");
                    } else {
                        cypherSql.append("," + key + ":\"" + value + "\"");
                    }

                    index++;

                }

                cypherSql.append("}");

            }

            cypherSql.append(") return start limit " + inInput.getLimit());

            log.info("cypher 语句：" + cypherSql);

            Result run = tx.run(cypherSql.toString());

            while (run.hasNext()) {

                WiklNodeInfo wiklNodeInfo = new WiklNodeInfo();

                Record next = run.next();

                List<String> keys = next.keys();

                for (String key : keys) {

                    Value value = next.get(key);

                    if (value instanceof NodeValue) {

                        NodeValue nodeValue = (NodeValue) value;

                        Node node = nodeValue.asNode();

                        long id = node.id();

                        Iterable<String> labels = node.labels();

                        List<String> labelList = new ArrayList<>();

                        Iterator<String> iterator = labels.iterator();

                        iterator.forEachRemaining(labelList::add);

                        Iterable<String> keysList = node.keys();

                        Map<String, Object> propertyList = new HashMap<>(16);

                        keysList.forEach((x) -> {
                            propertyList.put(x, node.get(x).asString());
                        });

                        wiklNodeInfo.setId(String.valueOf(id));
                        wiklNodeInfo.setLabels(labelList);
                        wiklNodeInfo.setProperties(propertyList);

                        BeanUtils.copyProperties(node, wiklNodeInfo, PropertiesUtil.getNullPropertyNames(node));

                        list.add(wiklNodeInfo);
                    }
                }
            }

            return list;

        });

        session.close();

        return stream;

    }

    @Override
    public WiklResultData searchByDepth(InInput inInput) {

        Session session = driver.session();

        Map<String, String> conditions = inInput.getConditions();

        Integer depth = inInput.getDepth();

        //label
        String label = inInput.getLabel();

        StringBuilder cypterSql = new StringBuilder();

//        cypterSql.append("match p=(person:" + label + "{name:\"" + condition + "\"})-[*.." + depth + "]-(movie:" + label + ") \n"  +
//                "                                return person,movie,extract(n in relationships(p) | (({source:id(startNode(n)),target:id(endNode(n)),relationShip:type(n)}))) as edges");

        Object transaction = session.readTransaction(tx -> {

            StringBuilder cypherSql = new StringBuilder();

            Result result = tx.run(cypherSql.toString());

            return result;
        });

        return null;
    }

    @Override
    public void createNode(Map<String, Object> params) {

        Session session = driver.session();

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("params", params);

        Result transaction = session.writeTransaction(x -> {

            String cypher = " CREATE (n: Person $params) return n";

            System.out.println("cypher ==> " + cypher);

            Result result = x.run(cypher, parameters);

            return result;

        });

        while (transaction.hasNext()) {

            Record next = transaction.next();

            List<String> keys = next.keys();

            keys.stream().forEach(x -> {

                Value value = next.get(x);

                String value_type = value.type().name();

                if ("NODE".equals(value_type)) {
                    Node node = value.asNode();

                    System.out.println("节点  ==》 " + node.toString());

                } else {
                    Relationship relationship = value.asRelationship();

                    System.out.println("关系  ==》 " + relationship.toString());
                }

            });
        }
    }

    @Override
    public void createNodesUnwind(List<Map<String, Object>> nodes, String label) {
        Session session = driver.session();

        Map<String, Object> params = new HashMap<>(1);

        params.put("nodes", nodes);

        /**
         * MERGE 时，需要加上唯一约束
         */
        Result StartTransaction = session.writeTransaction(x -> {

            String cypher = " UNWIND $nodes as map " + "\n\t"
                    + " MERGE (n:" + label + "{name: map.name}) " + "\n\t"
                    + " SET n = map ;" + "\n\t";

            System.out.println("cypher ==> " + cypher);

            Result result = x.run(cypher, params);

            return result;

        });

        session.close();
    }

    @Override
    public void createRelationShip(Map<String, Object> params) {

        Session session = driver.session();

        Result transaction = session.writeTransaction(x -> {

            //学生---老师
            String cypher = "   MATCH (s:Student),(e:Teacher)" + "\n\t"
                    + "   WHERE s.teacher = e.name " + "\n\t"
                    + "    CREATE (s)-[:TEACHER_OF]->(e)";

            System.out.println("cypher ==> " + cypher);

            Result result = x.run(cypher, params);

            return result;

        });

        session.close();

    }

    @Override
    public void createConstraint(String constraint_name, String label) {
        StringBuffer constraint_cypher = new StringBuffer();
        constraint_cypher.append("CREATE CONSTRAINT ");
        constraint_cypher.append(constraint_name);
        constraint_cypher.append("_name ");
        constraint_cypher.append("IF NOT EXISTS ");
        constraint_cypher.append("ON (n:");
        constraint_cypher.append(label);
        constraint_cypher.append(") ");
        constraint_cypher.append("ASSERT n.name IS UNIQUE\n");


        Session session = driver.session();

        Result transaction = session.writeTransaction(x -> {
            System.out.println("cypher ==> " + constraint_cypher.toString());

            Result result = x.run(constraint_cypher.toString());

            return result;

        });

        session.close();

    }

    @Override
    public void createBatch() {
        HashMap<String, Object> params = new HashMap<>();

        StringBuilder builder = new StringBuilder();

        for (int i = 1; i < 5; i++) {

            HashMap<String, Object> map = new HashMap<>();
            map.put("name", "张三" + i);
            map.put("id", i);
            map.put("age", i);

            String paramKey = String.format("%s_%s_params", "User", i);

            String idParamKey = String.format("%s_%s_id", "User", i);

            String idParamLabel = String.format("%s_label", "User");

            params.put(paramKey, map);
            params.put(idParamLabel, i);
            params.put(idParamKey, "User");

            String format = String.format("merge (n:%s:%s{id:$%s,label:$%s}) SET n += $%s;\n", "User", "Person", idParamKey, idParamLabel, paramKey);

            builder.append(format);
        }

        Session session = null;
        try {
            session = driver.session();

            Result transaction = session.writeTransaction(x -> {
                System.out.println("cypher ==> " + builder.toString());

                Result result = x.run(builder.toString(),params);

                return result;

            });
        } catch (Exception e) {
            e.printStackTrace();

        }

        session.close();

    }
}
