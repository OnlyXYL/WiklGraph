package top.wikl.entity.graph.fusion;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Objects;

/**
 * 属性资源定位规则
 *
 * @param
 * @author XYL
 * @date 2019/9/27 13:19
 * @return
 * @since V1.0
 */
@ApiModel(value = "属性资源定位规则")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Slf4j
public class PropertyValue implements Serializable {

    /**
     * 节点唯一标识
     */
    private String id;

    /**
     * 所属文件
     */
    private String fileUrl;

    /**
     * 所属sheet
     */
    private String sheet;

    /**
     * 所属列
     */
    private String column;

    /**
     * 删除标识，用户概念图谱回显时，判断能不能删除的依据，默认不能删除
     */
    private boolean deleteFlag = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PropertyValue)) {
            return false;
        }
        PropertyValue that = (PropertyValue) o;
        return Objects.equals(fileUrl, that.fileUrl) &&
                Objects.equals(sheet, that.sheet) &&
                Objects.equals(column, that.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileUrl, sheet, column);
    }
}
