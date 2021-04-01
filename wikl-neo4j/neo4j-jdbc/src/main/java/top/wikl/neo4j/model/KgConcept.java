package top.wikl.neo4j.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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
    @TableId(value = "conceptId", type = IdType.AUTO)
    private Integer conceptId;

    /**
     * 概念名称
     */
    @ApiModelProperty(value = "概念名称")
    @TableField("conceptName")
    private String conceptName;

    /**
     * 节点标签
     */
    @ApiModelProperty(value = "节点标签")
    private String label;

    /**
     * 节点来源
     */
    @ApiModelProperty(value = "节点来源")
    private String source;

    /**
     * 概念点是否已经配置   0：未配置  1：已配置
     */
    @ApiModelProperty(value = "概念点是否已经配置 0：未配置，1：已配置")
    private KgConceptStatus status;

    /**
     * 创建时间
     */
    @TableField("createDate")
    private Date createDate;

    /**
     * 更新时间
     */
    @TableField("updateDate")
    private Date updateDate;

    /**
     * 图谱id
     */
    @TableField("graphId")
    private Integer graphId;

    /**
     * 节点颜色
     */
    @TableField("nodeColor")
    private Integer nodeColor;

    /**
     * 节点颜色
     */
    @TableField("nodeSize")
    private Integer nodeSize;

    /**
     * 删除标记,0：未删除 1：删除'
     */
    @TableLogic
    private Boolean deleted;

    /**
     * 重写equals,用于比较对象属性是否包含
     *
     * @param obj
     * @return boolean
     * @author XiaYaLing
     * @date 2018/4/17
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        KgConcept kgConcept = (KgConcept) obj;
        //多重逻辑处理
        if (this.getGraphId().compareTo(kgConcept.getGraphId()) == 0
                && this.getConceptName().equals(kgConcept.getConceptName())
                && this.getDeleted().equals(kgConcept.getDeleted())) {
            return true;
        }
        return false;
    }
}
