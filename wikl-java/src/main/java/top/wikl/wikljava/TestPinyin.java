package top.wikl.wikljava;

import top.wikl.wikljava.utils.ChineseToPinYin;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/8/23 0023 15:55
 */
public class TestPinyin {

    public static void main(String[] args) {

        String property = "内部/外部";

        final String pinYin = ChineseToPinYin.getPinYin(property);

        final String pinYin1 = ChineseToPinYin.getPinYin(property.replaceAll("[/\\\\?？,，.。；;!！&%$￥@(（）)<>《》{}【】+]", ""));

        System.out.println(pinYin);
        System.out.println(pinYin1);
    }
}
