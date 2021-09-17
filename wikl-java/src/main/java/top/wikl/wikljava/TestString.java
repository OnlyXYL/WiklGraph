package top.wikl.wikljava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/5/17 0017 15:17
 */
public class TestString {

    public static void main(String[] args) {

        String a = "TK001属于A";

        final StringBuilder builder = new StringBuilder(a);

        final ArrayList<Map<String, Object>> maps = new ArrayList<>();

        final Map<String, Object> hashMap = new HashMap<>(3);

        hashMap.put("start", 0);
        hashMap.put("end", 5);
        hashMap.put("replace", "井");


        final Map<String, Object> hashMap1 = new HashMap<>(3);

        hashMap1.put("start", 7);
        hashMap1.put("end", 8);
        hashMap1.put("replace", "区块");

        maps.add(hashMap);
        maps.add(hashMap1);

        Integer start;
        Integer end;
        String replace;
        int i = 0;

        for (Map<String, Object> s : maps) {
            start = (Integer) s.get("start");
            end = (Integer) s.get("end");
            replace = s.get("replace").toString();

            if (i == 0) {
                i = (end - start) - (replace.length());
            }

            int i1 = start > 0 ? (start - i) : 0;

            builder.replace(i1, end - i1, replace);

        }

        System.out.println(builder.toString());

    }
}
