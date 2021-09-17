package top.wikl.wikljava.regex;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则
 *
 * @author XYL
 * @version 1.2
 * @since 2021/3/23 0023 14:15
 */
public class RegexLearn {

    public static void main(String[] args) {

        String text = "Hello, my phone number is 010-86432100 and email is cqc@cuiqingcai.com, and my website is https://cuiqingcai.com.";
        String regex = "[a-zA-z]+://[^\\s]*";

        String email = "abcd test@runoob.com 1234";
        String patt1 = "[^\\s]+@[^\\s]+.[a-zA-Z]{2,6}";

        String number = "1a";

        String reg = "^[0-9]?$";

        String re = "^([1-9]{1}\\d*)|0$";

        String train = "aadsfc0";

        String str = " 3 2 ";

        final String replaceAll = str.replaceAll("\s+", "");

        String r = "^(0{1})(\\.\\d{1,3})?$";

        String v = "1.000";

        String dic = "^[\\u4E00-\\u9FA5A-Za-z0-9]{0,15}$";

        String dicName = "测试 0521 test";

        String version = "1.9.9";

        final String replaceAll1 = version.replaceAll("[.]", "");

        int parseInt = Integer.parseInt(replaceAll1);

        parseInt+=1;

        System.out.println(parseInt);

        final String valueOf = String.valueOf(parseInt);

        final String[] split = valueOf.split("");

        final List<String> list = Arrays.asList(split);

        final String format = String.format("v%s", String.join(".", list));

        System.out.println(format);

//        regex(r, v);
    }

    /**
     * 正则表达式
     *
     * @param regex
     * @param text
     * @return void
     * @author XYL
     * @since 13:39 2021/3/24 0024
     **/
    public static void regex(String regex, String text) {

        try {
            final Pattern compile = Pattern.compile(regex);

            final Matcher matcher = compile.matcher(text);

            if (matcher.find()) {
                System.out.println(matcher.group());
            } else {
                System.out.println("no matched");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
