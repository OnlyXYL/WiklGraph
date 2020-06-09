package top.wikl.orientdb.service.impl.dic;

/**
 * 字典服务接口
 *
 * @author XYL
 * @title: DicHandler
 * @description: TODO
 * @date 2020/5/14 10:09
 * @return
 * @since V1.0
 */
public interface DicHandler<T, R> {

    /**
     * 判断当前服务是否支持
     *
     * @param id 字典类型
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/14 11:51
     * @since V1.1
     */
    boolean support(String id);

    /**
     * 获取字典
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/14 11:26
     * @since V1.1
     */
    R dic(T param) throws Exception;
}
