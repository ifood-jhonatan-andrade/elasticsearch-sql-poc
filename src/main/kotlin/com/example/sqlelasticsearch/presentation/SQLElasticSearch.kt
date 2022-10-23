package com.example.sqlelasticsearch.presentation

import com.example.sqlelasticsearch.dto.SqlElasticSearchResponse
import com.example.sqlelasticsearch.port.ISQLElasticSearch
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.util.EntityUtils
import org.elasticsearch.client.Request
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.Response
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.stereotype.Service

@Service
class SQLElasticSearch(
    val client: RestHighLevelClient,
    val mapper: ObjectMapper
) : ISQLElasticSearch {
    override fun <T> executeQuery(query: String): List<List<T?>?>? {
        val request = Request("POST", "/_opendistro/_sql")
        val payload = "{\"query\":\"${query}\"}"

        request.setJsonEntity(payload)
        request.options = RequestOptions.DEFAULT
            .toBuilder()
            .addHeader("kbn-xsrf", "reporting")
            .build()

        val response: Response = client.lowLevelClient.performRequest(request)

        val jsonNode = mapper.readTree(EntityUtils.toString(response.entity))

        return (
            mapper.readValue(jsonNode.toString(), SqlElasticSearchResponse::class.java) as SqlElasticSearchResponse<T>
            ).datarows
    }
}
