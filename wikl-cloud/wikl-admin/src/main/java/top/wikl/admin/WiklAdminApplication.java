package top.wikl.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/4/20 0020 16:34
 */
@EnableAdminServer
@SpringBootApplication
public class WiklAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(WiklAdminApplication.class, args);
    }
}
