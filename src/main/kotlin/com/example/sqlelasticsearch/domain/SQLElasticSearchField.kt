package com.example.sqlelasticsearch.domain

data class SQLElasticSearchField(
    val name: String,
    val translation: String
) {
    companion object {
        fun root(name: String): Pair<String, SQLElasticSearchField> = Pair(name, SQLElasticSearchField(name, "doc.$name"))
        fun metadata(name: String): Pair<String, SQLElasticSearchField> = Pair(name, SQLElasticSearchField(name, "doc.itemMetadata.$name"))
    }
}
