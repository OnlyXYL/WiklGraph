package top.wikl.component.service;

import top.wikl.component.model.SearchGraphInfoInput;

/**
 * @author XYL
 * @title: LogService
 * @description: TODO
 * @date 2020/5/27 15:54
 * @return
 * @since V1.0
 */
public interface LogService {

    /**
     * 属性日志信息 test
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/27 19:38
     * @since V1.1
     */
    String test(String params);

    /**
     * post 方式验证日志输出参数
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/28 9:51
     * @since V1.1
     */
    SearchGraphInfoInput graphInfo(SearchGraphInfoInput searchGraphInfoInput);
}
