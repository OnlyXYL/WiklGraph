package top.wikl.neo4j.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.*;
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
    public void createNodes(List<Map> start, List<Map> end) {
        Session session = driver.session();

        Map<String, Object> startParams = new HashMap<>();

        startParams.put("start", start);

        Result StartTransaction = session.writeTransaction(x -> {

            String cypher = " UNWIND $start as map " + "\n\t"
                    + " CREATE (n:Student) " + "\n\t"
                    + " SET n = map ;" + "\n\t";

            System.out.println("cypher ==> " + cypher);

            Result result = x.run(cypher, startParams);

            return result;

        });

        Map<String, Object> endParams = new HashMap<>();

        endParams.put("end", end);

        Result endTransaction = session.writeTransaction(x -> {

            String cypher = " UNWIND $end as map " + "\n\t"
                    + " CREATE (n: Teacher) " + "\n\t"
                    + " SET n = map ;" + "\n\t";

            System.out.println("cypher ==> " + cypher);

            Result result = x.run(cypher, endParams);

            return result;

        });

        //创建关系
        HashMap<String, Object> relationShip = new HashMap<>();

        relationShip.put("start", "Student");
        relationShip.put("end", "Teacher");
        relationShip.put("type", "TEACHER_OF");

        this.createRelationShip(relationShip);


  /*      while (transaction.hasNext()) {

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
*/
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
}
