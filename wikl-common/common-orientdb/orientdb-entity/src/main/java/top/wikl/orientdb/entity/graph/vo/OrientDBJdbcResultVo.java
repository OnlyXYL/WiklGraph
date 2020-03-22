package top.wikl.orientdb.entity.graph.vo;

import com.orientechnologies.orient.core.db.ODatabasePool;
import com.orientechnologies.orient.core.db.OrientDB;
import com.orientechnologies.orient.core.sql.executor.OResultSet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OrientDB jdbc 方式结果对象
 *
 * @param
 * @author XYL
 * @date 2019/9/27 13:14
 * @return
 * @since V1.0
 */
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrientDBJdbcResultVo {

    /**
     * 结果集
     */
    @ApiModelProperty(value = "结果集")
    private OResultSet oResultSet;

    /**
     * session
     */
    @ApiModelProperty(value = "session")
    private ODatabasePool oDatabasePool;

    /**
     * 客户端
     */
    @ApiModelProperty(value = "客户端")
    private OrientDB orientDB;

}
