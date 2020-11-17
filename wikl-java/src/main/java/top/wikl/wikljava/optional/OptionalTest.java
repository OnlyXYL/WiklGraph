package top.wikl.wikljava.optional;

import java.util.ArrayList;
import java.util.Optional;

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

//        strings = null;

        final ArrayList<String> strings1 = Optional.ofNullable(strings).orElse(null);

        System.out.println(strings1);

    }

}
