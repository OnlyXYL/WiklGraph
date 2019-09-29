package top.wikl.entity.graph.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 单个记录详情返回结果
 *
 * @param
 * @author XYL
 * @date 2019/9/27 13:31
 * @return
 * @since V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "单个记录详情返回结果")
public class SingleRecordInfoResultData {

    /**
     * 线起点
     */
    @ApiModelProperty(value = "线起点")
    private String startLabel = "";

    /**
     * 线终点
     */
    @ApiModelProperty(value = "线终点")
    private String endLabel = "";

    /**
     * 线/点属性集合
     */
    @ApiModelProperty(value = "线/点属性集合")
    private Map<String, Object> others;

    /**
     * 是否存入LRUMap
     */
    private boolean storageLRU = false;

}
