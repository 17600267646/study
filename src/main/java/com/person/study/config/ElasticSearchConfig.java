package com.person.study.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author zhang
 * @version 1.0
 * @date 2022/1/19 9:26
 */
@Configuration
@Slf4j
public class ElasticSearchConfig {

    public   static  final  RequestOptions COMMON_OPTIONS;
    //hostName 代表ip
    @Value("${elasticsearch.cluster-nodes}")
    private String hostName;
    //因为咱们这个高级客户端rest 是基于http 咱们使用端口9200 ,而9300是tcp
    @Value("${elasticsearch.port}")
    private int port;
    static {
        // RequestOptions类保存了请求的部分，这些部分应该在同一个应用程序中的许多请求之间共享。
        // 创建一个singqleton实例，并在所有请求之间共享它。可以设置请求头之类的一些配置
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        // builder.addHeader("Authorization", "Bearer " + TOKEN); //增加需要的请求 头
        // builder.setHttpAsyncResponseConsumerFactory(
        //         new HttpAsyncResponseConsumerFactory
        //                 .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 *1024));
        COMMON_OPTIONS = builder.build();
    }
    //创建ES实例
    @Bean
    public RestHighLevelClient restHighLevelClient() throws IOException {
        log.info("es服务器url={},端口={}",hostName,port);
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(
                        hostName, port, "http"
                )));
        return client;
    }
}
