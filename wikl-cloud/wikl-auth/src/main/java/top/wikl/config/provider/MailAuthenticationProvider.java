package top.wikl.config.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import top.wikl.config.token.MailAuthenticationToken;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/9/18 0018 15:13
 */
public class MailAuthenticationProvider implements AuthenticationProvider {

    /**
     * 新增
     */
    private MailUserDetailService userDetailsService;

    public MailAuthenticationProvider(MailUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        MailAuthenticationToken authenticationToken = (MailAuthenticationToken) authentication;
        String username = (String) authenticationToken.getPrincipal();
        String code = (String) authenticationToken.getCredentials();
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if (user == null) {
            throw new InternalAuthenticationServiceException("邮箱不存在");
        }

        try {
            userDetailsService.checkMailCode(username,code);
        } catch (ValidateCodeException e) {
            e.printStackTrace();
            throw new BadCredentialsException(e.getMessage());
        }
        MailAuthenticationToken authenticationResult = new MailAuthenticationToken(user, code, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MailAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
