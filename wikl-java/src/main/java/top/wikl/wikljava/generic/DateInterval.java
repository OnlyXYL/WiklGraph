package top.wikl.wikljava.generic;

import top.wikl.wikljava.generic.model.Pair;

import java.time.LocalDate;

/**
 * @author XYL
 * @title: DateInterval
 * @description: TODO
 * @date 2020/4/6 16:01
 * @return
 * @since V1.0
 */
public class DateInterval extends Pair<LocalDate> {

    @Override
    public void setSecond(LocalDate second){

        if (second.compareTo(getFirst()) >=0) {
            super.setSecond(second);
        }

    }

}
