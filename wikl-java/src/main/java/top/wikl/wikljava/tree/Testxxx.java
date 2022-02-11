package top.wikl.wikljava.tree;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/11/26 0026 15:52
 */
public class Testxxx {

    public static void main(String[] args) {

        final HashMap<String, String> map = new HashMap<>();

        map.put("钻具装配组合","；钻具装配钻具组合；钻具装配钻具组合；钻具装配组合");

        final HashMap<String, List<String>> hashMap = new HashMap<>();


        map.forEach((k,v)->{
            final String[] split = v.split("[;；]");

            final Set<String> collect = Arrays.stream(split).filter(s -> !s.equals(k) && !s.equals("")).collect(Collectors.toSet());

            hashMap.put(k,new ArrayList<>(collect));
        });


        final String toString = hashMap.values().toString();

        System.out.println(toString);
    }

}
