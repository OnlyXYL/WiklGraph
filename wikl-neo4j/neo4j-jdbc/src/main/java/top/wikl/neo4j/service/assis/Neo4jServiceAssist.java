package top.wikl.neo4j.service.assis;

import com.google.common.collect.Lists;
import org.neo4j.driver.Record;
import org.neo4j.driver.*;
import org.neo4j.driver.exceptions.ServiceUnavailableException;
import org.neo4j.driver.internal.InternalPoint2D;
import org.neo4j.driver.internal.InternalPoint3D;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;
import org.neo4j.driver.types.Point;
import org.neo4j.driver.types.Relationship;
import org.springframework.stereotype.Component;
import top.wikl.neo4j.Neo4jConvertor;
import top.wikl.neo4j.entity.Neo4jType;
import top.wikl.neo4j.entity.Value;
import top.wikl.neo4j.entity.result.Result;
import top.wikl.neo4j.entity.result.ResultEnum;
import top.wikl.neo4j.entity.result.ResultUtil;
import top.wikl.neo4j.service.CQLFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DengYaJun
 * @ClassName: Neo4jServiceAssist
 * @Description: Neo4jService辅助类
 * @date: 2020/8/6 18:10
 */
@Component
public class Neo4jServiceAssist {

    /**
     * 设置List值的长度
     *
     * @param value_orign
     * @param value
     */
    private void setSizeAndEmpty(org.neo4j.driver.Value value_orign, Value value) {
        try {
            value.setSize(value_orign.size());
        } catch (Exception e) {
            value.setEmpty(false);
            value.setSize(1);
        }
    }

    /**
     * 执行查询
     *
     * @param model
     * @param cql
     * @param cqlFun
     * @return
     */

    public Result<List<Map<String, top.wikl.neo4j.entity.Value>>> toExecute(Driver driver,
                                                                            String model,
                                                                            String cql,
                                                                            Map<String, Object> map,
                                                                            CQLFunction cqlFun) {
        Session session = null;
        try {
            session = driver.session(SessionConfig.builder().withDefaultAccessMode(AccessMode.WRITE).build());
            if (model.equals("query"))
                return session.readTransaction(tx -> cqlFun.execute(cql, map, tx));
            else
                return session.writeTransaction(tx -> cqlFun.execute(cql, map, tx));
        } catch (ServiceUnavailableException exception) {
            return ResultUtil.error(ResultEnum.ERROR, 10010, exception.getMessage());
        } catch (org.neo4j.driver.exceptions.ClientException exception) {
            System.out.println("--error by ClientException");
            return ResultUtil.error(ResultEnum.ERROR, exception.getMessage());
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    /**
     * neo4j 结果转换
     *
     * @param result
     * @return
     */
    public Result<List<Map<String, Value>>> packResult(org.neo4j.driver.Result result) {
        List<String> keys = result.keys();
        List<Record> result_ls = result.list();
        List<Map<String, Value>> rs = new ArrayList<>();
        Neo4jConvertor convertor = Neo4jConvertor.build();
        for (int j = 0; j < result_ls.size(); j++) {
            Map<String, Value> map1 = new HashMap<>();
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                try {
                    org.neo4j.driver.Value value_orign = result_ls.get(j).get(key);
                    String typeName = value_orign.type().name();
                    Value value = new Value();
                    setSizeAndEmpty(value_orign, value);
                    value.setKeys(Lists.newArrayList(value_orign.keys()));
                    switch (typeName) {
                        case Neo4jType.NODE->{
                            Node node_origin = value_orign.asNode();
                            value.setType(Neo4jType.NODE);
                            value.setValue(Neo4jConvertor.build().convertNeo4jNodeToNode(node_origin));
                        }

                        case Neo4jType.RELATIONSHIP -> {
                            Relationship relationship_origin = value_orign.asRelationship();
                            value.setType(Neo4jType.RELATIONSHIP);
                            value.setValue(Neo4jConvertor.build().convertNeo4jRelationshipToRelationship(relationship_origin));
                        }
                        case Neo4jType.PATH -> {
                            Path path_origin = value_orign.asPath();
                            top.wikl.neo4j.entity.Path path = new top.wikl.neo4j.entity.Path();
                            //设置path
                            path_origin.nodes().forEach(n -> path.addNode(convertor.convertNeo4jNodeToNode(n)));
                            path_origin.relationships().forEach(r -> path.addRelationship(convertor.convertNeo4jRelationshipToRelationship(r)));
                            path.setStartNode(convertor.convertNeo4jNodeToNode(path_origin.start()));
                            path.setEndNode(convertor.convertNeo4jNodeToNode(path_origin.end()));
                            value.setType(Neo4jType.PATH);
                            value.setValue(path);
                        }
                        case Neo4jType.LISTOFANY -> {
                            List list_origin = value_orign.asList();
                            List list = new ArrayList();
                            list_origin.forEach(l -> {
                                if (l instanceof Node) {
                                    list.add(convertor.convertNeo4jNodeToNode((Node) l));
                                } else if (l instanceof Relationship) {
                                    list.add(convertor.convertNeo4jRelationshipToRelationship((Relationship) l));
                                } else
                                    list.add(l.toString());

                            });
                            value.setEmpty(list.isEmpty());
                            value.setType(Neo4jType.LISTOFANY);
                            value.setValue(list);
                        }
                        case "POINT" -> {
                            Point point_origin = value_orign.asPoint();
                            if (point_origin instanceof InternalPoint2D) {
                                value.setType(Neo4jType.POINT2D);
                                value.setValue(convertor.convertNeo4jPoint2DToPoint2D(point_origin));
                            } else if (point_origin instanceof InternalPoint3D) {
                                value.setType(Neo4jType.POINT3D);
                                value.setValue(convertor.convertNeo4jPoint2DToPoint3D(point_origin));
                            }
                        }
                        case Neo4jType.BYTEARRAY -> {
                            value.setType(Neo4jType.BYTEARRAY);
                            value.setValue(value_orign.asByteArray());
                        }
                        case Neo4jType.MAP -> {
                            value.setType(Neo4jType.MAP);
                            value.setValue(value_orign.asMap());
                        }
                        case Neo4jType.LIST -> {
                            value.setType(Neo4jType.LIST);
                            value.setValue(value_orign.asList());
                        }
                        case Neo4jType.ISODURATION -> {
                            value.setType(Neo4jType.ISODURATION);
                            value.setValue(convertor.convertNeo4jDurationToDuration(value_orign.asIsoDuration()));
                        }
                        case Neo4jType.DOUBLE -> {
                            value.setType(Neo4jType.DOUBLE);
                            value.setValue(value_orign.asDouble());
                        }
                        case Neo4jType.FLOAT -> {
                            value.setType(Neo4jType.FLOAT);
                            value.setValue(value_orign.asFloat());
                        }
                        case Neo4jType.INTEGER -> {
                            value.setType(Neo4jType.INTEGER);
                            value.setValue(value_orign.asInt());
                        }
                        case Neo4jType.BOOLEAN -> {
                            value.setType(Neo4jType.BOOLEAN);
                            value.setValue(value_orign.asBoolean());
                        }
                        case Neo4jType.LONG -> {
                            value.setType(Neo4jType.LONG);
                            value.setValue(value_orign.asLong());
                        }
                        case Neo4jType.NUMBER -> {
                            value.setType(Neo4jType.NUMBER);
                            value.setValue(value_orign.asNumber());
                        }
                        case Neo4jType.LOCALDATE -> {
                            value.setType(Neo4jType.LOCALDATE);
                            value.setValue(value_orign.asLocalDate());
                        }
                        case Neo4jType.LOCALTIME -> {
                            value.setType(Neo4jType.LOCALTIME);
                            value.setValue(value_orign.asLocalTime());
                            map1.put(key, value);
                        }
                        case Neo4jType.LOCALDATETIME -> {
                            value.setType(Neo4jType.LOCALDATETIME);
                            value.setValue(value_orign.asLocalDateTime());
                        }
                        case Neo4jType.OFFSETTIME -> {
                            value.setType(Neo4jType.OFFSETTIME);
                            value.setValue(value_orign.asOffsetTime());
                        }
                        case Neo4jType.OFFSETDATETIME -> {
                            value.setType(Neo4jType.OFFSETDATETIME);
                            value.setValue(value_orign.asOffsetDateTime());
                        }
                        default -> {
                            value.setType(Neo4jType.STRING);
                            value.setValue(result_ls.get(j).get(key).toString());
                        }
                    }
                    map1.put(key, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            rs.add(map1);
        }
        return ResultUtil.success(rs);
    }
}
