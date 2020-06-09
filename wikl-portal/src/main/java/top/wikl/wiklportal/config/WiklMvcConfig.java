package top.wikl.wiklportal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.wikl.interceptor.WiklPrintLogInterceptor;

/**
 * SpringMVC 相关配置
 *
 * @param
 * @author XYL
 * @date 2019/10/30 17:44
 * @return
 * @since V1.0
 */
@Configuration
public class WiklMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(new WiklPrintLogInterceptor()).addPathPatterns("/**");
    }
}
