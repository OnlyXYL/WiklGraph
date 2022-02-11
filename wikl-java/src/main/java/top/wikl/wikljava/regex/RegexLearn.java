package top.wikl.wikljava.regex;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

        parseInt += 1;

        System.out.println(parseInt);

        final String valueOf = String.valueOf(parseInt);

        final String[] split = valueOf.split("");

        final List<String> list = Arrays.asList(split);

        final String format = String.format("v%s", String.join(".", list));
        System.out.println(format);
        //正则表达式，去除word章节的序号
        removeXhFromChapter();


        splitByCX();

//        String parent = "岩屑.录井.剖面.数据.深度.归位";
//        String parent = "含有物.解释.深度.归位";
        String parent = "试油.";

//        String coreWords = "岩屑.录井.剖面.数据.归位";
//        String coreWords = "含有物.解释.归位";
        String coreWords = "试油.地质.设计";

        extractCommonFactors(parent, coreWords);

    }

    /**
     * 去除章节序号
     *
     * @param
     * @return void
     * @author XYL
     * @since 21:50 2021/10/24 0024
     **/
    public static void removeXhFromChapter() {
        String rr = "^[\\\\\\\\(\\\\\\\\（]?[[一二三四五六七八九十0-9a-zA-Z]+[、.]?[一二三四五六七八九十0-9a-zA-Z]]*[\\\\\\\\)\\\\\\\\）]?[、.\s]?";

        List<String> list1 = Arrays.asList("1.1.1.第一章节", "1.1.1. 第二章节", "1.1、第三章节", "一、第四章节", "(一)、第五章节", "a.b 第六章节", "1.1.1) 第七章节", "1）第八章节", "a) 第九章节", "");

        list1.forEach(a -> {
            String s = a.replaceAll(rr, "");
            System.out.println(s);
        });

    }

    /**
     * 根据词性切分
     * <p>
     * /v，/b，/o，/po
     *
     * @param
     * @return void
     * @author XYL
     * @since 21:50 2021/10/24 0024
     **/
    public static void splitByCX() {

//        String words = "井位/pp.设计/v_.区块/po_.单元/pp_.名称/pp.";
        String words = "井筒/o_温度/pp、/fh压力/pp测试/b_";
//        String words = "固井";

        String regex = "/(b_|v_|o_|po)";

//        final String all = words.replaceAll("[.]", "");

        String[] split = words.split(regex);

        LinkedList<String> strings = new LinkedList<>(Arrays.asList(split));

        final List<String> collect = strings.stream().map(s -> s.replaceAll("/[a-z]*", "")).collect(Collectors.toList());

        collect.forEach(System.out::println);
    }


    /**
     * 提取公因式
     *
     * @return java.lang.String
     * @author XYL
     * @since 12:21 2021/11/21 0021
     **/
    public static String extractCommonFactors(String parent, String coreWords) {

        //拼接词性
//        parent = "岩屑/o_.录井/b_.剖面/po.数据/po.深度.归位/pm";
//        parent ="含有物.解释.深度.归位";

//        final String replaceAll = parent.replaceAll("[.]", "");

//        final String[] split = parent.split("/(b_|v_|o_|po|pm)");
        final String[] split = parent.split("[.]");

        final int length = split.length;

        if (length > 1) {
            for (int i = length - 1; i > 0; i--) {
                String append = append(split, i);

                if (coreWords.startsWith(append)) {
                    System.out.println("提取公因式结果：" + append);

                    if (append.endsWith(".")) {

                        int indexOf = append.lastIndexOf(".");

                        append = append.substring(0,indexOf);

                    }


                    final String format = String.format("(%s)", append);

                    final String[] strings = parent.split(format);

                    final String string = strings[strings.length - 1];

                    System.out.println(string);

                    return append;
                }
            }
        } else {
            if (coreWords.startsWith(parent)&&coreWords.contains(".")) {
                System.out.println("提取公因式结果：" + parent);

                if (parent.endsWith(".")) {

                    int indexOf = parent.lastIndexOf(".");

                    parent = parent.substring(0,indexOf);

                }

                final String format = String.format("(%s.)", parent);

                final String[] strings = coreWords.split(format);

                final String string = strings[strings.length - 1];

                System.out.println(string);

                return parent;
            }
        }


        return "";
    }

    public static String append(String[] split, int i) {

        final StringBuffer buffer = new StringBuffer();

        for (int j = 0; j <= i; j++) {

            buffer.append(split[j]).append(".");

        }
        final String toString = buffer.toString();
//        final String replaceAll = toString.replaceAll("/[a-z]*", "");

        return toString;
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
