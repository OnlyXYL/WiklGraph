package top.wikl.wikljava.format;

import java.util.Date;

/**
 * 格式化输出
 *
 * Java SE 5.0 沿用了 C 语言的 printf 方法，可以对数值进行格式化
 *
 *
 * 语法:
 *
 * % —————————————————————————————————————————————————————————转换字符—————
 *        ｜                                               ↑ ↑                        ｜  ｜                       ↑     ｜   ｜                               ↑                                    ↑
 *        ｜                                               ｜ ｜                        ｜  ｜                       ｜     ｜   ｜—— . —— 精度——｜                                    ｜
 *        ｜——> 参数索引值——> $ ——｜ ｜—— 标志 <——｜  ｜——> 宽度——｜     ｜                                                                              ｜
 *                                                                                                                                    ｜—————— t ——>  转换字符 —————————｜
 *
 *  ※printf 的转换符
 *
 *  以 % 字符开始的格式说明符都用相应的参数替换
 *
 *  d 表示十进制整数
 *  f 表示浮点数
 *  s 表示字符串
 *
 *  ※printf 的标志
 *
 *  + 打印正数和负数的符号
 *  0 数字前面补 0
 *  - 左对齐
 *  (  将负数括在括号内
 *  , 添加分组分隔符
 *  $ 给定被格式化的参数索引    例如，%1$d，将以是进制格式打印第一个参数
 *  < 格式化前面说明的数值
 *
 *  例如：逗号标识增加了分组的分隔符
 *
 *  System.out.printf("%,.2f",10000.0/3.0)
 *
 *  打印  3,333.33
 *
 *  ※格式化日期和时间
 *  在新代码中，应当使用 java.time包的方法，不过遗留代码中可以看到 Date 类和相关的格式话选项。格式包括两个字母，以 t 开始，以特定的日志转换符结束
 *
 *  例如，
 *
 *  System.out.printf("%tc",new Date());
 *
 *  c 完整的日期和时间       Mone Feb 09
 *  r 12小时时间
 *  R 24小时时间没有秒
 *  Y （大写）4位数字的年（前面补 0）
 *  y （小写）年的后两位数字（前面补 0）
 *  C 年的前两位数字
 *  B 月份的完整拼写
 *  b 或 h 月的缩写
 *  m 两位数字的月（前面补 0）
 *  d 两位数字的日（前面补 0）
 *  e 两位数字的日（前面不补 0）
 *  A 星期几的完整拼写
 *  a 星期几的缩写
 *  H 两位数字的小时（前面补 0），在 0 到 23 之间
 *  k 两位数字的小时（前面不补 0），在0 到 23 之间
 *  I 两位数字的小时（前面补 0），在 0 到 12 之间
 *  l 两位数字的小时（前面不补 0），在 0 到 12 之间
 *  M 两位数字的分钟（前面补 0）
 *  S 两位数字的秒（前面补 0）
 *  L 三位数字的好眠（前面补 0）
 *  N 九位数字的毫秒（前面补 0）
 *  p 上午或下午的标志
 *
 * ※ 许多格式化规则是本地环境特有的
 *
 * @author XYL
 * @title: StringFormat
 * @description: TODO
 * @date 2020/3/22 14:05
 * @return
 * @since V1.0
 */
public class StringFormat {

    public static void main(String[] args) {

        System.out.printf("Hello,%s.Nice to meet you,I`m %d years old","张三",12);

        System.out.println("\n");
        System.out.printf("%,7.2f",10000.0/3.0);

        System.out.println("\n");

        System.out.printf("%tc",new Date());

        System.out.println("\n");

        //B 转换符好像不好使，不是完整拼写，而是中文
        System.out.printf("%1$s %2$tB %2$td %2$tY","Due Date:",new Date());

        System.out.println("\n");
        System.out.printf("%s %tB %<td %<tY","Due Date:",new Date());

    }

}
