package top.wikl.wikljava;

import java.util.ArrayList;

/**
 * @author XYL
 * @version 1.2
 * @date 2022/05/14 16:55
 */
public class T {

    public static void main(String[] args) {

        final ArrayList<String> list = new ArrayList<>();

        final boolean anyMatch = list.stream().anyMatch(s -> s.equals("11"));

        if (anyMatch) {
            System.out.println("111");
        }else{
            System.out.println("000");
        }

    }

}
