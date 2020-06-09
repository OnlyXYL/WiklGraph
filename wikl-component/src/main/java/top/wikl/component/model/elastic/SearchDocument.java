package top.wikl.component.model.elastic;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author XYL
 * @title: SearchDocument
 * @description: TODO
 * @date 2020/6/2 19:40
 * @return
 * @since V1.0
 */
@Data
public class SearchDocument implements Serializable {

    /**
     * 索引
     */
    private String index;

    /**
     * <index,<key,content>>
     * 搜索信息
     */
    private Map<String, String> searchContent;

    private int from;

    private int size;
}
