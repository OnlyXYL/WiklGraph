package top.wikl.orientdb.service.manage;

/**
 * @author XYL
 * @title: OrientDbInterface
 * @description: TODO
 * @date 2020/4/29 11:37
 * @return
 * @since V1.0
 */
public interface OrientDbInterface<R> {

    R graph(boolean transaction);
}
