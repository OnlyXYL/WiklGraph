package top.wikl.orientdb.dao.impl;

import org.apache.spark.SparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.springframework.stereotype.Service;
import top.wikl.orientdb.dao.OrientDbSparkDao;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XYL
 * @title: OrientDbSparkDaoImpl
 * @description: TODO
 * @date 2020/3/24 11:49
 * @return
 * @since V1.0
 */
@Service
public class OrientDbSparkDaoImpl implements OrientDbSparkDao {

    public static void main(String[] args) {
        Map<String, String> options = new HashMap<String, String>() {{
                put("url", "jdbc:orient:remote:localhost/sparkTest");
                put("user", "admin");
                put("password", "admin");
                // ENABLE Spark compatibility
                put("spark", "true");
                put("dbtable", "Item");
        }};

        SparkContext context = new SparkContext();

        SQLContext sqlCtx = new SQLContext(context);

        Dataset<Row> jdbc = sqlCtx.read().format("jdbc").options(options).load();
    }
}
