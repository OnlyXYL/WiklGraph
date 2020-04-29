package top.wikl.component.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 需要特殊处理的附件图谱
 *
 * @author XYL
 * @title: SpecialAttachmentConfig
 * @description: TODO
 * @date 2020/4/22 11:33
 * @return
 * @since V1.0
 */
@Conditional(SmbCondition.class)
@ConfigurationProperties(prefix = "graph.special", ignoreUnknownFields = false)
@PropertySource(value = {"classpath:application.yml"}, encoding = "utf-8")
@Slf4j
@Data
@Component
public class SmbConfig {

    /**
     * 是否开启特殊处理
     */
    private String open;

    /**
     * 共享服务器地址
     */
    private String hostIP;

    /**
     * 共享服务器用户名
     */
    private String userName;

    /**
     * 共享服务器密码
     */
    private String passWord;

    /**
     * 共享服务器上文件路径
     */
    private String filePath;

    /**
     * 特殊处理的图谱id
     */
    private Map<Integer,Map<String,String>> graphIds;
}
