package top.wikl.wikljava.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

/**
 * 分析类结构
 *
 * @author XYL
 * @title: ReflectTest
 * @description: TODO
 * @date 2020/4/5 14:14
 * @return
 * @since V1.0
 */
public class ReflectTest {

    public static void main(String[] args) {

        String name;

        if (args.length > 0) {

            name = args[0];
        } else {

            Scanner in = new Scanner(System.in);

            System.out.println("Enter class name (e.g. java.util.Date)：");

            name = in.nextLine();
        }

        try {
            Class<?> aClass = Class.forName(name);

            Class<?> superclass = aClass.getSuperclass();

            String modifiers = Modifier.toString(aClass.getModifiers());

            if (modifiers.length() > 0) {
                System.out.print(modifiers + " ");
            }

            System.out.print("class " + name);

            //判断是否有父类
            if (superclass != null && superclass != Object.class) {

                System.out.print(" extends " + superclass.getName());
            }

            System.out.print(" {\n\n");

            //打印构造方法
            printConstructors(aClass);

            System.out.println();

            //打印方法
            printMethods(aClass);

            System.out.println();

            //打印字段
            printFields(aClass);

            System.out.println();

            System.out.println("}");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }

    }


    /**
     * 打印构造方法
     *
     * @param
     * @return
     * @author XYL
     * @date 2020/4/5 14:17
     * @since V1.0
     */
    public static void printConstructors(Class aClass) {

        Constructor[] constructors = aClass.getDeclaredConstructors();

        for (Constructor constructor : constructors) {

            //获取构造方法名
            String name = constructor.getName();

            System.out.print(" ");

            //获取修饰符
            String modifiers = Modifier.toString(constructor.getModifiers());

            if (modifiers.length() > 0) {
                System.out.print(modifiers + " ");
            }

            System.out.print(name + "(");

            //print parameter types
            Class[] parameterTypes = constructor.getParameterTypes();

            for (int i = 0; i < parameterTypes.length; i++) {
                if (i > 0) {

                    System.out.print(", ");
                }

                System.out.print(parameterTypes[i].getName());

            }

            System.out.println(");");

        }

    }

    /**
     * 打印方法
     *
     * @param
     * @return
     * @author XYL
     * @date 2020/4/5 14:25
     * @since V2.0
     */
    public static void printMethods(Class aClass) {

        Method[] methods = aClass.getDeclaredMethods();

        for (Method method : methods) {

            Class<?> returnType = method.getReturnType();

            System.out.print(" ");

            //打印 修饰符  返回值类型  方法名  参数
            String modifiers = Modifier.toString(method.getModifiers());

            if (modifiers.length() > 0) {
                System.out.print(modifiers + " ");
            }

            System.out.print(returnType.getName() + " " + method.getName() + "(");

            //打印参数
            Class<?>[] parameterTypes = method.getParameterTypes();

            for (int j = 0; j < parameterTypes.length; j++) {

                if (j > 0) {

                    System.out.print(", ");
                }

                System.out.print(parameterTypes[j].getName());

            }

            System.out.println(");");

        }

    }

    /**
     * 打印变量
     *
     * @param
     * @return
     * @author XYL
     * @date 2020/4/5 14:39
     * @since V2.0
     */
    public static void printFields(Class aClass) {

        Field[] declaredFields = aClass.getDeclaredFields();

        for (Field field : declaredFields) {

            Class<?> type = field.getType();

            System.out.print(" ");

            String modifiers = Modifier.toString(field.getModifiers());

            if (modifiers.length() > 0) {
                System.out.print(modifiers + " ");
            }

            System.out.println(type.getName() + " " + field.getName() + ";");

        }

    }

}


