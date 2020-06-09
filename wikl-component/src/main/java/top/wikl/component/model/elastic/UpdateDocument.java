package top.wikl.component.model.elastic;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Map;

/**
 * @author XYL
 * @title: UpdateDocument
 * @description: TODO
 * @date 2020/6/2 19:21
 * @return
 * @since V1.0
 */
@Slf4j
@Data
public class UpdateDocument implements Serializable {

    private String index;

    private String id;

    private Map<String,Object> properties;
}
