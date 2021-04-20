package top.wikl.wikljava.regex;

import javax.crypto.MacSpi;
import javax.sound.midi.Soundbank;
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

        regex(reg, number);
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
