package com.example.sqlelasticsearch.port

interface ISQLElasticSearchParser {
    fun parser(query: String): String
}
