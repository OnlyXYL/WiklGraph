package top.wikl.component;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author XYL
 * @title: ComponentApplication
 * @description: TODO
 * @date 2019/12/17 14:24
 * @return
 * @since V1.0
 */
@SpringBootApplication(scanBasePackages = {"top.wikl.*","top.wikl.component.*"})
public class ComponentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComponentApplication.class, args);
    }
}
