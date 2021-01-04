package top.wikl.component.model.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author XYL
 * @version 1.2
 * @since 2020/12/29 0029 17:06
 */
@Data
@Document(collection = "KgBuildFileRecord")
public class KgBuildFileRecord {

    private String id;

    @Field("graphId")
    private Integer graphId;

    @Field("createTime")
    private Date createTime;

    @Field("createBy")
    private Integer createBy;
}
