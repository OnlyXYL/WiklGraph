package top.wikl.wikljava;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/5/14 0014 16:01
 */
public class TestList {

    public static void main(String[] args) {

        final ArrayList<Map<String, String>> maps = new ArrayList<>(3);

        final HashMap<String, String> hashMap = new HashMap<>(2);
        hashMap.put("dicId", "1001");
        hashMap.put("dicName", "字典");

        final HashMap<String, String> hashMap1 = new HashMap<>(2);
        hashMap1.put("dicId", "1002");
        hashMap1.put("dicName", "字典1");


        final HashMap<String, String> hashMap2 = new HashMap<>(2);
        hashMap2.put("dicId", "1003");
        hashMap2.put("dicName", "字典2");

        maps.add(hashMap);
        maps.add(hashMap1);
        maps.add(hashMap2);

        convert(maps);

        System.out.println("==========");

        convert1(maps);

    }

    public static void convert(List<Map<String, String>> maps) {

        final ArrayList<String> strings = new ArrayList<>();

        maps.forEach(m -> {
            strings.add(m.get("dicId"));
        });

        System.out.println(strings.toString());
    }


    public static void convert1(List<Map<String, String>> maps) {

        final List<String> dicId = maps.stream().map(x -> x.keySet().stream().filter(k->"dicId".equals(k)).map(e -> x.get(e)).findFirst().get()).collect(Collectors.toList());


//        maps.stream().map(m -> m.keySet().stream().map(e -> m.get("dicId"));

        System.out.println(dicId.toString());
    }

}
