package top.wikl.wikljava.reflect.objectAnalyzer;

import lombok.Data;

import java.io.Serializable;

/**
 * @author XYL
 * @title: Person
 * @description: TODO
 * @date 2020/4/5 16:11
 * @return
 * @since V1.0
 */
@Data
public class Person implements Serializable {

    private String name;

    private String address;

    private String sex;

    private int age;

    public Person(String name, String address, String sex, Integer age) {
        this.name = name;
        this.address = address;
        this.sex = sex;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }
}
