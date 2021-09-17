package top.wikl.wikljava.stream;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/8/18 0018 10:41
 */
public class StreamTest {

    public static void main(String[] args) {

        final ArrayList<Integer> list = new ArrayList<>();

        IntStream.range(0, 1000).forEach(list::add);

        System.out.println("stream: ");
        final long start = System.currentTimeMillis();
        list.stream().forEach(s -> System.out.print(s + " "));
        final long end = System.currentTimeMillis();
        System.out.println("\n耗时：" + (end - start) + " ms");


        final long l = System.currentTimeMillis();
        System.out.print("\nparallel stream: ");
        list.parallelStream().forEach(s -> System.out.print(s + " "));
        final long l1 = System.currentTimeMillis();
        System.out.println("\n耗时：" + (l1 - l) + " ms");

        final long l2 = System.currentTimeMillis();
        System.out.println("\nparallel stream order: \n");
        list.parallelStream().forEachOrdered(s -> System.out.print(s + " "));
        final long l3 = System.currentTimeMillis();
        System.out.println("\n耗时：" + (l3 - l2) + " ms");
    }
}
