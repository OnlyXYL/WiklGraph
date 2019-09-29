package top.wikl.entity.graph.show;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @param
 * @author XYL
 * @date 2019/9/27 13:35
 * @return
 * @since V1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ColorMap implements Serializable {

    private Map<String, Integer> colorMap = new HashMap<>();
}
