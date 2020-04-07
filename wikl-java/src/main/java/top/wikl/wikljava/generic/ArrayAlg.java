package top.wikl.wikljava.generic;

import top.wikl.wikljava.generic.model.Pair;

/**
 * @author XYL
 * @title: ArrayAlg
 * @description: TODO
 * @date 2020/4/6 14:42
 * @return
 * @since V1.0
 */
public class ArrayAlg {

    public static void main(String[] args) {

        String[] words = {"Mary", "had", "a", "little", "lamb"};

        Pair<String> minmax = ArrayAlg.minmax(words);

        System.out.printf("min: %s \n", minmax.getFirst());

        System.out.printf("max: %s \n", minmax.getSecond());

        String middle = ArrayAlg.getMiddle("John", "Q.", "Public");

        System.out.printf("middle: %s \n", middle);

        /**
         * 编译器将会自动打包参数为 1 个 Double 和 2 个 Integer ,然后寻找这些类的公共超类
         */
        Number number = ArrayAlg.getMiddle(3.14, 1729, 0);

        System.out.printf("number: %s \n", number.intValue());

        Object hello = ArrayAlg.getMiddle("Hello", 0, null);

        System.out.println("hello: " + hello.toString());
    }

    /**
     * @param a
     * @return
     * @author XYL
     * @date 2020/4/6 14:51
     * @since V2.0
     */
    public static Pair<String> minmax(String[] a) {

        if (a == null || a.length == 0) {

            return null;
        }

        String min = a[0];
        String max = a[0];

        for (int i = 0; i < a.length; i++) {
            if (min.compareTo(a[i]) > 0) {
                min = a[i];
            }

            if (max.compareTo(a[i]) < 0) {

                max = a[i];
            }

        }
        return new Pair<>(min, max);
    }

    /**
     * 泛型方法
     * <p>
     * 类型变量放在修饰符的后面，返回类型的前面
     *
     * @param
     * @return
     * @author XYL
     * @date 2020/4/6 14:53
     * @since V2.0
     */
    public static <T> T getMiddle(T... a) {
        return a[a.length / 2];
    }

    /**
     * 类型变量的限定
     * <p>
     * 注：由于参数类型是 T，意味着可以是任何类型，因此要使用 compareTo 方法比较大小，需要保证 T 所属的类实现了 Comparable 接口
     *
     * @param
     * @return
     * @author XYL
     * @date 2020/4/6 15:18
     * @since V2.0
     */
    public static <T extends Comparable> T min(T[] a) {

        if (a == null || a.length == 0) {

            return null;
        }

        T min = a[0];
        for (int i = 0; i < a.length; i++) {

            if (min.compareTo(a[i]) > 0) {

                min = a[i];
            }

        }

        return min;

    }

}
