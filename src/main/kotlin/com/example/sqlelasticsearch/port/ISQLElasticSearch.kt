package com.example.sqlelasticsearch.port

interface ISQLElasticSearch {
    fun <T> executeQuery(query: String): List<List<T?>?>?
}
