package top.wikl.wikljava.collections.list.abstractlist;

import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author XYL
 * @title: ArrayListTest
 * @description: TODO
 * @date 2020/4/8 17:01
 * @return
 * @since V1.0
 */
public class ArrayListTest {

    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<>(2);

        list.add("1");

        list.add("2");

        list.forEach(x-> System.out.println(x));

        list.iterator().forEachRemaining(x-> System.out.println(x));


    }
}
