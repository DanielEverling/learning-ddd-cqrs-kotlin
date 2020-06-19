package com.learning.ddd.api

import com.learning.ddd.application.query.ProductProjection
import com.learning.ddd.commons.*
import com.learning.ddd.commons.Gateway.post
import com.learning.ddd.commons.Gateway.get
import org.amshove.kluent.`should be equal to`
import org.junit.After
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.Customization
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher
import org.skyscreamer.jsonassert.comparator.CustomComparator

internal class ProductResourceIT {

    companion object {
        @BeforeAll
        @JvmStatic
        fun `init`() {
            waitForResource(HEALTH)
        }
    }

    @AfterEach
    fun down() {
        ClearDataTable.clearAllTables()
    }

    @Test
    fun `should create a product`() {
        val categoryId = DomainHelpers.createCategory()
        val mapToReplace = mapOf("CATEGORY_ID" to categoryId)
        val productResource = "/product"
        val createProductRequest = ReadFile.readAndReplace(productResource, "product_post_request_ok", mapToReplace)
        val expectedCreateProductResponse = ReadFile.read(productResource, "product_post_response_ok")

        val postResponse = post(productResource, createProductRequest)
        postResponse.first `should be equal to` HTTP_CREATED

        val getResponse = get(postResponse.second, ProductProjection::class)
        val jsonReceived = toJson(getResponse.second)

        JSONAssert.assertEquals(
                expectedCreateProductResponse, jsonReceived,
                CustomComparator(
                        JSONCompareMode.STRICT,
                        Customization("id", RegularExpressionValueMatcher(UUID_REGEX)),
                        Customization("category.id", RegularExpressionValueMatcher(UUID_REGEX))
                )
        )
    }
}