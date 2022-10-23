package com.example.sqlelasticsearch.dto

data class SqlElasticSearchResponse<T>(
    val schema: List<Data?>?,
    val datarows: List<List<T?>?>?
) {
    data class Data(
        val name: String,
        val type: String
    )
}
