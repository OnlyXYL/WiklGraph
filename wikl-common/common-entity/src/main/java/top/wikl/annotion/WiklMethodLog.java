package top.wikl.annotion;

import java.lang.annotation.*;

/**
 * 记录方法执行日志的自定义注解
 * <p>
 * Indicates how to record the log during running of method
 * <p>
 * eg:
 *
 * @param
 * @author XYL
 * @MethodLog public void test(){
 * System.out.println("AOP方式，给方法增加日志输出");
 * }
 * @date 2019/9/27 11:04
 * @return
 * @since V1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WiklMethodLog {

    String value() default "";
}
