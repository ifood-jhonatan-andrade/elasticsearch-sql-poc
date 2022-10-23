package com.example.sqlelasticsearch.presentation

import com.example.sqlelasticsearch.domain.SQLElasticSearchField
import com.example.sqlelasticsearch.port.ISQLElasticSearchParser
import org.springframework.stereotype.Service

@Service
class SqlElasticSearchServiceParser : ISQLElasticSearchParser {
    private lateinit var fieldTranslator: Map<String, SQLElasticSearchField>

    init {
        fieldTranslator = initFieldTranslation()
    }

    override fun parser(query: String): String {
        val sanitized = sanitation(query)
        val translated = translation(sanitized)

        return "SELECT doc FROM catalog_item_metadata WHERE $translated LIMIT 100;"
    }

    private fun sanitation(query: String): String = query
        .replace("(?i)(select).*(?i)(where)".toRegex(), "")
        .replace("(?i)(limit).*".toRegex(), "")
        .trim()

    private fun translation(query: String): String = query.replace(
        "(${fieldTranslator.keys.joinToString(separator = "|") { "(?<!\\.)$it" } })"
            .toRegex()
    ) {
        fieldTranslator[it.value.trim()]!!.translation
    }

    private fun initFieldTranslation(): Map<String, SQLElasticSearchField> {
        return mapOf(
            SQLElasticSearchField.metadata("lacFree"),
            SQLElasticSearchField.metadata("hasComplements"),
            SQLElasticSearchField.metadata("natural"),
            SQLElasticSearchField.metadata("alcoholic"),
            SQLElasticSearchField.metadata("serves"),
            SQLElasticSearchField.metadata("discount"),
            SQLElasticSearchField.metadata("glutenFree"),
            SQLElasticSearchField.metadata("vegan"),
            SQLElasticSearchField.metadata("hasMandatoryComplements"),
            SQLElasticSearchField.metadata("hasPhoto"),
            SQLElasticSearchField.metadata("sugarFree"),
            SQLElasticSearchField.metadata("vegetarian"),
            SQLElasticSearchField.metadata("diet"),
            SQLElasticSearchField.metadata("hasOnlyZeroPriceOptions"),
            SQLElasticSearchField.metadata("frosty"),
            SQLElasticSearchField.metadata("organic"),
            SQLElasticSearchField.metadata("isPortion"),
            SQLElasticSearchField.metadata("flavourClassification"),
            SQLElasticSearchField.metadata("taxonomyLevel1"),
            SQLElasticSearchField.metadata("taxonomyLevel2"),
            SQLElasticSearchField.metadata("brandName"),
            SQLElasticSearchField.metadata("ean"),
            SQLElasticSearchField.metadata("brandOwner"),
            SQLElasticSearchField.metadata("quantityUnit"),
            SQLElasticSearchField.metadata("groupId"),
            SQLElasticSearchField.root("ownerId"),
            SQLElasticSearchField.root("catalogId"),
            SQLElasticSearchField.root("itemStatus"),
            SQLElasticSearchField.root("categoryId"),
            SQLElasticSearchField.root("categoryName"),
            SQLElasticSearchField.root("itemId"),
            SQLElasticSearchField.root("itemProductId"),
            SQLElasticSearchField.root("itemName"),
            SQLElasticSearchField.root("itemLogosUrls"),
            SQLElasticSearchField.root("itemType"),
            SQLElasticSearchField.root("itemPrice"),
            SQLElasticSearchField.root("itemMinSalePrice"),
            SQLElasticSearchField.root("itemExternalCode"),
            SQLElasticSearchField.root("itemGeneralTags"),
            SQLElasticSearchField.root("itemProductTags"),
            SQLElasticSearchField.root("itemIsSellable"),
            SQLElasticSearchField.root("itemModifiedAt"),
            SQLElasticSearchField.root("ingestedAt"),
            SQLElasticSearchField.root("itemAisles"),
            SQLElasticSearchField.root("itemSalesRestrictions"),
            SQLElasticSearchField.root("segmentation")
        )
    }
}
