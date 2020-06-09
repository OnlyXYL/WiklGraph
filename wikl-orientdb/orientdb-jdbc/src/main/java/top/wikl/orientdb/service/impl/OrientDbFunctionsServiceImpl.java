package top.wikl.orientdb.service.impl;

import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.record.OEdge;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultInternal;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.wikl.entity.graph.input.*;
import top.wikl.entity.graph.input.vo.*;
import top.wikl.exception.NotFoundException;
import top.wikl.orientdb.service.OrientDbFunctionsService;
import top.wikl.orientdb.utils.WiklOrientdbUtil;
import top.wikl.utils.json.JsonUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author XYL
 * @title: OrientDbFunctionsServiceImpl
 * @description: SQL Functions In OrientDb {@see https://orientdb.org/docs/3.0.x/sql/SQL-Functions.html}
 * @date 2019/10/9 10:14
 * @return
 * @since V1.0
 */
@Slf4j
@Service
public class OrientDbFunctionsServiceImpl implements OrientDbFunctionsService {

    @Resource
    private ODatabasePool pool;

    @Override
    public String in(InInput inInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //查询条件
        Map<String, String> conditions = inInput.getConditions();

        //label
        String label = inInput.getLabel();

        //获取请求参数
        List<InInputParamVo> input = inInput.getInput();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (InInputParamVo paramVo : input) {

                String relationType = paramVo.getLabelType();

                if (index == 0) {
                    sql.append("expand(in('" + relationType + "')) as " + relationType);
                } else {
                    sql.append(",expand(in('" + relationType + "')) as " + relationType);
                }

                index++;

            }

        } else {
            sql.append(" expand(in()) ");
        }

        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + inInput.getLimit());

        log.info("The Sql Of in is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(sql.toString(), label, true);

        return executeSql;
    }

    @Override
    public String out(OutInput outInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //查询条件
        Map<String, String> conditions = outInput.getConditions();

        //label
        String label = outInput.getLabel();

        //获取请求参数
        List<OutInputParamVo> input = outInput.getInput();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (OutInputParamVo paramVo : input) {

                String relationType = paramVo.getRelationType();

                if (index == 0) {
                    sql.append("expand(out('" + relationType + "')) as " + relationType);
                } else {
                    sql.append(",expand(out('" + relationType + "')) as " + relationType);
                }

                index++;

            }

        } else {
            sql.append(" expand(out()) ");
        }

        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + outInput.getLimit());

        log.info("The Sql Of out is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(sql.toString(), label, true);

        return executeSql;
    }

    @Override
    public String both(BothInput bothInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //查询条件
        Map<String, String> conditions = bothInput.getConditions();

        //label
        String label = bothInput.getLabel();

        //获取请求参数
        List<BothInputParamVo> input = bothInput.getInput();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (BothInputParamVo paramVo : input) {

                String relationType = paramVo.getRelationType();

                if (index == 0) {
                    sql.append("expand(both('" + relationType + "')) as " + relationType);
                } else {
                    sql.append(",expand(both('" + relationType + "')) as " + relationType);
                }

                index++;

            }

        } else {
            sql.append(" expand(both()) ");
        }


        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + bothInput.getLimit());

        log.info("The Sql Of both is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(sql.toString(), label, true);

        return executeSql;
    }

    @Override
    public String outE(OutEInput outEInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //查询条件
        Map<String, String> conditions = outEInput.getConditions();

        //label
        String label = outEInput.getLabel();

        //获取请求参数
        List<OutEInputParamVo> input = outEInput.getInput();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (OutEInputParamVo paramVo : input) {

                String relationType = paramVo.getRelationType();

                if (index == 0) {
                    sql.append("expand(outE('" + relationType + "')) as " + relationType);
                } else {
                    sql.append(",expand(outE('" + relationType + "')) as " + relationType);
                }

                index++;

            }

        } else {
            sql.append(" expand(outE()) ");
        }


        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + outEInput.getLimit());

        log.info("The Sql Of outE is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(sql.toString(), label, false);

        return executeSql;
    }

    @Override
    public String inE(InEInput inEInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //查询条件
        Map<String, String> conditions = inEInput.getConditions();

        //label
        String label = inEInput.getLabel();

        //获取请求参数
        List<InEInputParamVo> input = inEInput.getInput();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (InEInputParamVo paramVo : input) {

                String relationType = paramVo.getRelationType();

                if (index == 0) {
                    sql.append("expand(inE('" + relationType + "')) as " + relationType);
                } else {
                    sql.append(",expand(inE('" + relationType + "')) as " + relationType);
                }

                index++;

            }

        } else {
            sql.append(" expand(inE()) ");
        }


        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + inEInput.getLimit());

        log.info("The Sql Of inE is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(sql.toString(), label, false);

        return executeSql;
    }

    @Override
    public String bothE(BothEInput bothEInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //查询条件
        Map<String, String> conditions = bothEInput.getConditions();

        //label
        String label = bothEInput.getLabel();

        //获取请求参数
        List<BothEInputParamVo> input = bothEInput.getInput();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (BothEInputParamVo paramVo : input) {

                String relationType = paramVo.getRelationType();

                if (index == 0) {
                    sql.append("expand(bothE('" + relationType + "')) as " + relationType);
                } else {
                    sql.append(",expand(bothE('" + relationType + "')) as " + relationType);
                }

                index++;

            }

        } else {
            sql.append(" expand(bothE()) ");
        }


        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + bothEInput.getLimit());

        log.info("The Sql Of bothE is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(sql.toString(), label, false);

        return executeSql;
    }

    @Override
    public String bothV(BothVInput bothVInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //查询条件
        Map<String, String> conditions = bothVInput.getConditions();

        //label
        String label = bothVInput.getLabel();

        //获取请求参数
        List<BothVInputParamVo> input = bothVInput.getInput();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (BothVInputParamVo paramVo : input) {

                String type = paramVo.getType();

                if (index == 0) {
                    sql.append("expand(bothV('" + type + "')) as " + type);
                } else {
                    sql.append(",expand(bothV('" + type + "')) as " + type);
                }

                index++;

            }

        } else {
            sql.append(" expand(bothV()) ");
        }


        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + bothVInput.getLimit());

        log.info("The Sql Of bothV is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(sql.toString(), label, false);

        return executeSql;
    }

    @Override
    public String outV(OutVInput outVInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //查询条件
        Map<String, String> conditions = outVInput.getConditions();

        //label
        String label = outVInput.getLabel();

        //获取请求参数
        List<OutVInputParamVo> input = outVInput.getInput();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (OutVInputParamVo paramVo : input) {

                String type = paramVo.getType();

                if (index == 0) {
                    sql.append("expand(outV('" + type + "')) as " + type);
                } else {
                    sql.append(",expand(outV('" + type + "')) as " + type);
                }

                index++;

            }

        } else {
            sql.append(" expand(outV()) ");
        }


        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + outVInput.getLimit());

        log.info("The Sql Of outV is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(sql.toString(), label, false);

        return executeSql;
    }

    @Override
    public String inV(InVInput inVInput) {

        //SQL
        StringBuilder sql = new StringBuilder();

        sql.append("select ");

        //查询条件
        Map<String, String> conditions = inVInput.getConditions();

        //label
        String label = inVInput.getLabel();

        //获取请求参数
        List<InVInputParamVo> input = inVInput.getInput();

        if (Objects.nonNull(input)) {

            int index = 0;

            for (InVInputParamVo paramVo : input) {

                String type = paramVo.getType();

                if (index == 0) {
                    sql.append("expand(inV('" + type + "')) as " + type);
                } else {
                    sql.append(",expand(inV('" + type + "')) as " + type);
                }

                index++;

            }

        } else {
            sql.append(" expand(inV()) ");
        }

        //拼接表
        sql.append(" from " + label);

        //拼接查询条件
        this.appendWhere(sql, conditions);

        //拼接个数
        sql.append(" limit " + inVInput.getLimit());

        log.info("The Sql Of inV is --> {}", sql.toString());

        //执行sql
        String executeSql = this.executeSql(sql.toString(), label, false);

        return executeSql;
    }

    /**
     * @param sql, label
     * @return void
     * @author XYL
     * @date 2019/10/14 14:38
     * @since V1.0
     */
    public String executeSql(String sql, String label, boolean isVertex) {

        List<Map<String, Object>> maps = new ArrayList<>();

        try (ODatabaseSession session = pool.acquire();) {

            //判断class是否存在
            OClass aClass = session.getClass(label);

            if (Objects.isNull(aClass)) {
                throw new NotFoundException("class not found: " + label);
            }

            OResultSet resultSet = session.command(sql);

            while (resultSet.hasNext()) {

                Map<String, Object> map = new HashMap<>(16);

                OResult next = resultSet.next();

                OResultInternal oResultInternal = (OResultInternal) next;

                if (isVertex) {

                    //获取点
                    Optional<OVertex> vertex = oResultInternal.getVertex();

                    if (!Optional.empty().equals(vertex)) {
                        OVertex oVertex = vertex.get();

                        Set<String> propertyNames = oVertex.getPropertyNames();

                        for (String property : propertyNames) {

                            if (!property.startsWith("in_") && !property.startsWith("out_")) {

                                Object oVertexProperty = oVertex.getProperty(property);

                                map.put(property, oVertexProperty);
                            }
                        }
                    }

                } else {

                    //获取点
                    Optional<OEdge> vertex = oResultInternal.getEdge();

                    if (!Optional.empty().equals(vertex)) {
                        OEdge oEdge = vertex.get();

                        Set<String> propertyNames = oEdge.getPropertyNames();

                        for (String property : propertyNames) {

                            if (!"in".equals(property) && !"out".equals(property)) {

                                Object oVertexProperty = oEdge.getProperty(property);

                                map.put(property, oVertexProperty);
                            }
                        }
                    }
                }

                maps.add(map);
            }

            //关闭资源
            WiklOrientdbUtil.close(resultSet);

        }

        String toJson = JsonUtils.parseObjToJson(maps);

        return toJson;

    }

    /**
     * 拼接sql的查询条件
     *
     * @param sql
     * @param condition
     * @return void
     * @author XYL
     * @date 2019/10/10 18:09
     * @since V1.0
     */
    public void appendWhere(StringBuilder sql, Map<String, String> condition) {

        //拼接查询条件
        if (Objects.nonNull(condition)) {

            //条件索引
            int conditionIndex = 0;

            for (Map.Entry<String, String> entry : condition.entrySet()) {

                //条件key
                String key = entry.getKey();

                //条件值
                String value = entry.getValue();

                if (conditionIndex == 0) {
                    sql.append(" where " + key + " = '" + value + "'");
                } else {
                    sql.append(" and " + key + " = '" + value + "'");
                }

                conditionIndex++;
            }
        }
    }

}
