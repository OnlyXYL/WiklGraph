package top.wikl.neo4j.service;

import org.neo4j.driver.Transaction;
import top.wikl.neo4j.entity.result.Result;

import java.util.Map;

/**
 * @ClassName: CQLFunction
 * @Description:  cql回调执行
 * @date: 2020/8/6 18:10
 * @author DengYaJun
*/
@FunctionalInterface
public interface CQLFunction {
    Result execute(String cql, Map<String,Object> map, Transaction tx);
}
