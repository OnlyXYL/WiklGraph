package top.wikl.neo4j.service.impl.dic;


import top.wikl.neo4j.service.impl.dic.factory.DicFactory;

/**
 * 字典服务抽象类
 *
 * @author XYL
 * @title: AbstractDicHandler
 * @description: TODO
 * @date 2020/5/14 10:11
 * @return
 * @since V1.0
 */
public abstract class AbstractDicHandler<T, R> implements DicHandler<T, R> {

    public AbstractDicHandler(String id) {
        DicFactory.registry(id, this);
    }
}
