package top.wikl.wikljava.codepoint;

/**
 * 码点和代码单元
 * <p>
 * 码点：是指与一个编码表中的某个字符对应的代码值。
 * <p>
 * 在 Unicode 标准中，码点采用十六进制书写，并加上前缀 U+，例如 U+0041 就是拉丁字母 A 的码点。 Unicode 的码点可以分成 17 个代码级别 （code plane）。第一个代码级别称为 基本的多语言级别 （basic multilingual plane），码点从 U+0000 到 U+FFFF，其中包括经典的 Unicode 代码；其余十六个级别码点从 U+10000 到 U+10FFFF，其中包括一些辅助字符。
 * <p>
 * 在基本多语言级别中，每个字符用 16 位标识，通常被称为 代码单元； 而辅助字符采用一对连续的代码单元进行编码。
 *
 * @author XYL
 * @title: CodePoint
 * @description: TODO
 * @date 2020/3/21 22:41
 * @return
 * @since V1.0
 */
public class CodePoint {

    /**
     * 𝕆 是一个数据符号（U+1D546），码点为：120134
     * <p>
     * String str = "\uD835\uDD46 is the set of octonions";
     * <p>
     * str.charAt(1) 的结果并不是空，而是𝕆 的第二个代码单元
     *
     * @param
     * @return
     * @author XYL
     * @date 2020/3/21 23:09
     * @since V2.0
     */
    public static void main(String[] args) {

        String str = "AB";

        int i = 0;

        //获取指定索引对应的字符
        char c = str.charAt(i);

        //第i个码点
        int index = str.offsetByCodePoints(0, i);
        int codePointAt = str.codePointAt(index);

        String format = String.format("字符串：%s 中，第 %d 字符：%s， 对应的码点是：%d", str, i + 1, c,codePointAt);

        System.out.println(format);

        //获取当前字符串对应的码点"流"
        int[] codePoints = str.codePoints().toArray();

        StringBuilder builder = new StringBuilder();

        for (int j = 0; j < codePoints.length; j++) {
            builder.append(codePoints[j] + " ");
        }

        String format_ = String.format("字符串：%s，对应的码点流是： %s", str, builder);

        System.out.println("\n");
        System.out.println(format_);

        /**
         * 用数组中，从 offset 开始的 count 个码点构造一个字符串
         */
        String s = new String(codePoints, 0, codePoints.length);

        String format1 = String.format("码点流：%s，转为字符串是：%s", builder, s);

        System.out.println("\n");
        System.out.println(format1);


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("C");

        //追加码点，并转为代码单元，即字符
        StringBuilder builder1 = stringBuilder.appendCodePoint(120134);

        String format2 = String.format("字符：%s，追加码点：%d，结果为：%s", "C", 120134, builder1);

        System.out.println("\n");
        System.out.println(format2);

    }

}
