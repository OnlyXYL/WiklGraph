package top.wikl.component.model.elastic;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

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

    private List<String> indices;

    private String key;

    private String value;
}
