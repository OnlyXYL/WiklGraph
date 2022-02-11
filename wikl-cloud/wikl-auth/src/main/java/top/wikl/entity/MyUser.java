package top.wikl.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/9/17 0017 19:39
 */
@Data
public class MyUser implements Serializable {

    private static final long serialVersionUID = 5619929529882573606L;

    private String userName;

    private String password;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked= true;

    private boolean credentialsNonExpired= true;

    private boolean enabled= true;
}
