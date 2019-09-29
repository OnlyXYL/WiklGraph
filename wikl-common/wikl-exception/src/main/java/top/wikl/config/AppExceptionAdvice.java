package top.wikl.config;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.wikl.entity.AppExceptionResult;
import top.wikl.exception.BaseAppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 通用 Api Controller 全局异常处理
 *
 * @param
 * @author XYL
 * @date 2019/9/27 11:24
 * @return
 * @since V1.0
 */
@Slf4j
@RestControllerAdvice
public class AppExceptionAdvice {

    /**
     * 自定义 REST 业务异常
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 11:29
     * @since V1.0
     */
    @ExceptionHandler(value = BaseAppException.class)
    public ResponseEntity<AppExceptionResult> handleCustomBadRequest(
            BaseAppException ex,
            Object handler,
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        ex.printStackTrace();

        log.error("==============Exception info:" + ex);


        AppExceptionResult appExResult = new AppExceptionResult();
        HttpStatus httpStatus = null;

        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (Objects.nonNull(responseStatus)) {
            httpStatus = responseStatus.value();

        }

        appExResult.setClazz(ex.getClass().getName());
        appExResult.setMessage(ex.getMessage());
        appExResult.setReason(httpStatus.getReasonPhrase());

        if (StringUtils.isEmpty(ex.getPath())) {
            appExResult.setPath(request.getServletPath());
        } else {
            appExResult.setPath(ex.getPath());

        }

        appExResult.setConvertedMessage(ex.getMessage());
        appExResult.setTimestamp(System.currentTimeMillis() + "");

        return ResponseEntity.status(httpStatus.value()).body(appExResult);

    }

    /**
     * 捕获系统级别异常
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 11:29
     * @since V1.0
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<AppExceptionResult> handleSystemBadRequest(
            Exception ex,
            Object handler,
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        ex.printStackTrace();

        log.error("==============Exception info:" + ex);

        AppExceptionResult appExResult = new AppExceptionResult();

        HttpStatus httpStatus = null;
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (Objects.nonNull(responseStatus)) {
            httpStatus = responseStatus.value();
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        appExResult.setClazz(ex.getClass().getName());
        appExResult.setMessage(ex.getMessage());
        appExResult.setReason(httpStatus.getReasonPhrase());
        appExResult.setPath(request.getServletPath());

        appExResult.setConvertedMessage(ex.getMessage());
        appExResult.setTimestamp(System.currentTimeMillis() + "");

        return ResponseEntity.status(httpStatus.value()).body(appExResult);

    }

    /**
     * 捕获熔断异常
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 11:30
     * @since V1.0
     */
    @ExceptionHandler(value = HystrixBadRequestException.class)
    public ResponseEntity<AppExceptionResult> handleHystrixBadRequest(
            BaseAppException ex,
            Object handler,
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        ex.printStackTrace();

        log.error("==============Exception info:" + ex);
        AppExceptionResult appExResult = new AppExceptionResult();
        HttpStatus httpStatus = null;

        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (Objects.nonNull(responseStatus)) {
            httpStatus = responseStatus.value();

        }

        log.debug("status:" + response.getStatus());

        appExResult.setClazz(ex.getClass().getName());
        appExResult.setMessage(ex.getMessage());
        appExResult.setReason(httpStatus.getReasonPhrase());
        appExResult.setPath(request.getServletPath());
        appExResult.setConvertedMessage(ex.getMessage());
        appExResult.setTimestamp(System.currentTimeMillis() + "");

        return ResponseEntity.status(httpStatus.value()).body(appExResult);

    }
}
