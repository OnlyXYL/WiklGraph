package top.wikl.orientdb.demo;

import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;

import java.util.Iterator;

/**
 * @param
 * @author XYL
 * @date 2020/3/21 17:54
 * @return
 * @since V2.0
 */
public class demo9 {

    public static void main(String[] args) {

        String url = "remote:10.0.43.101/xyl;remote:/10.0.43.102/xyl;remote:/10.0.43.103/xyl;remote:/10.0.43.104/xyl";

        OrientGraphFactory factory = new OrientGraphFactory(url);

        OrientGraphNoTx graphNoTx = factory.getNoTx();

        String insertSql = "create vertex class_9ca953d8_b5c4_4d70_ac71_845d0f8cf985 cluster class_9ca953d8_b5c4_4d70_ac71_845d0f8cf985_2 CONTENT {\"p_ceshi_36\":\"12\"}";


        OrientDynaElementIterable execute = graphNoTx.command(new OCommandSQL(insertSql)).execute();

        Iterator<Object> iterator = execute.iterator();

        while (iterator.hasNext()) {

            String s = iterator.next().toString();

            System.out.println(s);

        }

    }

}
