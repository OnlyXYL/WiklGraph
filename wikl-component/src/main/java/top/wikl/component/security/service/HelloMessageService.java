package top.wikl.component.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Objects;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/8/13 0013 15:26
 */
public class HelloMessageService implements MessageService {

    /**
     * 获取信息，进行授权拦截
     *
     * el expression
     *
     * hasAnyRole
     *
     * authenticated
     *
     * @param
     * @return java.lang.String
     * @author XYL
     * @since 18:08 2021/8/13 0013
     **/
    @PreAuthorize("authenticated")
    @Override
    public String getMessage() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if (Objects.isNull(authentication)) {
            return " 认证失败\n\n请认证后再访问！";
        }else{

            final Object principal = authentication.getPrincipal();

            if (principal instanceof String) {
                //匿名
                final String anonymous = (String) principal;

                final String format = String.format("匿名访问：%s", anonymous);

                return format;
            }

            if (principal instanceof User) {
                //认证
                final User user = (User) authentication.getPrincipal();

                final String format = String.format("认证通过\n\n用户名：%s，密码：%s，authorities：%s", user.getUsername(),user.getPassword(),user.getAuthorities());

                return format;
            }

            return null;
        }
    }
}