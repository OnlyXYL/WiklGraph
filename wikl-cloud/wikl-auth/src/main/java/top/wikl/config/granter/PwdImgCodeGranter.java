package top.wikl.config.granter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import top.wikl.service.ValidateCodeService;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/9/17 0017 16:24
 */
public class PwdImgCodeGranter extends ResourceOwnerPasswordTokenGranter {

    private static final String GRANT_TYPE = "password_code";

    private final ValidateCodeService validateCodeService;

    public PwdImgCodeGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices
            , ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, ValidateCodeService validateCodeService) {
        super(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.validateCodeService = validateCodeService;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest){
        try {
            Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
            String key = parameters.get("key");
            String code = parameters.get("code");
            //校验图形验证码
            validateCodeService.check(key, code);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.getOAuth2Authentication(client, tokenRequest);
    }
}
