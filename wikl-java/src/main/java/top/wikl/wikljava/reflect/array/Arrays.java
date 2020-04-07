package top.wikl.wikljava.reflect.array;

import top.wikl.wikljava.reflect.objectAnalyzer.Student;

import java.lang.reflect.Array;

/**
 * 通用扩展数组
 *
 * @author XYL
 * @title: Arrays
 * @description: TODO
 * @date 2020/4/5 17:48
 * @return
 * @since V1.0
 */
public class Arrays {

    public static void main(String[] args) {

        System.out.println("++++++++++++++++++++ 基础类型数组 ++++++++++++++++++++");
        System.out.println();

        int[] ints = {1, 2, 3, 4, 5};

        System.out.println("【原数组】：" + java.util.Arrays.toString(ints));
        System.out.println("【类型】：int[]");

        System.out.println();
        System.out.printf("【复制前】数组长度：%d，元素：%s \n", ints.length, java.util.Arrays.toString(ints));

        int[] copyOf = (int[]) copyOf(ints, 10);

        System.out.printf("【复制后】数组长度：%d，元素：%s", copyOf.length, java.util.Arrays.toString(copyOf));

        System.out.println("\n");
        System.out.println("++++++++++++++++++++++ 对象数组 ++++++++++++++++++++++");
        System.out.println();

        Student[] students = new Student[2];

        for (int j = 0; j < students.length; j++) {

            students[j] = new Student("学生_" + j, "北京", "男", 18, "No111", "北京大学");
        }

        System.out.println("【原数组】：" + java.util.Arrays.toString(students));
        System.out.println("【类型】：Student[]");

        System.out.println();
        System.out.printf("【复制前】数组长度：%d，元素：%s \n", students.length, java.util.Arrays.toString(students));

        Student[] copyOf1 = (Student[]) copyOf(students, 5);

        System.out.printf("【复制后】数组长度：%d，元素：%s", copyOf1.length, java.util.Arrays.toString(copyOf1));

        System.out.println("\n");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");

    }

    /**
     * 动态扩展数组
     *
     * @param
     * @return
     * @author XYL
     * @date 2020/4/5 17:49
     * @since V2.0
     */
    public static Object copyOf(Object source, int newLength) {

        Class<?> aClass = source.getClass();

        if (!aClass.isArray()) {

            return null;
        }

        //数组类型
        Class<?> componentType = aClass.getComponentType();

        int length = Array.getLength(source);

        Object target = Array.newInstance(componentType, newLength);

        System.arraycopy(source, 0, target, 0, Math.min(length, newLength));

        return target;

    }

}
