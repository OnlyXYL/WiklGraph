package top.wikl.wikljava;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/7/1 0001 17:42
 */
public class TestC {

    public static void main(String[] args) {

        final List<String> list = Arrays.asList("6","1", "1", "1", "4", "4");

        final Map<String, Long> collect = list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


        final Map<String, Long> stringLongMap = collect.entrySet().stream().limit(10).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println(collect.toString());
        System.out.println(stringLongMap.toString());

    }

    public static void forTest(List<String> list) {

        final ArrayList<Map> maps = new ArrayList<>();

        for (String s : list) {
            final HashMap<String, Object> map = new HashMap<>(2);

            map.put("id", id());
            map.put("content", s);
            maps.add(map);
        }

        System.out.println(maps.toString());
    }



    public static void lambdaTest(List<String> list) {

        final ArrayList<Map> maps = new ArrayList<>();

        list.forEach(s -> {

            final HashMap<String, Object> map = new HashMap<>(2);

            map.put("id", id());
            map.put("content", s);
            maps.add(map);
        });

        System.out.println(maps.toString());
    }


    public static String id() {

        return UUID.randomUUID().toString();
    }
}
