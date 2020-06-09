package top.wikl.component.model.elastic;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author XYL
 * @title: AddDocument
 * @description: TODO
 * @date 2020/6/2 14:15
 * @return
 * @since V1.0
 */
@Data
public class AddDocument implements Serializable {

    /**
     * 索引，理解成库
     */
    private String index;

    /**
     * id
     */
    private String id;

    /**
     * type,理解成class
     */
    private String type;

    /**
     * 属性
     */
    private Map<String,Object> properties;
}
