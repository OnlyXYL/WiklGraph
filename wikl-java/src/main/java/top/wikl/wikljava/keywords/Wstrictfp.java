package top.wikl.wikljava.keywords;

/**
 * 关键字 strictfp
 * <p>
 * 标记的方法，所有的指令都将使用严格的浮点计算
 * <p>
 * 标记的类，所有的方法都要使用严格的浮点计算
 * <p>
 * 可移植性是Java语言的设计目标之一，无论在哪个虚拟机上执行，同一运算应该得到同样的结果。对于浮点数的运算，实现可移植性是相当困难的，double类型使用64为存储一个数据，但是有些处理器使用80位浮点寄存器。这些寄存器增加了中间过程的计算精度，例如：
 * <p>
 * double w = x * y / z;
 * <p>
 * 很多intel 处理器计算 x*y，并且将结果存储在80位的寄存器中，再除以 z 并将结果截断为 64位。这样可以得到一个更加精确的结果，并且还能够避免产生指数溢出。但是这个结果可能与始终在64位机器上的计算结果不一样。因此，java虚拟机的最初规范规定所有的中间计算结果都必须进行截断。但是遭到了数值计算团队的反对，截断计算不仅可能导致溢出，而且由于截断操作需要消耗时间，所以在计算速度上要比精确计算慢。为此，Java程序设计者承认了最优性能与理想结果之间存在冲突，并给予了改进。在默认情况下，虚拟机设计者允许对中间计算结果采用扩展的精度。但是，对于使用 stricftp 关键字标记的方法必须使用严格的浮点计算来生成可再生的结果。
 * <p>
 * 实际的计算方式将取决于 Intel 处理器的行为，在默认情况下，中间结果允许使用扩展的指数，但是不允许使用扩展的位数（不懂）。因此，这两种方式的区别在于采用默认的方式不会产生溢出，而采用严格的计算有可能产生溢出。
 * <p>
 * 所以，个人觉得还是不用这个关键字的好吧！
 *
 * @author XYL
 * @title: Wstrictfp
 * @description: TODO
 * @date 2020/3/21 19:00
 * @return
 * @since V1.0
 */
public class Wstrictfp {

    public static void main(String[] args) {

        double sum = caculate(1, 2, 2);

        System.out.println(sum);
    }

    /**
     * 浮点运算
     *
     * @param x
     * @param y
     * @param z
     * @return
     * @author XYL
     * @date 2020/3/21 19:11
     * @since V2.0
     */
    private static strictfp double caculate(int x, int y, int z) {
        return x * y / z;
    }

}
