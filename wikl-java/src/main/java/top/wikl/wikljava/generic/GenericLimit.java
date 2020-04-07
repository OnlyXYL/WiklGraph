package top.wikl.wikljava.generic;

import top.wikl.wikljava.generic.model.Pair;
import top.wikl.wikljava.reflect.objectAnalyzer.Student;

/**
 * 泛型限制
 *
 * @author XYL
 * @title: GenericLimit
 * @description: TODO
 * @date 2020/4/6 17:06
 * @return
 * @since V1.0
 */
public class GenericLimit {

    public static void main(String[] args) {

        notArray();

    }

    /**
     * 运行时类型查询只适用于原始类型
     *
     * @param
     * @return
     * @author XYL
     * @date 2020/4/6 17:20
     * @since V2.0
     */
    public void noRunTinmeQuery(Object object) {

        /**
         * 编译错误
         */
//        if (object instanceof Pair<Student>) {}

        /**
         * 产生警告
         */
        Pair<Student> object1 = (Pair<Student>) object;

    }

    /**
     * 不能创建参数化类型额数组
     * <p>
     * Pair<String>[] table = new Pair<String>[10];
     * <p>
     * 类型擦除之后，table 的类型是 Pair[]，可以转换为 Object[]，但是数组会记住它的元素类型，如果试图存储其他类型的元素，就会抛出一个 ArrayStoreException 异常。
     * <p>
     * objArray[0] = "hello";
     * <p>
     * 不过对于泛型类型，擦除回事这种机制无效。以下赋值：
     * <p>
     * objArray[0] = new Pair<String>();
     * <p>
     * 能够通过数组存储的检查
     * <p>
     * 需要说明的是，只是不允许创建这些数组，而声名类型 Pair<>String>[] 的变量仍是合法的。不过不能用  new Pair<String>[10] 初始化这个变量。
     *
     * @param
     * @return
     * @author XYL
     * @date 2020/4/6 17:10
     * @since V2.0
     */
    public static void notArray() {

        Pair<String>[] table = new Pair[10];

        Object[] objArray = table;

        objArray[0] = "hello";

        objArray[0] = new Pair<String>();

    }

}
