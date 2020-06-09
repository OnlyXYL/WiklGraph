package top.wikl.wikljava.generic.model;

/**
 * 泛型类
 *
 * @author XYL
 * @title: Pair
 * @description: TODO
 * @date 2020/4/6 14:43
 * @return
 * @since V1.0
 */
public class Pair<T> {

    private T first;

    private T second;

    public Pair() {
    }

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }
}
