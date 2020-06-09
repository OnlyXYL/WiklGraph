package top.wikl.component.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.wikl.interceptor.WiklPrintLogInterceptor;

/**
 * @author XYL
 * @title: InterceptorConfig
 * @description: TODO
 * @date 2020/5/27 15:20
 * @return
 * @since V1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //注册拦截器
        InterceptorRegistration registration = registry.addInterceptor(new WiklPrintLogInterceptor());

        //拦截请求
        registration.addPathPatterns("/**");

    }
}

