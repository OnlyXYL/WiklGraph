package top.wikl.orientdb;

import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;

import java.util.Iterator;

/**
 * Created by XYL on 2020/2/24 0024.
 */
public class demo9 {

    public static void main(String[] args) {

        String url = "remote:10.0.43.101/xyl;remote:/10.0.43.104/xyl;remote:/10.0.43.103/xyl;remote:/10.0.43.104/xyl;remote:/10.0.43.105/xyl;remote:/10.0.43.106/xyl";

        OrientGraphFactory factory = new OrientGraphFactory(url);

        OrientGraphNoTx graphNoTx = factory.getNoTx();

        OrientDynaElementIterable execute = graphNoTx.command(new OCommandSQL("select from city")).execute();

        Iterator<Object> iterator = execute.iterator();

        while (iterator.hasNext()){

            String s = iterator.next().toString();

            System.out.println(s);

        }

    }

}
