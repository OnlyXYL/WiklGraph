package top.wikl.wikljava.optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Optional测试
 *
 * @author XYL
 * @version 1.2
 * @since 2020/10/21 11:33
 */
public class OptionalTest {

    public static void main(String[] args) {

        ArrayList<String> strings = new ArrayList<>();

        strings.add("1");

        final ArrayList<String> strings1 = Optional.ofNullable(strings).orElse(null);

        System.out.println(strings1);

        final List<String> list = Optional.ofNullable(strings).map(n -> n.stream().limit(2).collect(Collectors.toList())).orElse(null);

        final List<String> collect = list.stream().filter(r -> !strings.contains(r)).collect(Collectors.toList());

        System.out.println(collect.toString());

    }

}
