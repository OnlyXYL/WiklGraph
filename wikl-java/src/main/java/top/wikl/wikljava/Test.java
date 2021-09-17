package top.wikl.wikljava;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/4/26 0026 16:27
 */
public class Test {


    public static void main(String[] args) {

        String prase = "TK001井属于TK001井属于A公司";

        String regex = "TK001井+";

        final Pattern compile = Pattern.compile(regex);

        final Matcher matcher = compile.matcher(prase);

        int count = 1;

        while (matcher.find()) {

            final int start = matcher.start();

            final int end = matcher.end();

            System.out.println("第 " + count + "次，开始：" + start + "--结束：" + end);

            count++;
        }
    }
}
