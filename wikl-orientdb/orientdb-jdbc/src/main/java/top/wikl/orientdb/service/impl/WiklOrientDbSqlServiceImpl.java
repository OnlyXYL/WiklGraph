package top.wikl.orientdb.service.impl;

import com.orientechnologies.orient.core.db.ODatabase;
import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.wikl.constant.SysConstant;
import top.wikl.entity.graph.output.SingleRecordInfoResultData;
import top.wikl.entity.graph.output.WiklGraphInfo;
import top.wikl.entity.graph.output.WiklResultData;
import top.wikl.entity.graph.vo.*;
import top.wikl.lru.LRULinkedHashMap;
import top.wikl.orientdb.service.WiklOrientDbSqlService;
import top.wikl.orientdb.utils.WiklGraphResultUtil;
import top.wikl.orientdb.utils.WiklOrientdbUtil;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author XYL
 * @title: WiklOrientDbSqlServiceImpl
 * @description: OrientDb 服务
 * @date 2019/9/27 14:08
 * @return
 * @since V1.0
 */
@Slf4j
@Service
public class WiklOrientDbSqlServiceImpl implements WiklOrientDbSqlService {

    @Resource
    private LRULinkedHashMap lruLinkedHashMap;

    @Resource
    private ODatabasePool pool;

    @Override
    public SingleRecordInfoResultData searchOneInstanceNodeDetails(SearchInstanceNodeDetailsVo searchInstanceNodeDetailsVo) {

        SingleRecordInfoResultData resultData = new SingleRecordInfoResultData();

        log.info("开始从内存中获取信息，图谱id --> {}，", searchInstanceNodeDetailsVo.getKngraphId());

        LRULinkedHashMap linkedHashMap = (LRULinkedHashMap) lruLinkedHashMap.get(searchInstanceNodeDetailsVo.getKngraphId());

        //从LRU中获取数据
        this.LruGet(linkedHashMap, searchInstanceNodeDetailsVo.getId(), resultData);

        long startConnect = System.currentTimeMillis();

        log.info("执行查询语句：" + startConnect);

        String sql = "select from " + searchInstanceNodeDetailsVo.getId();

        //调用方法，执行数据库操作
        try (ODatabaseSession session = pool.acquire()) {

            //处理当前线程中数据库实例不可用情况
            ODatabase oDatabase = WiklOrientdbUtil.activeOnCurrentThread(session);

            //执行查询
            OResultSet rs = oDatabase.query(sql);

            //封装结果对象

            //处理结果集
            resultData = WiklGraphResultUtil.dealWithPropertyResult(rs);

            //关闭资源
            WiklOrientdbUtil.close(rs);

        }

        long endExecute = System.currentTimeMillis();

        log.info("语句执行结束，耗时：" + (endExecute - startConnect) + " ms");

        log.info("开始结果展示信息的处理：" + endExecute);

        //处理属性
        Map<String, Object> others = resultData.getOthers();

        Map<String, Object> map = new HashMap<>(16);

        //图谱默认属性（非业务属性，详情时不展示）
        List<String> strings = Arrays.asList("frequency", "type", "version", "kngraphId", "comeFrom", "date");

        for (Map.Entry<String, Object> entry : others.entrySet()) {

            String key = entry.getKey();
            Object value = entry.getValue();

            if ("name".equals(key)) {
                map.put("名称", value);
                continue;
            }

            if (!strings.contains(key) && !key.startsWith("out") && !key.startsWith("in")) {

                SearchOriginFieldVo searchOriginFieldVo = new SearchOriginFieldVo();

                searchOriginFieldVo.setKngraphId(searchInstanceNodeDetailsVo.getKngraphId());
                searchOriginFieldVo.setField(key);
                searchOriginFieldVo.setName(searchInstanceNodeDetailsVo.getConceptName());

                OriginFieldResultData originFieldResultData = this.searchOriginField(true, searchOriginFieldVo);

                map.put(originFieldResultData.getOriginField(), value);
            }
        }

        resultData.setOthers(map);

        resultData.setStorageLRU(true);

        long enddeal = System.currentTimeMillis();

        log.info("结果展示信息的处理结束，耗时：" + (enddeal - endExecute) + " ms");

        /**
         * 存储数据到LRU
         */
        this.LruPut(linkedHashMap, searchInstanceNodeDetailsVo.getId(), searchInstanceNodeDetailsVo.getKngraphId(), map);

        return resultData;
    }

    @Override
    public SingleRecordInfoResultData searchOneInstanceEdgeDetails(SearchInstanceEdgeDetailsVo searchInstanceEdgeDetailsVo) {

        SingleRecordInfoResultData resultData = new SingleRecordInfoResultData();

        log.info("开始从内存中获取信息，图谱id --> {}，", searchInstanceEdgeDetailsVo.getKngraphId());

        LRULinkedHashMap linkedHashMap = (LRULinkedHashMap) lruLinkedHashMap.get(searchInstanceEdgeDetailsVo.getKngraphId());

        //从LRU中获取数据
        this.LruGet(linkedHashMap, searchInstanceEdgeDetailsVo.getId(), resultData);

        long startConnect = System.currentTimeMillis();

        log.info("执行查询语句：" + startConnect);

        String sql = "MATCH {class:e, as:nodes, where:(@rid = :rid)} RETURN distinct nodes limit -1";

        //参数
        Map<String, Object> params = new HashMap<>(1);
        params.put("rid", searchInstanceEdgeDetailsVo.getId());

        //调用方法，执行数据库操作
        try (ODatabaseSession session = pool.acquire()) {

            //处理当前线程中数据库实例不可用情况
            ODatabase oDatabase = WiklOrientdbUtil.activeOnCurrentThread(session);

            //执行查询
            OResultSet rs = oDatabase.query(sql, params);

            //封装结果对象

            //处理结果集
            resultData = WiklGraphResultUtil.dealWithPropertyResult(rs);

            //关闭资源
            WiklOrientdbUtil.close(rs, null, null);

        }

        long endExecute = System.currentTimeMillis();

        log.info("语句执行结束，耗时：" + (endExecute - startConnect) + " ms");

        log.info("开始结果展示信息的处理：" + endExecute);

        //处理属性
        Map<String, Object> others = resultData.getOthers();

        Map<String, Object> map = new HashMap<>(16);

        for (Map.Entry<String, Object> entry : others.entrySet()) {

            String key = entry.getKey();
            Object value = entry.getValue();

            if ("name".equals(key)) {
                map.put("名称", value);
                continue;
            }

            SearchOriginFieldVo searchOriginFieldVo = new SearchOriginFieldVo();

            searchOriginFieldVo.setKngraphId(searchInstanceEdgeDetailsVo.getKngraphId());
            searchOriginFieldVo.setField(key);
            searchOriginFieldVo.setName(searchInstanceEdgeDetailsVo.getConceptName());

            OriginFieldResultData originFieldResultData = this.searchOriginField(false, searchOriginFieldVo);

            map.put(originFieldResultData.getOriginField(), value);

        }

        resultData.setOthers(map);

        resultData.setStorageLRU(true);

        long enddeal = System.currentTimeMillis();

        log.info("结果展示信息的处理结束，耗时：" + (enddeal - endExecute) + " ms");

        /**
         * 存储数据到LRU
         */
        this.LruPut(linkedHashMap, searchInstanceEdgeDetailsVo.getId(), searchInstanceEdgeDetailsVo.getKngraphId(), map);

        return resultData;
    }

    /**
     * 从LRU中获取数据
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 14:41
     * @since V1.0
     */
    public void LruGet(LRULinkedHashMap linkedHashMap, String Id, SingleRecordInfoResultData resultData) {

        if (!Objects.isNull(linkedHashMap)) {
            Object o = linkedHashMap.get(Id);
            if (!Objects.isNull(o)) {

                Map<String, Object> o1 = (Map<String, Object>) o;

                resultData.setOthers(o1);
                resultData.setStorageLRU(false);
            }
        }
    }

    /**
     * 存储数据到LRU
     *
     * @param linkedHashMap, id, graphId, map
     * @return void
     * @author XYL
     * @date 2019/9/27 14:22
     * @since V1.0
     */
    public void LruPut(LRULinkedHashMap linkedHashMap, String id, String graphId, Map<String, Object> map) {

        if (Objects.isNull(linkedHashMap)) {

            LRULinkedHashMap nodeInfo = new LRULinkedHashMap(600);

            nodeInfo.put(id, map);

            lruLinkedHashMap.put(graphId, nodeInfo);

        } else {

            //缓存
            linkedHashMap.put(id, map);
        }
    }

    @Override
    public WiklGraphInfo searchKngraphInstanceAndEdgeNumbers(String currentUserId, String kngraphId) {

        WiklGraphInfo kngraphInfo = new WiklGraphInfo();

        Map<String, String> params = new HashMap<>(1);

        params.put("kngraphId", kngraphId);

        String edgeCount = "select count(*) from e where kngraphId = :kngraphId and not(@class = 'ConceptEdge')";

        String nodeCount = "select count(*) from v where kngraphId = :kngraphId and not(@class = 'Concept')";

        // 调用方法执行数据库操作
        try (ODatabaseSession session = pool.acquire()) {

            //处理当前线程中数据库实例不可用情况
            ODatabase oDatabase = WiklOrientdbUtil.activeOnCurrentThread(session);

            //执行查询
            OResultSet rs_edge = oDatabase.query(edgeCount, params);

            //执行查询
            OResultSet rs_node = oDatabase.query(nodeCount, params);

            //处理结果集
            String edge = WiklGraphResultUtil.dealSingleResult(rs_edge);

            String node = WiklGraphResultUtil.dealSingleResult(rs_node);

            kngraphInfo.setEdgeNumber(Long.parseLong(edge));

            kngraphInfo.setInstanceNumber(Long.parseLong(node));

            //关闭资源
            WiklOrientdbUtil.close(rs_edge);

            WiklOrientdbUtil.close(rs_node);

        }
        return kngraphInfo;
    }

    @Override
    public WiklResultData searchOneDepthData(SearchOneDepthDataVo searchOneDepthDataVo) {

        // sql
        StringBuilder sql = new StringBuilder();

        //参数
        Map<String, Object> params = new HashMap<>(16);

        sql.append("MATCH {class:" + searchOneDepthDataVo.getLabel() + ", as:nodes, where:(@rid = :rid)}.bothE(){as:edges, where:($matched.nodes != $currentMatch),optional:true}  RETURN distinct nodes,edges limit " + searchOneDepthDataVo.getLimit());

        params.put("rid", searchOneDepthDataVo.getId());

        //调用方法，执行数据库操作
        try (ODatabaseSession session = pool.acquire()) {

            //处理当前线程中数据库实例不可用情况
            ODatabase oDatabase = WiklOrientdbUtil.activeOnCurrentThread(session);

            //执行查询
            OResultSet rs = oDatabase.query(sql.toString(), params);

            //处理结果集
            WiklResultData resultData = WiklGraphResultUtil.InitInstanceResult(rs, "");

            //关闭资源
            WiklOrientdbUtil.close(rs);

            return resultData;
        }
    }

    /**
     * 查询属性对应的汉字
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 14:18
     * @since V1.0
     */
    public OriginFieldResultData searchOriginField(boolean isNode, SearchOriginFieldVo searchOriginFieldVoId) {

        OriginFieldResultData originFieldResultData = new OriginFieldResultData();

        Map<String, String> params = new HashMap<>(2);

        params.put("kngraphId", searchOriginFieldVoId.getKngraphId());

        params.put("instanceLabel", searchOriginFieldVoId.getName());

        String sql;

        if (isNode) {

            sql = "MATCH {class:" + SysConstant.CONCEPT_LABEL + ", as:node, where:(kngraphId = :kngraphId and instanceLabel = :instanceLabel)} RETURN node." + searchOriginFieldVoId.getField();

        } else {

            sql = "MATCH {class:" + SysConstant.CONCEPT_EDGE_LABEL + ", as:node, where:(kngraphId = :kngraphId and instanceLabel = :instanceLabel)} RETURN node." + searchOriginFieldVoId.getField();
        }

        // 调用方法执行数据库操作
        try (ODatabaseSession session = pool.acquire()) {

            //处理当前线程中数据库实例不可用情况
            ODatabase oDatabase = WiklOrientdbUtil.activeOnCurrentThread(session);

            //执行查询
            OResultSet rs = oDatabase.query(sql, params);

            //处理结果集
            String result = WiklGraphResultUtil.dealSingleResult(rs);

            //关闭资源
            WiklOrientdbUtil.close(rs, null, null);

            Map<String, String> hashMap = new HashMap<>(1);

            hashMap.put(searchOriginFieldVoId.getField(), result);

            originFieldResultData.setOriginField(result);

            originFieldResultData.setPropertyKeyMap(hashMap);

        }

        return originFieldResultData;
    }

}
