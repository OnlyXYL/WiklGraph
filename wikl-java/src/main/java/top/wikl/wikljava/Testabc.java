package top.wikl.wikljava;

import com.google.common.collect.HashBiMap;
import top.wikl.utils.json.JsonUtils;

import java.util.*;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/6/8 0008 11:00
 */
public class Testabc {

    public static void main(String[] args) {


        HashMap<String, String> map = new HashMap<>(2);
        map.put("text_id","001");
        map.put("content","11");

        HashMap<String, String> map1 = new HashMap<>(2);
        map1.put("text_id","002");
        map1.put("content","22");

        final ArrayList<Map<String, String>> arrayList = new ArrayList<>();

        arrayList.add(map);
        arrayList.add(map1);


        arrayList.parallelStream().filter(s->s.get("text_id").equals("001")).forEach(s-> s.put("content","333333"));

        System.out.println(arrayList.toString());


        final ArrayList<Object> collect = arrayList.parallelStream().collect(ArrayList::new, (list, i) -> {
//            list.addAll(Arrays.asList(i.split(",")));

            if (i.get("text_id").equals("001")) {
                i.put("content", "4444");
            }

            list.add(i);

        }, List::addAll);

        System.out.println(collect.toString());
    }
}
