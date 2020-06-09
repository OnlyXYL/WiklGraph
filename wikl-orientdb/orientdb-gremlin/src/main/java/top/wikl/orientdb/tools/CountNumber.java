package top.wikl.orientdb.tools;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.record.OElement;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultInternal;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import top.wikl.wikljava.log.console.ConsoleLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * 统计数量
 *
 * @author XYL
 * @title: CountNumber
 * @description: TODO
 * @date 2020/4/7 15:04
 * @return
 * @since V1.0
 */
public class CountNumber {

    public static void main(String[] args) {

        OrientDB orient = new OrientDB("remote:10.0.45.65", OrientDBConfig.defaultConfig());

        ODatabaseSession session = orient.open("orientdb", "root", "password");

        String label = "v_78c0de261a24489a910bf49fce64bc7e";

        try {
            String count = count(session, label, "id");

            HashMap<String, String> map = new HashMap<>(3);
            map.put("概念", "奶台DppMaterialConsumeDtl");
            map.put("label", label);
            map.put("对应MySQL 中主键集合", count);

            ConsoleLog.outAll(map, "output");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            session.close();
        }


    }


    /**
     * 统计图谱中指定label的数据
     *
     * @param
     * @return
     * @author XYL
     * @date 2020/4/7 15:06
     * @since V2.0
     */
    public static String count(ODatabaseSession session, String label, String key) {

        ArrayList<String> integers = new ArrayList<>();
        integers.add("\n");

        String sql = "select " + key + " from " + label + " limit -1";

        OResultSet oResultSet = session.command(sql);

        while (oResultSet.hasNext()) {

            OResult item = oResultSet.next();

            //获取元素
            Optional<OElement> optional = item.getElement();

            if (Optional.empty().equals(optional)) {

                OResultInternal oResultInternal = (OResultInternal) item;

                Object value = oResultInternal.getProperty(key);

                integers.add(value.toString());
                integers.add("\n");
            }

        }
        return integers.toString();

    }

}
