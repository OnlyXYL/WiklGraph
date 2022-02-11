package top.wikl.wikljava.tree;

import java.util.*;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/10/28 0028 11:57
 */
public class StackTest {

    public static void main(String[] args) {

        final Stack stack = createStack();

        final Iterator iterator = stack.stream().iterator();

        while (iterator.hasNext()){
            final Object next = iterator.next();

            System.out.println(next.toString());
        }

    }

    /**
     * 创建栈
     *
     * @param
     * @return java.util.Stack
     * @author XYL
     * @since 11:59 2021/10/28 0028
     **/
    public static Stack createStack() {

        final Stack<Object> stack = new Stack<>();

        stack.push("A");


        stack.push(Arrays.asList("B","C"));


        return stack;
    }

}
