package top.wikl.wikljava.date;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * @author XYL
 * @version 1.2
 * @date 2022/04/24 11:47
 */
public class DateUtils {

    public static void main(String[] args) {

        final Date date = new Date();

//        date.setTime(1650771570864L);
//        date.setTime(1650771575432L);
        date.setTime(1650620178207L);

        final String format = DateFormatUtils.format(date, "YYYY-MM-dd HH:mm:ss");

        System.out.println(format);
    }
}
