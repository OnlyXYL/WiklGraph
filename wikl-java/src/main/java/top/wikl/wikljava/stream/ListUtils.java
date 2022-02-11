package top.wikl.wikljava.stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/10/27 0027 16:57
 */
public class ListUtils {


    public static void main(String[] args) {


        final List<String> list1 = Arrays.asList("1", "2", "3", "4");

        final List<String> list3 = Arrays.asList("4", "3", "2", "1");

        final HashMap<String, String> map1 = new HashMap<>();
        map1.put("name","1");
        map1.put("value","1");


        final HashMap<String, String> map2 = new HashMap<>();
        map2.put("name","2");
        map2.put("value","2");

        final HashMap<String, String> map3 = new HashMap<>();
        map3.put("name","3");
        map3.put("value","3");

        final HashMap<String, String> map4 = new HashMap<>();
        map4.put("name","4");
        map4.put("value","4");

        final ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        arrayList.add(map1);
        arrayList.add(map2);
        arrayList.add(map3);
        arrayList.add(map4);


        final List<String> list2 = Arrays.asList("2", "3", "5");

        //集合差集
        reduce(list2, list1);

        sort(list1,arrayList);
    }

    public static void sort(List<String> list1,List<HashMap<String,String>> list2){
        list2.sort(Comparator.comparingInt(list1::indexOf));


        System.out.println(list2.toString());
    }


    /**
     * list差集  list1-list2
     *
     * @param list1
     * @param list2
     * @return void
     * @author XYL
     * @since 16:58 2021/10/27 0027
     **/
    public static void reduce(List<String> list1, List<String> list2) {

        final List<String> collect = list1.stream().filter(item -> !list2.contains(item)).collect(Collectors.toList());

        System.out.println(collect.toString());
    }


}
