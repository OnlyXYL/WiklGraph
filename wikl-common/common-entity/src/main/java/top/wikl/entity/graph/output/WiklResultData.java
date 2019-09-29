package top.wikl.entity.graph.output;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 图谱公共返回对象
 *
 * @param
 * @author XYL
 * @date 2019/9/27 13:04
 * @return
 * @since V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "图谱公共返回对象")
public class WiklResultData implements Serializable {

    /**
     * 节点信息
     */
    @ApiModelProperty(value = "节点信息")
    private List<WiklNodeInfo> nodes;

    /**
     * 线信息
     */
    @ApiModelProperty(value = "线信息")
    private List<WiklRelationInfo> edges;

    /**
     * 其他数据，eg 单个属性值
     */
    @ApiModelProperty(value = "其他数据，eg 单个属性值")
    private Map<String, Object> others;

    @Override
    public String toString() {

        //存在重复的数据在json转化时候的问题（叫循环引用检测）
        return JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect);
    }
}
