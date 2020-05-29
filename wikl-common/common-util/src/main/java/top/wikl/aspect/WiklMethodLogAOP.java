package top.wikl.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import top.wikl.annotion.WiklMethodLog;
import top.wikl.enums.log.LogLevel;
import top.wikl.utils.holder.WiklLogHolder;
import top.wikl.utils.json.JsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 记录方法执行日志的切面处理类
 *
 * @param
 * @author XYL
 * @date 2019/2/27 14:24
 * @return
 * @since V1.0
 */
@Aspect
@Component
@Slf4j
public class WiklMethodLogAOP {

    @Pointcut("@annotation(top.wikl.annotion.WiklMethodLog)")
    public void costTimePointCut() {
    }

    /**
     * 环绕增强
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/10/30 17:33
     * @since V1.0
     */
    @Around("costTimePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        WiklLogHolder.set(System.currentTimeMillis());

        StringBuilder sb = new StringBuilder();

        MethodSignature signature = (MethodSignature) point.getSignature();

        WiklMethodLog methodLog = signature.getMethod().getAnnotation(WiklMethodLog.class);

        //日志级别
        LogLevel level = methodLog.level();

        Object[] args = point.getArgs();

        //方法前处理
        before(sb, signature, args);

        //执行方法
        Object result = point.proceed();

        //方法后处理
        after(sb, result);

        if (LogLevel.DEBUG.equals(level)) {
            log.info(sb.toString());
        }

        return result;
    }

    /**
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/28 10:48
     * @since V1.1
     */
    @AfterThrowing(value = "costTimePointCut()", throwing = "e")
    private void doAfterThrow(JoinPoint joinPoint, Throwable e) {

        //获取方法名
        String methodName = joinPoint.getSignature().getName();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        //类名
        String className = signature.getDeclaringType().getName();

        WiklMethodLog wiklMethodLog = signature.getMethod().getAnnotation(WiklMethodLog.class);

        StringBuilder sb = new StringBuilder();

        String className_ = joinPoint.getTarget().getClass().getSimpleName();

        sb.append("\n").append("\n").append("【Service】").append(">>>>>").append(className_).append("\n").append("\n");

        sb.append("【功能】：").append(wiklMethodLog.description()).append("\n");

        sb.append("【类名】：").append(className).append("\n");

        sb.append("【方法】：").append(methodName).append("\n");

        //参数集合
        Map<String, Object> params = new HashMap<>();

        Object[] args = joinPoint.getArgs();

        String[] parameterNames = signature.getParameterNames();

        for (int i = 0; i < parameterNames.length; i++) {
            String name = parameterNames[i];

            params.put(name, args[i].toString());
        }
        sb.append("【参数】：").append(JsonUtils.parseObjToJson(params)).append("\n");

        // 得到异常棧的首个元素
        StackTraceElement stackTraceElement = e.getStackTrace()[0];

        StringBuilder errorMsg = new StringBuilder();

        //拼接异常信息
        StringBuilder append = errorMsg.append(stackTraceElement.getClassName()).append(".").append(stackTraceElement.getMethodName()).append("(").append(stackTraceElement.getFileName()).append(":").append(stackTraceElement.getLineNumber()).append(")");

        //异常行号
        int lineNumber = stackTraceElement.getLineNumber();

        sb.append("【异常】：").append(e.getClass().getSimpleName()).append(": ").append(e.getMessage()).append(lineNumber).append("，详情：").append(append);

        log.error(sb.toString());
    }


    /**
     * 方法前处理
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/27 17:53
     * @since V1.1
     */
    private void before(StringBuilder sb, MethodSignature signature, Object[] args) {

        //类名
        String className = signature.getDeclaringType().getName();

        //方法名
        String methodName = signature.getName();

        WiklMethodLog methodLog = signature.getMethod().getAnnotation(WiklMethodLog.class);

        sb.append("\n").append("\n").append("【Service】").append(">>>>>").append("\n").append("\n");

        sb.append("【功能】：").append(methodLog.description()).append("\n");

        sb.append("【类名】：").append(className).append("\n");

        sb.append("【方法】：").append(methodName).append("\n");

        //参数集合
        Map<String, Object> params = new HashMap<>();

        String[] parameterNames = signature.getParameterNames();

        for (int i = 0; i < parameterNames.length; i++) {
            String name = parameterNames[i];

            Object arg = args[i];

            if (arg instanceof String ) {
                params.put(name, arg.toString());
            }else{
                params.put(name, arg);
            }

        }
        sb.append("【参数】：").append(JsonUtils.parseObjToJson(params)).append("\n");
    }

    /**
     * 方法后处理
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/27 17:53
     * @since V1.1
     */
    private void after(StringBuilder builder, Object result) {

        builder.append("【结果】：").append(result + "").append("\n");
    }
}
