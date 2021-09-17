package top.wikl.wikljava.stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/9/15 0015 16:16
 */
public class MapListAddByKey {

    public static void main(String[] args) {

        //将两个List<Map>合并成一个List<Map>，“name”为map的key
        mergeTwoListMapToOneListMap(null, null, "key");

        System.out.println("");
        System.out.println("");
        System.out.println("相同数据相减");

        mergeTwoListMapToOneListMapBySubtract(null, null, "key");

    }

    public static void mergeTwoListMapToOneListMapBySubtract(List<Map> list1, List<Map> list2, final String mergeKey) {

        List<Map<String, Object>> lists = new ArrayList<>();

        List<Map<String, Object>> lists1 = new ArrayList<>();
        List<Map<String, Object>> lists2 = new ArrayList<>();


        //--------------lists1--------------------
        Map<String, Object> h1 = new HashMap<>();
        h1.put("key", 1);
        h1.put("value", Arrays.asList(1, 2, 3));

        Map<String, Object> h2 = new HashMap<>();
        h2.put("key", 2);
        h2.put("value", Arrays.asList(4, 5));

        lists1.add(h1);
        lists1.add(h2);

        //--------------lists2--------------------

        Map<String, Object> h3 = new HashMap<>();
        h3.put("key", 1);
        h3.put("value", Arrays.asList(1,2));

        Map<String, Object> h4 = new HashMap<>();
        h4.put("key", 3);
        h4.put("value", Arrays.asList(1, 2, 3));

        lists2.add(h3);
        lists2.add(h4);
        //测试
        //mergeKey="name";

        lists1.addAll(lists2);

        final Map<String, List<Map<String, Object>>> key1 = lists1.parallelStream()
                .collect(Collectors.groupingBy(group -> group.get(mergeKey).toString()));

        System.out.println(key1.toString());

        List<Map<String, Object>> key = lists1.parallelStream()
                .collect(Collectors.groupingBy(group -> group.get(mergeKey).toString()))
                .entrySet()
                .stream()
                .map(m -> {
                    Map<String, Object> collect = m.getValue().stream()
                            .flatMap(o -> o.entrySet().stream())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (m1, m2) -> {

                                if (m1 instanceof List && m2 instanceof List) {

                                    List m11 = (List) m1;

                                    List m21 = (List) m2;

                                    return removeRepeat(m11, m21);

                                }
                                return m2;

                            }));

                    return collect;

                }).collect(Collectors.toList());


        System.out.println("----------list--mergetMap---------");
        System.out.println(key.toString());

    }


    /**
     * 两个list《map》中的map合并为一个list《map》,新的list中的每个map包含了之前的两个listmap的key
     *
     * @param list1
     * @param list2
     * @param mergeKey
     * @return void
     * @author XYL
     * @since 18:39 2021/9/15 0015
     **/
    public static void mergeTwoListMapToOneListMap(List<Map> list1, List<Map> list2, final String mergeKey) {

        List<Map<String, Object>> lists = new ArrayList<>();

        List<Map<String, Object>> lists1 = new ArrayList<>();
        List<Map<String, Object>> lists2 = new ArrayList<>();


        //--------------lists1--------------------
        Map<String, Object> h1 = new HashMap<>();
        h1.put("key", 1);
        h1.put("value", Arrays.asList(1, 2, 3));

        Map<String, Object> h2 = new HashMap<>();
        h2.put("key", 2);
        h2.put("value", Arrays.asList(4, 5));

        lists1.add(h1);
        lists1.add(h2);

        //--------------lists2--------------------

        Map<String, Object> h3 = new HashMap<>();
        h3.put("key", 1);
        h3.put("value", Arrays.asList(6, 7, 8));

        Map<String, Object> h4 = new HashMap<>();
        h4.put("key", 3);
        h4.put("value", Arrays.asList(1, 2, 3));

        lists2.add(h3);
        lists2.add(h4);
        //测试
        //mergeKey="name";

        lists1.addAll(lists2);

        final Map<String, List<Map<String, Object>>> key1 = lists1.parallelStream()
                .collect(Collectors.groupingBy(group -> group.get(mergeKey).toString()));

        System.out.println(key1.toString());

        List<Map<String, Object>> key = lists1.parallelStream()
                .collect(Collectors.groupingBy(group -> group.get(mergeKey).toString()))
                .entrySet()
                .stream()
                .map(m -> {
                    Map<String, Object> collect = m.getValue().stream()
                            .flatMap(o -> o.entrySet().stream())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (m1, m2) -> {

                                if (m1 instanceof List && m2 instanceof List) {

                                    ArrayList arrayList = new ArrayList();

                                    List m11 = (List) m1;

                                    List m21 = (List) m2;

                                    arrayList.addAll(m11);
                                    arrayList.addAll(m21);

                                    return arrayList;
                                }
                                return m2;

                            }));

                    return collect;

                }).collect(Collectors.toList());


        System.out.println("----------list--mergetMap---------");
        System.out.println(key.toString());

    }

    /**
     * 集合去重
     *
     * @param list1
     * @param list2
     * @return void
     * @author XYL
     * @since 18:39 2021/9/15 0015
     **/
    public static List<Integer> removeRepeat(List<Integer> list1, List<Integer> list2) {

        List<Integer> result;

        if (list1.size() > list2.size()) {
            result = list1.stream().filter(s -> !list2.contains(s)).collect(Collectors.toList());
        } else {
            result = list2.stream().filter(s -> !list1.contains(s)).collect(Collectors.toList());
        }

        return result;
    }

}
