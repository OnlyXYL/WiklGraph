package top.wikl.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 记录方法执行日志的切面处理类
 *
 * @author XYL
 * @Title: CostTimeAOP
 * @ProjectName SemanticCube
 * @date 2019/2/2714:24
 * @since V3.1
 */
@Aspect
@Component
@Slf4j
public class WiklMethodLogAOP {

    //统计请求的处理时间
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("@annotation(top.wikl.annotion.WiklMethodLog)")
    public void costTimePointCut() {
    }

    @Around("costTimePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        startTime.set(System.currentTimeMillis());

        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        //记录请求的内容
        System.out.println("Aspect_URL : " + request.getRequestURL().toString());

        System.out.println("Aspect_Method : " + request.getMethod());

        //执行方法
        Object result = point.proceed();

        //执行时长(毫秒)
        System.out.println("方法执行时间 : " + (System.currentTimeMillis() - startTime.get() + " ms"));

        return result;
    }

}
