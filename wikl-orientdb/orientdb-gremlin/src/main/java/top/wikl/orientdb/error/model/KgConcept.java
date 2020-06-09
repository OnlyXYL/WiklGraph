package top.wikl.orientdb.error.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 图谱-概念表
 *
 * @author gaokai
 * @date 2019-09-25
 */
@ApiModel(value = "KgConcept", description = "概念信息")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KgConcept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 概念方式图谱节点Id
     */
    @ApiModelProperty(value = "概念节点Id")
    private Integer conceptId;

    /**
     * 概念名称
     */
    @ApiModelProperty(value = "概念名称")
    private String conceptName;

    /**
     * 节点标签
     */
    @ApiModelProperty(value = "节点标签")
    private String label;

    /**
     * 图谱id
     */
    private Integer graphId;

    /**
     * 概念的属性
     */
    private List<KgProperty> properties;
}
