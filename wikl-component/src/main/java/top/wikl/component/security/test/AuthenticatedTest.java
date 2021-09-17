package top.wikl.component.security.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.wikl.component.security.service.HelloMessageService;

/**
 * 认证
 *
 * @author XYL
 * @version 1.2
 * @WithMockUser 默认用户名，密码为 user,password
 * @since 2021/8/13 0013 17:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AuthenticatedTest {

    /**
     * 认证
     *
     * @param
     * @return void
     * @author XYL
     * @since 18:06 2021/8/13 0013
     **/
//    @WithMockUser
//    @WithMockUser(username = "xyl",roles = "{'USER','ADMIN'}")
    @Test
    @WithMockUser(username = "xyl",authorities = "{'USER','ADMIN'}")
    public void authenticated() {

        final HelloMessageService messageService = new HelloMessageService();

        final String message = messageService.getMessage();

        System.out.println("");
        System.out.println("");
        System.out.println(message);
        System.out.println("");
        System.out.println("");
    }

    /**
     * 没有认证信息，不能正常获取信息
     *
     * @param
     * @return void
     * @author XYL
     * @since 18:06 2021/8/13 0013
     **/
    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void unAuthenticated() {

        final HelloMessageService messageService = new HelloMessageService();

        final String message = messageService.getMessage();

        System.out.println("");
        System.out.println("");
        System.out.println(message);
        System.out.println("");
        System.out.println("");
    }

    /**
     * 匿名用户，能正常获取信息
     *
     * @param
     * @return void
     * @author XYL
     * @since 18:25 2021/8/13 0013
     **/
    @Test
    @WithAnonymousUser
    public void anonymous() {

        final HelloMessageService messageService = new HelloMessageService();

        final String message = messageService.getMessage();

        System.out.println("");
        System.out.println("");
        System.out.println(message);
        System.out.println("");
        System.out.println("");
    }

    @Test
    @WithUserDetails
    public void userDetails(){
        final HelloMessageService messageService = new HelloMessageService();

        final String message = messageService.getMessage();

        System.out.println("");
        System.out.println("");
        System.out.println(message);
        System.out.println("");
        System.out.println("");
    }
}
