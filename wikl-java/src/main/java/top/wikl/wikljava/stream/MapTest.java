package top.wikl.wikljava.stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/11/8 0008 9:58
 */
public class MapTest {

    public static void main(String[] args) {

        final HashMap<String, Integer> map = new HashMap<>();

        map.put("井筒", 100);
        map.put("井筒1", 1001);
        map.put("井筒2", 10);
        map.put("井筒3", 13);

        /**
         * Map.Entry.comparingByValue() 升序
         *
         * Collections.reverseOrder(Map.Entry.comparingByValue())降序
         */
        final List<String> collect = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue(), (x1, x2) -> x2, LinkedHashMap::new))
                .keySet().stream().collect(Collectors.toList());

        System.out.println(collect.toString());

        final List<String> collect1 = map.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue(), (x1, x2) -> x2, LinkedHashMap::new))
                .keySet().stream().collect(Collectors.toList());

        System.out.println(collect1.toString());
    }
}
