package top.wikl.orientdb.service;

/**
 * orientdb 服务接口
 *
 * @author XYL
 * @title: OrientDbService
 * @description: TODO
 * @date 2020/4/29 11:24
 * @return
 * @since V1.0
 */
public interface OrientDbService<R> {

    /**
     * 获取OrientDB 数据库实例
     *
     * @return
     * @throws
     * @author XYL
     * @date 2020/4/29 11:29
     * @since V1.1
     */
    R graph(boolean transaction);

    void createIndex(String indexKey);
}
