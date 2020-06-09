package top.wikl.wikljava.reflect.objectAnalyzer;

import lombok.Data;

/**
 * @author XYL
 * @title: Student
 * @description: TODO
 * @date 2020/4/5 16:50
 * @return
 * @since V1.0
 */
@Data
public class Student extends Person {

    private String studentNo;

    private String school;

    public Student(String name, String address, String sex, Integer age, String studentNo, String school) {
        super(name, address, sex, age);
        this.studentNo = studentNo;
        this.school = school;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentNo='" + studentNo + '\'' +
                ", school='" + school + '\'' +
                '}';
    }
}
