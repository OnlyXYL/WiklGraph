package top.wikl.neo4j.utils;

import org.neo4j.driver.Driver;
import org.springframework.stereotype.Component;
import top.wikl.neo4j.NetUtils;
import top.wikl.neo4j.entity.Value;
import top.wikl.neo4j.entity.result.Result;
import top.wikl.neo4j.service.assis.Neo4jServiceAssist;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/4/1 0001 21:26
 */
@Component
public class Neo4jUtils {

    @Resource
    private Neo4jServiceAssist neo4jServiceAssist;

    @Resource
    private Driver driver;

    public Result<List<Map<String, Value>>> query(String cypherQL) {
        Map<String, Object> paras = new HashMap<>(2);
        paras.put("cypherQL", cypherQL);
        Result<List<Map<String, Value>>> result = this.execute(paras);
        return result;
    }

    public Result<List<Map<String, Value>>> execute(String cypherQL, Map<String, Object> params) {
        Map<String, Object> paras = new HashMap<>(3);
        paras.put("cypherQL", cypherQL);
        paras.put("map", params);
        Result<List<Map<String, Value>>> result = this.execute(paras);
        return result;
    }

    /**
     * query cypher
     *
     * @param param
     * @return top.wikl.neo4j.entity.result.Result<java.util.List < java.util.Map < java.lang.String, top.wikl.neo4j.entity.Value>>>
     * @author XYL
     * @since 22:26 2021/4/1 0001
     **/
    private Result<List<Map<String, top.wikl.neo4j.entity.Value>>> query(Map<String, Object> param) {
        Object cqlStrObj = param.get("cypherQL");
        String cqlStr = Optional.ofNullable(cqlStrObj).map(Object::toString).orElse(null);

        Object mapObj = param.get("map");
        Map<String, Object> map = Optional.ofNullable(mapObj).map(o -> (Map<String, Object>) o).orElse(null);
        System.out.println("---client ip:" + NetUtils.getRealIp() + " cql:" + cqlStr.substring(0, cqlStr.length() >= 100 ? 100 : cqlStr.length()) + " ...");
        //执行cql
        Result rs_execute = neo4jServiceAssist.toExecute(driver, "query", cqlStr, map, (cql, mp, tx) -> {

            org.neo4j.driver.Result rs;
            if (mp == null)
                rs = tx.run(cql);
            else
                rs = tx.run(cql, mp);
            return neo4jServiceAssist.packResult(rs);
        });
        return rs_execute;
    }

    /**
     * execute cypher
     *
     * @param param
     * @return top.wikl.neo4j.entity.result.Result<java.util.List < java.util.Map < java.lang.String, top.wikl.neo4j.entity.Value>>>
     * @author XYL
     * @since 22:26 2021/4/1 0001
     **/
    private Result<List<Map<String, top.wikl.neo4j.entity.Value>>> execute(Map<String, Object> param) {
        Object cqlStrObj = param.get("cypherQL");
        String cqlStr = Optional.ofNullable(cqlStrObj).map(Object::toString).orElse(null);

        Object mapObj = param.get("map");
        Map<String, Object> map = Optional.ofNullable(mapObj).map(o -> (Map<String, Object>) o).orElse(null);
        System.out.println("---client ip:" + NetUtils.getRealIp() + " cql:" + cqlStr.substring(0, cqlStr.length() >= 100 ? 100 : cqlStr.length()) + " ...");
        //执行cql
        Result rs_execute = neo4jServiceAssist.toExecute(driver, "execute", cqlStr, map, (cql, mp, tx) -> {

            org.neo4j.driver.Result rs;
            if (mp == null)
                rs = tx.run(cql);
            else
                rs = tx.run(cql, mp);
            return neo4jServiceAssist.packResult(rs);
        });
        return rs_execute;
    }

}
