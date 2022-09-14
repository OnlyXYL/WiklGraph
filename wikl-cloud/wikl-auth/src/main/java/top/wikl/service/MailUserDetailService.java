package top.wikl.service;

import cn.com.bmsmart.entity.AuthUser;
import cn.com.bmsmart.exception.ValidateCodeException;
import cn.com.bmsmart.manager.UserManager;
import cn.com.bmsmart.utils.PageData;
import cn.com.bmsmart.utils.Verify;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author XYL
 * @version 1.2
 * @since 2021/9/18 0018 15:14
 */
@Service
public class MailUserDetailService implements UserDetailsService {

    @Resource
    private ValidateMailService validateMailService;

    @Autowired
    private UserManager userManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PageData pd = new PageData();
        if (username.contains(":")) {
            pd.put("login_name", username.split(":")[0]);
        } else {
            pd.put("login_name", username);
        }
        PageData uPd = userManager.findUserInfo(pd);
        if (Verify.verifyIsNotNull(uPd)) {
            if (uPd.getString("status").equals("2")) {
                throw new UsernameNotFoundException("账号已休眠，请联系管理员");
            } else {
                if (uPd.getString("status").equals("0")) {
                    //查询当前用户组织
                    pd.put("user_id", uPd.get("id"));
                    PageData orgPd = userManager.findUserOrganizeInfo(pd);
                    //登录成功
                    pd.put("user_id", uPd.get("id"));
                    String permissions = userManager.findUserPermissions(pd);
                    boolean notLocked = false;
                    if (StringUtils.equals("0", uPd.get("status").toString())) {
                        notLocked = true;
                    }
                    AuthUser authUser = new AuthUser(uPd.get("login_name").toString() + ":" + uPd.get("id").toString() + ":" + orgPd.get("organize_id").toString(), uPd.get("login_password").toString(), true, true, true, notLocked,
                            AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));
                    return transToAuthUser(authUser, uPd);

                } else if (uPd.getString("status").equals("N")) {
                    throw new UsernameNotFoundException("账号已禁用，请联系管理员");
                } else if (uPd.getString("status").equals("2")) {
                    throw new UsernameNotFoundException("账号已休眠，请联系管理员");
                }
            }
            return null;
        } else {
            throw new UsernameNotFoundException("登录名称不存在，请重新输入。");
        }
    }

    /**
     * @param
     * @return void
     * @author XYL
     * @since 15:21 2021/9/18 0018
     **/
    public void checkMailCode(String username, String code) throws ValidateCodeException {
        validateMailService.check(username, code);
    }

    /**
     * @throws
     * @title transToAuthUser
     * @description 用户信息转换
     * @author Administrator
     * @updateTime 2021/4/3 0003 19:13
     */
    private AuthUser transToAuthUser(AuthUser authUser, PageData uPd) {
        authUser.setId(uPd.getString("id"));
        authUser.setType(uPd.getString("type"));
        authUser.setLogin_name(uPd.getString("login_name"));
        authUser.setLogin_password(uPd.getString("login_password"));
        authUser.setName(uPd.getString("name"));
        authUser.setSex(uPd.getString("sex"));
        authUser.setPhone(uPd.getString("phone"));
        authUser.setEmail(uPd.getString("email"));
        authUser.setBirth_date(uPd.getString("birth_date"));
        authUser.setLive_address(uPd.getString("live_address"));
        authUser.setBirth_address(uPd.getString("birth_address"));
        authUser.setHead_address(uPd.getString("head_address"));
        authUser.setMotto(uPd.getString("motto"));
        authUser.setStatus(uPd.getString("status"));
        authUser.setOrder_by(uPd.getString("order_by"));
        authUser.setLast_login_time(uPd.getString("last_login_time"));
        authUser.setBrowser(uPd.getString("browser"));
        authUser.setOs(uPd.getString("os"));
        authUser.setIpaddr(uPd.getString("ipaddr"));
        authUser.setIprealaddr(uPd.getString("iprealaddr"));
        authUser.setStatus(uPd.getString("status"));
        return authUser;
    }
}
