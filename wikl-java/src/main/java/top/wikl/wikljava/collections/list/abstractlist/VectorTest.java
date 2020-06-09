package top.wikl.wikljava.collections.list.abstractlist;

import java.util.Vector;

/**
 * @author XYL
 * @title: VectorTest
 * @description: TODO
 * @date 2020/4/8 18:06
 * @return
 * @since V1.0
 */
public class VectorTest {

    public static void main(String[] args) {

        Vector<String> vector = new Vector<>(1);

        vector.add("1");

        vector.add("2");

        System.out.println(vector.toString());

    }
}
