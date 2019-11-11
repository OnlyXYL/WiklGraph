package top.wikl.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.wikl.utils.holder.WiklLogHolder;

import javax.servlet.http.HttpServletRequest;

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

        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        //记录请求的内容
        System.out.println("Aspect_URL : " + request.getRequestURL().toString());

        System.out.println("Aspect_Method : " + request.getMethod());

        //执行方法
        Object result = point.proceed();

        //执行时长(毫秒)
        System.out.println("方法执行时间 : " + (System.currentTimeMillis() - WiklLogHolder.get() + " ms"));

        return result;
    }

}
