package top.wikl.wikljava.grouping;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/6/29 0029 17:57
 */
public class GroupingTest {

    public static void main(String[] args) {

        final ArrayList<Map> maps = new ArrayList<Map>();

        final HashMap<String, Object> jingshen = new HashMap<>(4);
        jingshen.put("concept", "井筒");
        jingshen.put("entity", "TK001");
        jingshen.put("propertyKey", "井深");
        jingshen.put("propertyValue", "100米");

        final HashMap<String, Object> jingkuan = new HashMap<>(4);
        jingkuan.put("concept", "井筒");
        jingkuan.put("entity", "TK001");
        jingkuan.put("propertyKey", "井宽");
        jingkuan.put("propertyValue", "10米");

        maps.add(jingshen);
        maps.add(jingkuan);

        final LinkedHashMap<Object, LinkedHashMap<Object, List<Map>>> collect = maps.stream()
                .collect(
                        Collectors.groupingBy(
                                k -> Optional.ofNullable(k.get("concept")).orElse(""),
                                LinkedHashMap::new,
                                Collectors.groupingBy(
                                        k -> Optional.ofNullable(k.get("entity")).orElse(""),
                                        LinkedHashMap::new,
                                        Collectors.toList()
                                )
                        )
                );


        System.out.println(collect.toString());

    }
}
