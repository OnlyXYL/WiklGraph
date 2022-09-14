package top.wikl.annotation;

import org.springframework.context.annotation.Import;
import top.wikl.selector.CloudApplicationSelector;

import java.lang.annotation.*;

/**
 * @ProjectName EnableCloudApplication
 * @author Administrator
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021/4/3 0003 19:27
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CloudApplicationSelector.class)
public @interface EnableCloudApplication {

}