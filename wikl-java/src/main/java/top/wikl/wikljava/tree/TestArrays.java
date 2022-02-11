package top.wikl.wikljava.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/12/7 0007 23:04
 */
public class TestArrays {

    public static void main(String[] args) {

        final ArrayList<String> list = new ArrayList<>();

        list.add("钻井");
        list.add("钻井.地质.设计");
        list.add("构造.单元");

        final List<String> collect = list.stream().sorted(Comparator.comparing(String::length)).collect(Collectors.toList());


        Collections.sort(list, (o1, o2) -> {
            if (o1.length() < o2.length()) {
                return 1;
            } else if (o1.length() == o2.length()) {
                return 0;
            } else {
                return -1;
            }
        });

        System.out.println(collect.toString());
    }

}
