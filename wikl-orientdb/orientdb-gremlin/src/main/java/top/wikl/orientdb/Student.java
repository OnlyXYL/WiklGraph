package top.wikl.orientdb;

import lombok.Data;

/**
 * @author XYL
 * @title: Student
 * @description: TODO
 * @date 2020/7/28 19:44
 * @return
 * @since V1.2
 */
@Data
public class Student {

    private String name;

    private String sex;

    private Integer age;

    public Student() {
    }

    public Student(String name, String sex, Integer age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }
}
