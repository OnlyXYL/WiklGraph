package top.wikl.cloud.eureka.config;

import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 解决开启安全认证之后，报错问题
 *
 * @author XYL
 * @title: WebSecurityConfigurer
 * @description: TODO
 * @date 2020/5/5 16:57
 * @return
 * @since V1.0
 */
@Profile("dev")
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    /**
     * You can secure your Eureka server simply by adding Spring Security to your server’s classpath via spring-boot-starter-security. By default when Spring Security is on the classpath it will require that a valid CSRF token be sent with every request to the app. Eureka clients will not generally possess a valid cross site request forgery (CSRF) token you will need to disable this requirement for the /eureka/** endpoints.
     *
     * 官方推荐：
     *            http.csrf().ignoringAntMatchers("/eureka/**");
     *            super.configure(http);
     *
     * 还可以：
     *
     *            http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
     *
     * example:
     *
     *           http://${user}:${password}@${host}:${port}/eureka/
     *
     * @param
     * @return
     * @throws
     * @author XYL
     * @date 2020/5/5 17:15
     * @since V1.1
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().ignoringAntMatchers("/eureka/**");
        super.configure(http);
    }

}
