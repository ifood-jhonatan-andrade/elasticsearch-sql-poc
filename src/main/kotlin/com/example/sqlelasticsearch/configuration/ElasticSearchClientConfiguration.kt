package com.example.sqlelasticsearch.configuration

import org.apache.http.HttpHost
import org.apache.http.client.config.RequestConfig
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ElasticSearchClientConfiguration {

    @Bean
    fun elasticSearchClient(
        @Value("\${es.url}") esUrl: String,
        @Value("\${es.port}") port: Int,
        @Value("\${es.useSSL:true}") useSSL: Boolean,
        @Value("\${es.socket-timeout}") socketTimeout: Int,
        @Value("\${es.connection-timeout}") connectionTimeout: Int
    ): RestHighLevelClient {
        return RestHighLevelClient(
            RestClient.builder(
                HttpHost(esUrl, port, if (useSSL) "https" else "http")
            )
                .setRequestConfigCallback { requestConfigBuilder: RequestConfig.Builder? ->
                    requestConfigBuilder?.setConnectTimeout(connectionTimeout)
                    requestConfigBuilder?.setSocketTimeout(socketTimeout)
                }
        )
    }
}
