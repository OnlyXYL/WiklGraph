package top.wikl.component.service;

import java.util.Map;

/**
 * poc 服务
 *
 * @author XYL
 * @title: PocService
 * @description: TODO
 * @date 2020/5/9 11:30
 * @return
 * @since V1.0
 */
public interface PocService {

    /**
     * gw_bd
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/9 11:32
     * @since V1.1
     */
    Map<String, String> gw_bd(String filePath, String Scolumn, String Ecolumn);

}
