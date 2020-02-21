package top.wikl.orientdb.controller;

import cn.hutool.json.JSONUtil;
import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.id.ORID;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

import java.util.*;

/**
 * @author XYL
 * @title: Test
 * @description: TODO
 * @date 2019/12/18 10:44
 * @return
 * @since V1.0
 */
public class Test {

    public static void main(String[] args) {

        OrientDB orient = new OrientDB("remote:10.0.47.51", OrientDBConfig.defaultConfig());

        ODatabasePool pool = new ODatabasePool(orient, "orientdb_so_xyl", "root", "password");

        //每页大小
        int pageSize = 10;

        //图谱id
        String kngraphId = "34400000oil";

        //起点标签
        String startLabel = "yuangong";

        //终点标签
        String endLabel = "ufc10cb3aaa304f8c842f74a37f0b5df0u";

        //起点关联属性key
        String startBoundPropertyKey = "suoshugongsi";

        //终点关联属性key
        String endBoundPropertyKey = "ua5cdadefaf934e13b06463271069b777u";

        //关系label
        String relationLabel = "suoshu";

        //关系名
        String relationName = "员工所属公司";

        //关系业务类型
        String businessType = "所属";

        //分页获取起点数据，根据起点关联属性，查询终点，创建关系
        String sql = "select count(*) as count ,min(@rid) as firstRid from  " + startLabel + "  where kngraphId = :kngraphId";

        //获取第一条记录的rid
        ORID firstRid = null;

        String count = "";

        try (ODatabaseSession session = pool.acquire();) {

            //查询条件
            HashMap<String, Object> map = new HashMap<>();
            map.put("kngraphId", kngraphId);

            OResultSet rs = session.query(sql, map);

            while (rs.hasNext()) {

                OResult item = rs.next();
                count = item.getProperty("count") + "";

                firstRid = item.getProperty("firstRid");

            }

            rs.close();

        }

        //分页信息
        OVertexPageConditon pageConditon = new OVertexPageConditon(Integer.parseInt(count), pageSize);

        //关系属性
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("date", new Date());
        hashMap.put("name", relationName);
        hashMap.put("instanceLabel", relationLabel);
        hashMap.put("businessType", businessType);
        hashMap.put("type", "general");

        //总页数
        int totalPage = pageConditon.getTotalPage();

        //创建schema
        createEdgeSchema(relationLabel, pool, hashMap);

        //分页处理数据
        for (int i = 0; i < totalPage; i++) {

            //相同关联属性的集合
            Set<Object> list = new HashSet<>();

            //每页起点的Rid
            List<ORID> ids = new ArrayList<>();

            String findByPage;

            if (1 == (i + 1)) {
                findByPage = "select from " + startLabel + " where @rid >= " + firstRid + " and kngraphId = \'" + kngraphId + "\' order by @rid asc limit " + pageSize;

            } else {
                findByPage = "select from " + startLabel + " where @rid > " + firstRid + " and kngraphId = \'" + kngraphId + "\' order by @rid asc limit " + pageSize;

            }

            try (ODatabaseSession session = pool.acquire();) {

                OResultSet rs = session.command(findByPage);

                while (rs.hasNext()) {

                    OResult item = rs.next();

                    OVertex oVertex = item.getVertex().get();

                    ORID identity = oVertex.getIdentity();

                    ids.add(identity);

                    //关联属性的值
                    Object property = oVertex.getProperty(startBoundPropertyKey);

                    list.add(property);

                    //更新每页的开始点
                    firstRid = oVertex.getIdentity();

                }

                rs.close();

            }

            //创建关系
            //创建概念关系,拼接实例关系sql
            String batchExcute = batchExcute(list, ids, startLabel, startBoundPropertyKey, kngraphId, endLabel, relationLabel, endBoundPropertyKey, hashMap);

            System.out.println("");
            System.out.println("");
            System.out.println("开始批量创建关系，关系语句 -->{}" + batchExcute);
            System.out.println("");
            System.out.println("");

            try (ODatabaseSession session = pool.acquire();) {

                OResultSet rs = session.execute("sql", batchExcute);

                rs.close();
            }

        }


    }


    /**
     * @param boundValue          起点关联属性值的集合
     * @param nodes               起点Rid集合
     * @param startLabel          起点label
     * @param startBound          起点关联属性key
     * @param kngraphId           图谱id
     * @param endLabel            终点label
     * @param relationLabel       关系label
     * @param endBoundPropertyKey 终点关联属性key
     * @return
     * @author XYL
     * @date 2019/12/18 13:39
     * @since V1.0
     */
    public static String batchExcute(Set<Object> boundValue, List<ORID> nodes, String startLabel, String startBound, String kngraphId, String endLabel, String relationLabel, String endBoundPropertyKey, Map<String, Object> propertyMap) {

        //获取起点数据
        String start = "let start = select from " + startLabel + " where " + startBound + " = $val and kngraphId = \'" + kngraphId + "\' and @rid in " + nodes + ";";

        StringBuilder batchSql = new StringBuilder();

        batchSql.append("begin; \n\t");

        batchSql.append("foreach ($val in " + JSONUtil.toJsonStr(boundValue) + ") \n\t");

        batchSql.append("{\n\t");

        batchSql.append(start + "\n\t");

        String toJsonStr = JSONUtil.toJsonStr(propertyMap);

        batchSql.append("create edge " + relationLabel + " from $start To (select from " + endLabel + " where " + endBoundPropertyKey + " = $val  and kngraphId = \'" + kngraphId + "\') CONTENT " + toJsonStr + "; \n\t");


        batchSql.append("} \n\t");
        batchSql.append("commit");

        return batchSql.toString();

    }

    /**
     * 创建节点schema
     *
     * @param relationLabel
     * @author XYL
     * @date 2019年7月30日 14点26分
     */
    public static void createEdgeSchema(String relationLabel, ODatabasePool pool, Map<String, Object> propertyMap) {

        try (ODatabaseSession session = pool.acquire();) {

            OClass oClass = session.getClass(relationLabel);

            if (oClass == null) {

                oClass = session.createEdgeClass(relationLabel);
            }

            if (Objects.nonNull(propertyMap)) {

                for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
                    String key = entry.getKey();
                    if (oClass.getProperty(key) == null) {

                        if ("date".equals(key)) {
                            oClass.createProperty(key, OType.DATETIME);
                        } else {
                            oClass.createProperty(key, OType.STRING);
                        }

                        oClass.createIndex(relationLabel + "_" + key + "_index", OClass.INDEX_TYPE.NOTUNIQUE, key);
                    }
                }

            }
        }
    }

}
