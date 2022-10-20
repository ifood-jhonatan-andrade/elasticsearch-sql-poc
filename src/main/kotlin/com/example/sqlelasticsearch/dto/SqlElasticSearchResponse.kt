package com.example.sqlelasticsearch.dto

data class SqlElasticSearchResponse(
    val schema: List<Data?>?,
    val datarows: List<List<Any?>?>?
) {
    data class Data(
        val name: String,
        val type: String
    )
}
