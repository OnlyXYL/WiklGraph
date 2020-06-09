package top.wikl.component.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 是否开启特殊处理附件功能
 *
 * @author XYL
 * @title: SmbCondition
 * @description: TODO
 * @date 2020/4/22 11:37
 * @return
 * @since V1.0
 */
public class SmbCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        String type = conditionContext.getEnvironment().getProperty("graph.special.open");

        if ("true".equals(type)) {
            return true;
        } else {
            return false;
        }
    }
}
