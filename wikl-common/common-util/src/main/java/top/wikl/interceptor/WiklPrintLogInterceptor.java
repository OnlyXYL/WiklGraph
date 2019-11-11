package top.wikl.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.wikl.utils.date.WiklDateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 记录信息:</br> 访问时间</br>Controller路径</br>对应方法名</br>请求参数信息</br>请求相对路径</br>请求处理时长
 *
 * @param
 * @author XYL
 * @date 2019/9/27 12:58
 * @return
 * @since V1.0
 */
@Slf4j
public class WiklPrintLogInterceptor implements HandlerInterceptor {

    /**
     * before the actual handler will be executed
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        if (handler instanceof HandlerMethod) {
            StringBuilder sb = new StringBuilder(1000);

            String requestURI = request.getRequestURI();
            requestURI = java.net.URLDecoder.decode(new String(requestURI.getBytes("ISO-8859-1"), "UTF-8"), "UTF-8");

            sb.append("-----------------------").append(WiklDateUtil.getTime())
                    .append("-------------------------------------\n");
            HandlerMethod h = (HandlerMethod) handler;
            sb.append("Controller : ").append(h.getBean().getClass().getName()).append("\n");
            sb.append("Method     : ").append(h.getMethod().getName()).append("\n");
            sb.append("Params      : ").append(getParamString(request.getParameterMap())).append("\n");
            sb.append("URI           : ").append(requestURI).append("\n");

            log.info(sb.toString());
        }
        return true;
    }

    /**
     * after the handler is executed
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 12:58
     * @since V1.0
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;
        if (handler instanceof HandlerMethod) {
            StringBuilder sb = new StringBuilder(1000);
            sb.append("\n");
            sb.append("Controller执行时间  : ").append(executeTime).append(" ms").append("\n");
            sb.append("-------------------------------------------------------------------------------");
            log.info(sb.toString());
        }
    }

    /**
     * 处理参数
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 13:00
     * @since V1.0
     */
    private String getParamString(Map<String, String[]> map) {
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String[]> e : map.entrySet()) {
            sb.append(e.getKey()).append("=");
            String[] value = e.getValue();
            if (value != null && value.length == 1) {
                sb.append(value[0]).append("\t");
            } else {
                sb.append(Arrays.toString(value)).append("\t");
            }
        }
        return sb.toString();
    }

}