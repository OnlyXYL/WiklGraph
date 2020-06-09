package top.wikl.wikljava.reflect.objectAnalyzer;

import org.bouncycastle.math.raw.Mod;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * 分析对象
 *
 * @author XYL
 * @title: ObjectAnalyzer
 * @description: TODO
 * @date 2020/4/5 15:03
 * @return
 * @since V1.0
 */
public class ObjectAnalyzer {

    private ArrayList<Object> visited = new ArrayList<>();

    /**
     * Converts an objects to a string
     *
     * @param
     * @return
     * @author XYL
     * @date 2020/4/5 15:21
     * @since V2.0
     */
    public String toString(Object object) {


        if (Objects.isNull(object)) {
            return "null";
        }

        if (visited.contains(object)) {
            return "...";
        }

        visited.add(object);

        Class<?> aClass = object.getClass();

        if (aClass == String.class) {
            return "'" + object + "'";
        }

        if (aClass == Integer.class) {

            return object + "";
        }

        if (aClass.isArray()) {

            //获取数组类型
            String r = aClass.getComponentType() + "[]{";

            for (int i = 0; i < Array.getLength(object); i++) {

                if (i > 0) {
                    r += ",";
                }

                //获取数组中指定索引的值
                Object value = Array.get(object, i);

                //判断是基本类型数组，还是对象数组
                if (aClass.getComponentType().isPrimitive()) {
                    r += value;
                } else {
                    r += toString(value);
                }

            }

            return r + "}";

        }

        //对象
        String r = aClass.getName();

        r += "[";

        do {

            Field[] fields = aClass.getDeclaredFields();

            AccessibleObject.setAccessible(fields, true);

            //获取所有属性和属性值
            for (Field field : fields) {

                if (!Modifier.isStatic(field.getModifiers())) {

                    if (!r.endsWith("[")) {
                        r += ",";
                    }

                    r += field.getName() + "=";

                    try {
                        //获取值
                        Class type = field.getType();

                        Object value = field.get(object);

                        if (type.isPrimitive()) {

                            r += value;
                        } else {
                            r += toString(value);
                        }


                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }


                }

            }

            Class<?> superclass = aClass.getSuperclass();

            if (superclass != Object.class) {

                aClass = superclass;
            } else {
                aClass = null;
            }

        } while (aClass != null);

        r += "]";

        return r;

    }

}
