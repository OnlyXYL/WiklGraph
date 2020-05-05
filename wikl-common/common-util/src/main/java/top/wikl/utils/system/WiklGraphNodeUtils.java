package top.wikl.utils.system;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 图谱节点工具类
 *
 * @param
 * @author XYL
 * @date 2019/9/27 13:33
 * @return
 * @since V1.0
 */
@Data
public class WiklGraphNodeUtils {

    /**
     * 图谱节点大小级别生成规则
     *
     * @param longs
     * @param edgeNumber
     * @return
     */
    public static int rangeRegular(List<Long> longs, int edgeNumber) {

        Long max = Collections.max(longs);

        Long min = Collections.min(longs);

        int size = longs.size();

        int range = rangeRegular(max.intValue(), min.intValue(), size, edgeNumber);

        return range;

    }


    /**
     * 图谱节点大小级别生成规则
     *
     * @param max
     * @param min
     * @param size
     * @param edgeNumber
     * @return
     */
    public static int rangeRegular(int max, int min, int size, int edgeNumber) {

        int range = 1;

        if (edgeNumber == 0 || edgeNumber == 1) {

            range = 1;
        } else {

            double a = (max - min) / size;

            Double floor = Math.floor(a);

            int intValue = floor.intValue();

            if (min <= edgeNumber && edgeNumber <= (min + intValue)) {
                range = 1;
            }

            if ((min + intValue) <= edgeNumber && edgeNumber <= (min + 2 * intValue)) {
                range = 2;
            }

            if ((min + 2 * intValue) <= edgeNumber && edgeNumber <= max) {
                range = 3;
            }
        }

        return range;

    }

    public static void main(String[] args) {

        rangeRegular(3, 1, 2, 3);

    }


    /**
     * 根据节点生成一个指定范围的随机颜色
     * <p>
     * 1 ，2 ，3，4，5
     *
     * @param label
     */
    public static Integer nodeColor(String label) {

        return 0;

    }

}
