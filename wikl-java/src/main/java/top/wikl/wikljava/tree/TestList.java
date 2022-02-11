package top.wikl.wikljava.tree;

import java.util.ArrayList;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/10/28 0028 12:07
 */
public class TestList {

    public static void main(String[] args) {

        String str = "\"哈(\"你\")哈\"";

        final String[] split = str.split("\"");

        final StringBuilder builder = new StringBuilder();

        final ArrayList<String> strings = new ArrayList<>();

        for (int i = 1; i < split.length; i++) {
            strings.add(split[i]);
        }

        final String join = String.join("\"", strings);

        System.out.println(join);

    }
}
