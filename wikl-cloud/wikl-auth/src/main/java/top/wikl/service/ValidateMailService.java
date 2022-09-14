package top.wikl.service;

import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import top.wikl.entity.MyConstant;
import top.wikl.properties.AuthProperties;
import top.wikl.properties.MailProperties;
import top.wikl.properties.ValidateCodeProperties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 校验邮件验证码
 *
 * @author XYL
 * @version 1.2
 * @since 2021/9/18 0018 14:42
 */
@Slf4j
@Service
public class ValidateMailService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private AuthProperties properties;
    @Autowired
    private MailProperties mailProperties;

    @Resource
    private UserMapper userMapper;

    /**
     * 发送登录凭证到邮箱
     *
     * @param request
     * @param response
     * @return void
     * @author XYL
     * @since 15:08 2021/9/18 0018
     **/
    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ValidateCodeException {
        String username = request.getParameter("username");

        System.out.println(username);

        if (StringUtils.isBlank(username)) {
            throw new ValidateCodeException("用户名不能为空");
        }
        ValidateCodeProperties code = properties.getCode();
        setHeader(response, code.getType());

        Captcha captcha = createCaptcha(code);

        String text = StringUtils.lowerCase(captcha.text());

        redisService.set(MyConstant.CODE_PREFIX + username, text, code.getTime());

        //根据用户，查询用户邮箱
        final String email = email(username);

        //发送邮件
        sendMail(email, text);

        final Object o = redisService.get(MyConstant.CODE_PREFIX + username);

        System.out.println(o.toString());

        captcha.out(response.getOutputStream());
    }

    /**
     * 获取用户邮箱
     *
     * @param username
     * @return java.lang.String
     * @author XYL
     * @since 16:38 2021/9/18 0018
     **/
    protected String email(String username) {

        PageData pd = new PageData();
        if (username.contains(":")) {
            pd.put("login_name", username.split(":")[0]);
        } else {
            pd.put("login_name", username);
        }
        PageData uPd = userMapper.findUserInfo(pd);

        return uPd.get("email").toString();
    }

    /**
     * 校验验证码
     *
     * @param username
     * @param code
     * @return void
     * @author XYL
     * @since 15:23 2021/9/18 0018
     **/
    public void check(String username, String code) throws ValidateCodeException {

        Object codeInRedis = redisService.get(MyConstant.CODE_PREFIX + username);
        if (StringUtils.isBlank(code)) {
            throw new ValidateCodeException("请输入验证码");
        }
        if (codeInRedis == null) {
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equalsIgnoreCase(code, String.valueOf(codeInRedis))) {
            throw new ValidateCodeException("验证码不正确");
        }
    }

    /**
     * @title createCaptcha
     * @description 生成验证码
     * @author Administrator
     * @updateTime 2021/4/3 0003 19:14
     */
    private Captcha createCaptcha(ValidateCodeProperties code) {
        Captcha captcha = null;
        if (StringUtils.equalsIgnoreCase(code.getType(), MyConstant.GIF)) {
            captcha = new GifCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        } else {
            captcha = new SpecCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        }
        captcha.setCharType(code.getCharType());
        return captcha;
    }

    private void setHeader(HttpServletResponse response, String type) {
        if (StringUtils.equalsIgnoreCase(type, MyConstant.GIF)) {
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        } else {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }

    /**
     * 发送邮件
     *
     * @param to   收件箱
     * @param code 验证码
     * @return void
     * @author XYL
     * @since 14:54 2021/9/18 0018
     **/
    public void sendMail(String to, String code) {

        SimpleMailMessage msg = new SimpleMailMessage();

        StringBuffer contentBuffer = new StringBuffer();

        contentBuffer.append("收到 SMART.KG 的登录凭证，请及时处理！凭证如下：\n");
        contentBuffer.append("\n");
        contentBuffer.append("\n");
        contentBuffer.append("\n");
        contentBuffer.append(code);
        contentBuffer.append("\n");

        msg.setText(contentBuffer.toString());
        msg.setTo(to);
        msg.setFrom(mailProperties.getFrom());
        msg.setSubject("登录凭证");

        try {

            JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
            javaMailSender.setHost(mailProperties.getHost());
            javaMailSender.setUsername(mailProperties.getFrom());
            javaMailSender.setPassword(mailProperties.getPassword());
            javaMailSender.setDefaultEncoding(mailProperties.getEcoding());

            javaMailSender.send(msg);
            log.info("++++++++++用户注册：邮件发送成功++++++++++");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("++++++++++用户注册：邮件发送失败++++++++++");
        }
    }
}
