package top.wikl.component.model.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

/**
 * @author XYL
 * @version 1.2
 * @since 2020/12/29 0029 17:07
 */
@Data
@Document(collection = "KgNodeInfo")
public class KgNodeInfo {

    @Id
    private String id;

    @Field("conceptId")
    private String conceptId;

    @Field("properties")
    @DBRef(lazy = true)
    private Map<String,Object> properties;

    @Field("checkPass")
    private boolean checkPass;
}
