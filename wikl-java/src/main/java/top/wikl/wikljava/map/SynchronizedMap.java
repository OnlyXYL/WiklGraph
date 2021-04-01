package top.wikl.wikljava.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/3/30 0030 10:53
 */
public class SynchronizedMap {

    public static void main(String[] args) {

        Map<String, Object> map = Collections.synchronizedMap(new HashMap<String, Object>());
    }
}
