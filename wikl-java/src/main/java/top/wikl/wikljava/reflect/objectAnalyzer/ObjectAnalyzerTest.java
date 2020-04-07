package top.wikl.wikljava.reflect.objectAnalyzer;

import java.util.ArrayList;

/**
 * @author XYL
 * @title: ObjectAnalyzerTest
 * @description: TODO
 * @date 2020/4/5 15:49
 * @return
 * @since V1.0
 */
public class ObjectAnalyzerTest {

    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {

            list.add(i + "");
        }


        Person person = new Person("张三", "北京", "男", 18);


        ArrayList<Person> people = new ArrayList<>(1);
        people.add(person);
        people.trimToSize();

        Student student = new Student("张三", "北京", "男", 18, "No001", "北京大学");

        System.out.println(student.toString());

        System.out.println(new ObjectAnalyzer().toString(student));

    }

}
