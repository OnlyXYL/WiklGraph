package top.wikl.orientdb;

import top.wikl.utils.json.JsonUtils;

import java.util.HashMap;

/**
 * @author XYL
 * @title: Test
 * @description: TODO
 * @date 2020/7/24 17:53
 * @return
 * @since V1.2
 */
public class Test {

    public static void main(String[] args) {

        HashMap<Student, Integer> map = new HashMap<>();

        Student hong = new Student("红太狼", "女", 1);

        method(hong,map);

        Student hui = new Student("灰太狼", "男", 1);

        method(hui,map);

        Student hong_1 = new Student("红太狼", "女", 1);

        method(hong_1,map);

        System.out.println(JsonUtils.parseObjToJson(map));

    }

    public static void method(Student student, HashMap<Student, Integer> map) {
        if (map.containsKey(student)) {

            Integer integer = map.get(student);

            integer = integer +1;

            map.put(student, integer);

        } else {
            map.put(student, 1);
        }
    }

}
