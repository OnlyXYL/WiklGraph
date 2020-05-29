package top.wikl.component.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author XYL
 * @title: SearchGraphInfoInput
 * @description: TODO
 * @date 2020/5/28 9:50
 * @return
 * @since V1.0
 */
@Data
public class SearchGraphInfoInput implements Serializable {

    /**
     * 图谱id
     */
    private Integer graphId;

    /**
     * 参数
     */
    private String param;

    public SearchGraphInfoInput() {
    }

    public SearchGraphInfoInput(Integer graphId, String param) {
        this.graphId = graphId;
        this.param = param;
    }
}
