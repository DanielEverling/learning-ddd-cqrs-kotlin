package com.learning.ddd.api

import com.learning.ddd.application.query.CategoryProjection
import com.learning.ddd.commons.*
import com.learning.ddd.commons.Gateway.post
import com.learning.ddd.commons.Gateway.get
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.Customization
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher
import org.skyscreamer.jsonassert.comparator.CustomComparator

internal class CartegorytResourceIT {

    private val companyResources = "/category"

    companion object {
        @BeforeAll
        @JvmStatic
        fun `init`() {
            waitForResource(HEALTH)
        }
    }

    @AfterEach
    fun `down`() {
        ClearDataTable.clearAllTables()
    }

    @Test
    fun `should create a category`() {
        val createCompanyRequest = ReadFile.read(companyResources, "category_post_request_ok")
        val expectedCreateProductResponse = ReadFile.read(companyResources, "category_post_response_ok")

        val postResponse = post(companyResources, createCompanyRequest)
        postResponse.first `should be equal to` HTTP_CREATED

        val getResponse = get(postResponse.second, CategoryProjection::class)
        val jsonReceived = toJson(getResponse.second)
        JSONAssert.assertEquals(
                expectedCreateProductResponse, jsonReceived,
                CustomComparator(
                        JSONCompareMode.STRICT,
                        Customization("id", RegularExpressionValueMatcher(UUID_REGEX))
                )
        )
    }

    @Test
    fun `should validate the creating of a category with empty fields`() {
        val createCompanyRequest = ReadFile.read(companyResources, "category_post_request_empty_fields")
        val expectedCreateProductResponse = ReadFile.read(companyResources, "category_post_response_empty_fields")

        val response = post(companyResources, createCompanyRequest, List::class)

        response.first `should be equal to` HTTP_BAD_REQUEST
        toJson(response.second) `should be strict` expectedCreateProductResponse
    }

    @Test
    fun `should validate the creating of a category with invalid fields`() {
        val createCompanyRequest = ReadFile.read(companyResources, "category_post_request_invalid_fields")
        val expectedCreateProductResponse = ReadFile.read(companyResources, "category_post_response_invalid_fields")

        val response = post(companyResources, createCompanyRequest, List::class)

        response.first `should be equal to` HTTP_BAD_REQUEST
        toJson(response.second) `should be strict` expectedCreateProductResponse
    }

}