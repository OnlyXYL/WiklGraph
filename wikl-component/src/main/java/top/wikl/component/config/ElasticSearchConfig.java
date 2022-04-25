package top.wikl.component.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.wikl.component.config.properties.ElasticSearchProperties;

import javax.annotation.Resource;

/**
 * @author XYL
 * @title: ElasticSearchConfig
 * @description: TODO
 * @date 2020/6/2 11:41
 * @return
 * @since V1.0
 */
@Slf4j
@Configuration
public class ElasticSearchConfig {

    @Resource
    private ElasticSearchProperties elasticSearchProperties;

    @Bean
    public RestHighLevelClient restHighLevelClient() throws Exception {

        /**
         * 如果ES设置了密码，那这里也提供了一个基本的认证机制，下面设置了ES需要基本身份验证的默认凭据提供程序
         */
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(elasticSearchProperties.getUsername(), elasticSearchProperties.getPassword()));

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        HttpHost.create(elasticSearchProperties.getUris())
//                        new HttpHost(elasticSearchProperties.getUris())
                )
                        //设置监听器，每次节点失败都可以监听到，可以作额外处理
                        .setFailureListener(
                                new RestClient.FailureListener() {
                                    @Override
                                    public void onFailure(Node node) {
                                        super.onFailure(node);
                                        log.warn("Failed on node called %s,", node.getName());
                                    }
                                }
                        )
                        //配置ES安全认证
                        .setHttpClientConfigCallback(
                                //默认采用异步抢先认证
                                httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                        )

                        /**
                         * 配置请求超时，将连接超时（默认为1秒）和套接字超时（默认为30秒）增加，
                         *     这里配置完应该相应地调整最大重试超时（默认为30秒），即上面的setMaxRetryTimeoutMillis，一般于最大的那个值一致即60000
                         */
                        .setRequestConfigCallback(
                                // 连接5秒超时，套接字连接60s超时
                                requestConfigBuilder -> requestConfigBuilder.setConnectTimeout(5000).setSocketTimeout(60000)
                        )
        );

        return client;
    }

}
