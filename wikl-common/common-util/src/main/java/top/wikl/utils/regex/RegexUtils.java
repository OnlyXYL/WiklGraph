package top.wikl.utils.regex;

/**
 * @author XYL
 * @title: RegexUtils
 * @description: 正则工具类
 * @date 2019/10/18 20:55
 * @return
 * @since V1.0
 */
public class RegexUtils {

    /**
     * 中文、英文、数字包括下划线--->汉字或者字母开头
     */
    private final static String EN_ZH_NUM_ = "^([\\u4E00-\\u9FA5]|[A-Za-z])[\\u4E00-\\u9FA5A-Za-z0-9_]+$";

    /**
     * 判断字符串是不是  【中文、英文、数字包括下划线--->汉字或者字母开头】
     *
     * @param input
     * @return boolean
     * @author XYL
     * @date 2019/10/18 21:15
     * @since V1.0
     */
    public static boolean validLabel(String input) {

        if (input.matches(EN_ZH_NUM_)) {
            return true;
        }

        return false;
    }
}
