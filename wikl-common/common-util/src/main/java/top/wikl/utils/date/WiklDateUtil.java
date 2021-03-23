package top.wikl.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 日期格式化工具
 *
 * @param
 * @author XYL
 * @date 2019/9/27 12:45
 * @return
 * @since V1.0
 */
public class WiklDateUtil {

    /**
     * default date format pattern
     */
    public final static String DATE_FORMAT = "yyyy-MM-dd";
    public final static String DATE_FORMAT_ = "yyyy年MM月dd日";
    public final static String MONTH_DATE_FORMAT = "MM-dd";
    public final static String YEAR_WEEK_FORMAT = "yyyy-ww";
    public final static String YEAR_MONTH_FORMAT = "yyyy-MM";
    public final static String YEAR_MONTH_DAY_FORMAT = "yyyyMMdd";
    public final static String YEAR_WEEK_FORMAT_SHORT = "yy-ww";
    public final static String YEAR_MONTH_FORMAT_SHORT = "yy-MM";
    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public final static String FULL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static String TIME_FORMAT = "HH:mm";
    public final static String MONTH_DAY_HOUR_MINUTE_FORMAT = "MM-dd HH:mm";
    public final static String LOCATE_DATE_FORMAT = "yyyyMMddHHmmss";

    private final static SimpleDateFormat LOCATE_DATE_FORMAT2 = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final int DAYS_OF_A_WEEK = 7;

    private final static SimpleDateFormat SDF_YEAR = new SimpleDateFormat("yyyy");

    private final static SimpleDateFormat SDF_DAY = new SimpleDateFormat("yyyy-MM-dd");

    private final static SimpleDateFormat SDF_DAYS = new SimpleDateFormat("yyyyMMdd");

    private final static SimpleDateFormat SDF_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final static SimpleDateFormat SDF_TIMES = new SimpleDateFormat("yyyyMMddhhmmss.SSS");

    /**
     * 获取yyyyMMddhhmmss.SSS格式
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 12:54
     * @since V1.0
     */
    public static String getTimeMs() {

        return SDF_TIMES.format(new Date());
    }

    /**
     * 获取YYYY格式
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 12:54
     * @since V1.0
     */
    public static String getYear() {
        return SDF_YEAR.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 12:54
     * @since V1.0
     */
    public static String getDay() {
        return SDF_DAY.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/10/30 18:00
     * @since V1.0
     */
    public static String getDay(Date date) {
        return SDF_DAY.format(date);
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 12:54
     * @since V1.0
     */
    public static String getDays() {
        return SDF_DAYS.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 12:54
     * @since V1.0
     */
    public static String getTime() {
        return SDF_TIME.format(new Date());
    }

    /**
     * 日期比较 ， 如果s > = e 返回true 否则返回false
     *
     * @param s, e
     * @return boolean
     * @author XYL
     * @date 2019/9/27 12:53
     * @since V1.0
     */
    public static boolean compareDate(String s, String e) {

        if (fomatDate(s) == null || fomatDate(e) == null) {
            return false;
        }
        return fomatDate(s).getTime() >= fomatDate(e).getTime();
    }

    /**
     * 格式化日期
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 12:55
     * @since V1.0
     */
    public static Date fomatDate(String date) {
        try {
            return SDF_DAY.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 校验日期是否合法
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 12:55
     * @since V1.0
     */
    public static boolean isValidDate(String s) {
        try {
            SDF_DAY.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    /**
     * 时间相减得到年数
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 12:55
     * @since V1.0
     */
    public static int getDiffYear(String startTime, String endTime) {
        try {
            int years = (int) (((SDF_DAY.parse(endTime).getTime() - SDF_DAY.parse(startTime).getTime()) / (1000 * 60 * 60 * 24))
                    / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * 时间相减得到天数
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 12:55
     * @since V1.0
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {

        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = SDF_DAY.parse(beginDateStr);
            endDate = SDF_DAY.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        // ("相隔的天数="+day);

        return day;
    }

    /**
     * 得到n天之后的日期
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 12:56
     * @since V1.0
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);

        /**
         * java.util包
         */
        Calendar canlendar = Calendar.getInstance();

        /**
         * 日期减 如果不够减会将月变动
         */
        canlendar.add(Calendar.DATE, daysInt);
        Date date = canlendar.getTime();

        String dateStr = SDF_TIME.format(date);

        return dateStr;
    }

    /**
     * 得到n天之后的日期
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 12:56
     * @since V1.0
     */
    public static String getAfterDayDate(String days, String format) {
        int daysInt = Integer.parseInt(days);

        /**
         * java.util包
         */
        Calendar canlendar = Calendar.getInstance();

        /**
         * 日期减 如果不够减会将月变动
         */
        canlendar.add(Calendar.DATE, daysInt);
        Date date = canlendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        String dateStr = dateFormat.format(date);

        return dateStr;
    }

    /**
     * 得到n天之后是周几
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 12:56
     * @since V1.0
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);

        /**
         * java.util包
         */
        Calendar canlendar = Calendar.getInstance();

        /**
         * 日期减 如果不够减会将月变动
         */
        canlendar.add(Calendar.DATE, daysInt);
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 常规自动日期格式识别
     * @param str 时间字符串
     * @return Date
     * @author dc
     */
    public static String getDateFormat(String str) {
        boolean year = false;
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if(pattern.matcher(str.substring(0, 4)).matches()) {
            year = true;
        }
        StringBuilder sb = new StringBuilder();
        int index = 0;
        if(!year) {
            if(str.contains("月") || str.contains("-") || str.contains("/")) {
                if(Character.isDigit(str.charAt(0))) {
                    index = 1;
                }
            }else {
                index = 3;
            }
        }
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if(Character.isDigit(chr)) {
                if(index==0) {
                    sb.append("y");
                }
                if(index==1) {
                    sb.append("M");
                }
                if(index==2) {
                    sb.append("d");
                }
                if(index==3) {
                    sb.append("H");
                }
                if(index==4) {
                    sb.append("m");
                }
                if(index==5) {
                    sb.append("s");
                }
                if(index==6) {
                    sb.append("S");
                }
            }else {
                if(i>0) {
                    char lastChar = str.charAt(i-1);
                    if(Character.isDigit(lastChar)) {
                        index++;
                    }
                }
                sb.append(chr);
            }
        }
        return sb.toString();
    }
}
