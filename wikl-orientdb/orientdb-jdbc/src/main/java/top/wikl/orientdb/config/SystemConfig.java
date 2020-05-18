package top.wikl.orientdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.wikl.lru.LRULinkedHashMap;

/**
 * 系统配置类
 *
 * @author XYL
 * @Title: SystemConfig
 * @ProjectName SemanticCube
 * @Description: TODO
 * @date 2019/5/2217:33
 */
@Configuration
public class SystemConfig {

    @Bean
    public LRULinkedHashMap lruLinkedHashMap() {

        LRULinkedHashMap lruLinkedHashMap = new LRULinkedHashMap(300);

        return lruLinkedHashMap;
    }

}
