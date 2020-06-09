package top.wikl.config;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import top.wikl.entity.AppExceptionResult;
import top.wikl.exception.BaseAppException;

import java.util.List;
import java.util.Objects;

/**
 * 对feign中捕获到的服务中的异常进行封装成 HystrixBadRequestException 使之免除熔断
 *
 * @param
 * @author XYL
 * @date 2019/9/27 11:30
 * @return
 * @since V1.0
 */
@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Autowired
    FeignHystrixConfig feignHystrixConfig;

    @Autowired
    List<BaseAppException> customExceptionList;

    @Override
    public Exception decode(String methodKey, Response response) {
        log.info("+++++++++++++ The ErrorDecoder of feign +++++++++++");
        try {
            if (response.body() != null) {
                String msg = Util.toString(response.body().asReader());

                log.error("==============Exception info:" + msg);

                AppExceptionResult exceptionInfo = bodyToExceptionInfo(msg);
                String message = exceptionInfo.getMessage();

                Exception customException = null;


                /**
                 * 判断自定义的异常类型
                 */
                Class clazz = Class.forName(exceptionInfo.getClazz());
                customException = (Exception) clazz.getDeclaredConstructor(String.class).newInstance(exceptionInfo.getConvertedMessage());

                if (Objects.isNull(customExceptionList) || customExceptionList.size() <= 0) {
                    return customException;
                }

                BaseAppException baseAppException = null;
                for (BaseAppException exceptionItem : customExceptionList) {
                    if (exceptionItem.equalsClassName(clazz.getName())) {
                        baseAppException = exceptionItem.execute(exceptionInfo.getMessage(), exceptionInfo);
                    }
                }

                /**
                 * 判断是否开启hystrix
                 */

                if (feignHystrixConfig.isEnabled() == true) {
                    //这里走 熔断异常 的策略
                    Exception hystrixException = null;

                    // 这里只封装4开头的请求异常
                    if (500 >= response.status()) {
                        //状态500+的异常策略

                        hystrixException = new HystrixBadRequestException(message, Objects.nonNull(baseAppException) ? baseAppException : customException);
                    } else if (400 <= response.status() && response.status() < 500) {
                        //状态400+的异常策略

                        hystrixException = new HystrixBadRequestException(message, Objects.nonNull(baseAppException) ? baseAppException : customException);
                    } else if (300 <= response.status() && response.status() < 400) {
                        //状态300+的异常策略

                        hystrixException = new HystrixBadRequestException(message, Objects.nonNull(baseAppException) ? baseAppException : customException);
                    } else if (100 <= response.status() && response.status() < 200) {
                        //状态100+的异常策略


                        hystrixException = new HystrixBadRequestException(message, Objects.nonNull(baseAppException) ? baseAppException : customException);
                    } else {
                        log.error(hystrixException.getMessage(), hystrixException);

                        hystrixException = new HystrixBadRequestException(message, Objects.nonNull(baseAppException) ? baseAppException : customException);

                    }

                    return hystrixException;

                } else {
                    //这里返回 普通异常 的策略
                    if (Objects.isNull(baseAppException)) {
                        return customException;
                    } else {

                        return baseAppException;
                    }
                }
            }
        } catch (Exception e) {
            log.info(e.toString());
        }

        return FeignException.errorStatus(methodKey, response);
    }

    /**
     * 封装异常信息
     *
     * @param
     * @return
     * @author XYL
     * @date 2019/9/27 11:31
     * @since V1.0
     */
    public AppExceptionResult bodyToExceptionInfo(String body) {
        try {
            log.debug("==========body:" + body);

            AppExceptionResult exceptionInfo = JSON.parseObject(body, AppExceptionResult.class);

            log.debug("==========exceptionInfo:" + exceptionInfo.toString());

            return exceptionInfo;

        } catch (Exception e) {
            log.error(e.toString());
            return new AppExceptionResult(body);
        }

    }

}
