package top.wikl.wikljava.test;

/**
 * @author XYL
 * @version 1.2
 * @date 2022/03/23 9:18
 */
public class TestInt {

    public static void main(String[] args) {

        int a = 1;


        int b = 5;

        a = repeat(a, b);

        System.out.println("递归后 a :" + a);
    }


    public static int repeat(int a, int b) {

        if (b == 0) {

            return 0;
        }

        b = b - 1;

        a = test_(a);

        System.out.println("循环后a：" + a);

        repeat(a, b);

        return a;
    }

    public static int test_(int a) {

        for (int i = 0; i < 10; i++) {

            a = a + 1;
        }

        System.out.println(a);

        return a;
    }


}
