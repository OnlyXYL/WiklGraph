package top.wikl.neo4j.service.impl.dic.factory;


import top.wikl.neo4j.service.impl.dic.DicHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author XYL
 * @title: DicFactory
 * @description: TODO
 * @date 2020/5/14 10:11
 * @return
 * @since V1.0
 */
public class DicFactory {

    private final static Map<String, DicHandler> handlerMap = new ConcurrentHashMap<>(5);

    /**
     * 注册处理类
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/11/20 20:23
     * @since V1.0
     */
    public static void registry(String id, DicHandler handler) {
        handlerMap.putIfAbsent(id, handler);
    }

    /**
     * 取消注册类
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/11/20 20:23
     * @since V1.0
     */
    public static void unregistry(String id) {
        handlerMap.remove(id);
    }

    /**
     * 获取结果服务
     *
     * @param
     * @return
     * @author XYL
     * @date 2020/2/28 17:42
     * @since V2.0
     */
    public static DicHandler getDicHandler(String id) {
        return handlerMap.get(id);
    }
}
