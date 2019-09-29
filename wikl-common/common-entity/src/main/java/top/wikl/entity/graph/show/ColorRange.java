package top.wikl.entity.graph.show;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 颜色级别
 *
 * @param
 * @author XYL
 * @date 2019/9/27 13:35
 * @return
 * @since V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ColorRange implements Serializable {

    private List<Integer> colorRanges = new ArrayList<>();

    private Integer currentRange = 0;
}

