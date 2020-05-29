package top.wikl.interceptor;

import com.google.common.base.Throwables;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import top.wikl.utils.date.WiklDateUtil;
import top.wikl.utils.json.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

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

    private static final String SESSION_KEY = "sessionId";

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

        // 入口传入请求ID
        String sessionId = request.getSession().getId();

        MDC.put(SESSION_KEY, sessionId);

        //restful方式获取请求参数
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        if (handler instanceof HandlerMethod) {
            StringBuilder sb = new StringBuilder(1000);

            String requestURI = request.getRequestURI();
            requestURI = java.net.URLDecoder.decode(new String(requestURI.getBytes("ISO-8859-1"), "UTF-8"), "UTF-8");

            sb.append("\n").append("-------------------------------------").append(WiklDateUtil.getTime())
                    .append("-------------------------------------").append("\n").append("\n");

            sb.append("【Controller】").append(">>>>>").append("\n").append("\n");

            HandlerMethod h = (HandlerMethod) handler;

            ApiOperation apiOperation = h.getMethodAnnotation(ApiOperation.class);


            //获取ip地址
            String ip = getIpAddress();

            sb.append("【 I  P 】：").append(ip).append("\n");

            String value = apiOperation.value();
            sb.append("【功能】：").append(value).append("\n");
            sb.append("【接口】：").append(requestURI).append("\n");
            sb.append("【标识】：").append(sessionId).append("\n");
            sb.append("【类名】：").append(h.getBean().getClass().getName()).append("\n");
            sb.append("【方法】：").append(h.getMethod().getName()).append("\n");
            sb.append("【参数】：").append(JsonUtils.parseObjToJson(pathVariables)).append("\n");

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
            sb.append("【耗时】：").append(executeTime).append(" ms").append("\n");
            sb.append("-------------------------------------------------------------------------------").append("\n");
            sb.append("TO BE CONTINUE").append("\n");
            log.info(sb.toString());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        // 出口移除请求ID
        MDC.remove(SESSION_KEY);
    }

    /**
     * 获取请求IP
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/28 10:41
     * @since V1.1
     */
    private static String getIpAddress() {

        String hostAddress = null;

        try {
            InetAddress addr = InetAddress.getLocalHost();

            hostAddress = addr.getHostAddress();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return hostAddress;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        log.error("获取ip异常：{}", Throwables.getStackTraceAsString(e));
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }

}