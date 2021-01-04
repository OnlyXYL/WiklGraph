package top.wikl.component.model.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author XYL
 * @version 1.2
 * @since 2020/12/29 0029 17:07
 */
@Data
@Document(collection = "KgMongoConcept")
public class KgMongoConcept {

    private String id;

    @Field("recordId")
    private String recordId;

    @Field("conceptName")
    private String conceptName;

    @Field("createTime")
    private Date createTime;

    @Field("createBy")
    private Integer createBy;
}
