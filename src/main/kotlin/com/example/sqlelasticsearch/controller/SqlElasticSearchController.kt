package com.example.sqlelasticsearch.controller

import com.example.sqlelasticsearch.dto.SqlElasticSearchDTO
import com.example.sqlelasticsearch.dto.SqlElasticSearchResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.util.EntityUtils
import org.elasticsearch.client.Request
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.Response
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("sql")
class SqlElasticSearchController(
    val client: RestHighLevelClient,
    val mapper: ObjectMapper
) {

    @PostMapping
    fun query(@RequestBody body: SqlElasticSearchDTO): List<Any?>? {
        val request = Request("POST", "/_opendistro/_sql")
        request.setJsonEntity("{\"query\":\"${body.query}\"}")
        request.options = RequestOptions.DEFAULT.toBuilder().addHeader("kbn-xsrf", "reporting").build()
        val response: Response = client.lowLevelClient.performRequest(request)
        val jsonNode = mapper.readTree(EntityUtils.toString(response.entity))

        return mapper.readValue(jsonNode.toString(), SqlElasticSearchResponse::class.java).datarows
    }
}
