package top.wikl.orientdb.config;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * orientdb管理类
 *
 * @author XYL
 * @title: OrientDbManage
 * @description: TODO
 * @date 2020/4/29 11:34
 * @return
 * @since V1.0
 */
@Configuration
public class OrientDbManage<R> {

    @Resource
    private OrientGraphFactory factory;

    public R graph(boolean transaction){

        if (transaction) {

            OrientGraph tx = factory.getTx();
            return tx;
        }

    };

}
