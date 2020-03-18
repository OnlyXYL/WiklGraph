package top.wikl.orientdb.error.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 图谱-属性表
 * </p>
 *
 * @author gaokai
 * @date 2019-09-25
 */
@ApiModel(value = "KgProperty", description = "图谱属性")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KgProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    private String propertyName;

    /**
     * 拼音
     */
    private String label;

    /**
     * 数据类型
     */
    private KgPropertyDataType dataType;
}
