package com.example.sqlelasticsearch.controller

import com.example.sqlelasticsearch.dto.SqlElasticSearchDTO
import com.example.sqlelasticsearch.port.ISQLElasticSearchParser
import com.example.sqlelasticsearch.presentation.SQLElasticSearch
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.Connection

@RestController
@RequestMapping("sql")
class SqlElasticSearchController(
    val connection: Connection,
    val parserSqlElasticSearchService: ISQLElasticSearchParser,
    val sqlElasticSearch: SQLElasticSearch
) {

    @PostMapping
    fun query(@RequestBody body: SqlElasticSearchDTO): List<List<Any?>?>? {
        val query = parserSqlElasticSearchService.parser(body.query)
        return sqlElasticSearch.executeQuery<Any>(
            query
        )
    }

    @PostMapping("jdbc")
    fun queryJDBC(@RequestBody body: SqlElasticSearchDTO): List<Any?> {
        connection.createStatement().use { statement ->
            statement.executeQuery(body.query).use { results ->
                val esResult = mutableListOf<Any>()
                while (results.next()) {
                    esResult.add(results.row)
                }
                return esResult
            }
        }
    }
}
