package top.wikl.neo4j.service;

import top.wikl.neo4j.model.GetDicInput;

/**
 * 获取字典服务
 *
 * @author XYL
 * @title: PocService
 * @description: TODO
 * @date 2020/5/13 19:55
 * @return
 * @since V1.0
 */
public interface PocService {

    /**
     * 获取字典
     *
     * @param getDicInput
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/13 20:03
     * @since V1.1
     */
    void getDic(GetDicInput getDicInput);

}
