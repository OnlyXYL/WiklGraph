package top.wikl.neo4j.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.internal.value.NodeValue;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Node;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.wikl.annotion.WiklMethodLog;
import top.wikl.entity.graph.input.InInput;
import top.wikl.entity.graph.output.WiklNodeInfo;
import top.wikl.neo4j.service.Neo4jService;
import top.wikl.properties.PropertiesUtil;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author XYL
 * @title: Neo4jServiceImpl
 * @description: TODO
 * @date 2019/10/30 19:55
 * @return
 * @since V1.0
 */
@Slf4j
@Service
public class Neo4jServiceImpl implements Neo4jService {

    @Resource
    private Driver driver;

    @WiklMethodLog
    @Override
    public List<WiklNodeInfo> searchOneNode(InInput inInput) {

        ArrayList<WiklNodeInfo> list = new ArrayList<>();

        Session session = driver.session(AccessMode.READ);

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

            StatementResult statementResult = tx.run(cypherSql.toString());

            while (statementResult.hasNext()) {

                WiklNodeInfo wiklNodeInfo = new WiklNodeInfo();

                Record next = statementResult.next();

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

                        Map<String, Object> propertyList = new HashMap<>();

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

        return stream;

    }
}