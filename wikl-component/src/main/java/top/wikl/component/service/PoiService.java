package top.wikl.component.service;

/**
 * @author XYL
 * @title: PoiService
 * @description: TODO
 * @date 2020/4/1 19:26
 * @return
 * @since V1.0
 */
public interface PoiService {

    byte[] excel(String filePath, String sheetName);

    /**
     * 处理实例数据
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/4/17 13:50
     * @since V1.1
     */
    byte[] instanceTemplate(String filePath);
}
