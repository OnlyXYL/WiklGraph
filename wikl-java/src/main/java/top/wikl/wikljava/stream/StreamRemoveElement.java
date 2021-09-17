package top.wikl.wikljava.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 从流中删除元素
 *
 * @author XYL
 * @version 1.2
 * @since 2021/9/17 0017 9:54
 */
public class StreamRemoveElement {

    public static void main(String[] args) {

        List<String> list = Arrays.asList("1", "2", "3", "4", "5");

        removeFromStream2(list, "3");

    }

    public static void removeFromStream1(List<String> data, String remove) {

        System.out.println("------ 数据移除之前 ------");
        System.out.println(data.toString());

        data.stream()
                .filter(s -> s.equals(remove))
                .findFirst()
                .map(p -> {
                    data.remove(p);
                    return p;
                });

        System.out.println("------ 数据移除之后 ------");
        System.out.println(data.toString());

    }

    public static void removeFromStream2(List<String> data, String remove) {

        System.out.println("------ 数据移除之前 ------");
        System.out.println(data.toString());

        IntStream.range(0, data.size())
                .filter(i -> data.get(i).equals(remove))
                .boxed()
                .findFirst()
                .map(i -> data.remove((int) i));
        System.out.println("------ 数据移除之后 ------");
        System.out.println(data.toString());

    }

    public static void removeFromStream3(List<String> data, String remove) {

        System.out.println("------ 数据移除之前 ------");
        System.out.println(data.toString());

        final List<String> collect = data.stream().filter(s -> !s.equals(remove)).collect(Collectors.toList());

        System.out.println("------ 数据移除之后 ------");
        System.out.println(collect.toString());


    }


}
