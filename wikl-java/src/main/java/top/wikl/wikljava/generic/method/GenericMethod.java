package top.wikl.wikljava.generic.method;

import top.wikl.wikljava.generic.DateInterval;
import top.wikl.wikljava.generic.model.Pair;

import java.time.LocalDate;

/**
 * 翻译泛型方法
 *
 * @author XYL
 * @title: GenericMethod
 * @description: TODO
 * @date 2020/4/6 16:06
 * @return
 * @since V1.0
 */
public class GenericMethod {

    public static void main(String[] args) {

        DateInterval dateInterval = new DateInterval();

        dateInterval.setFirst(LocalDate.now());

        Pair<LocalDate> pair = dateInterval;

        pair.setSecond(LocalDate.now());

    }

}
