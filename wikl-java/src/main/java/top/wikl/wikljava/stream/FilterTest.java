package top.wikl.wikljava.stream;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/9/23 0023 10:39
 */
public class FilterTest {

    public static void main(String[] args) {

        filter("");

    }



    private static  void filter(String con){

        final List<String> containList = Arrays.asList("2", "3");


        final List<String> list = Arrays.asList("1", "2", "3", "4");

        final List<String> collect = list.stream().filter(s -> {

            if (StringUtils.isNotBlank(con)) {

                return containList.contains(s);
            }

            return false;

        }).collect(Collectors.toList());


        System.out.println(collect.toString());

    }

}
