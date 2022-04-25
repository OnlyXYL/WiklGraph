package top.wikl.component.sentinel.fallback;

/**
 * @author XYL
 * @version 1.2
 * @since 2022/2/19 0019 17:37
 */
public class SentinelServiceFallback {

    /**
     * testSentinel 回调函数
     *
     * @param input
     * @param throwable
     * @return java.lang.String
     * @author XYL
     * @since 17:38 2022/2/19 0019
     **/
    String testSentinelFallback(String input, Throwable throwable){

        final String format = String.format("方法调用失败了，异常是：{}，这是降级执行的结果！！！！！", throwable.getMessage());

        return format;
    }
}
