package com.learning.ddd.api

import com.learning.ddd.application.query.OrderProjection
import com.totest.commons.ClearDataTable
import com.totest.commons.Gateway.get
import com.totest.commons.Gateway.post
import com.totest.commons.HEALTH
import com.totest.commons.HTTP_CREATED
import com.totest.commons.HTTP_OK
import com.totest.commons.ReadFile.Companion.read
import com.totest.commons.UUID_REGEX
import com.totest.commons.toJson
import com.totest.commons.waitForResource
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.Customization
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher
import org.skyscreamer.jsonassert.comparator.CustomComparator

internal class OrderResourceIT {

    private val orderResources = "/order"

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
    fun `should create a order` () {
        val createOrderRequest = read(orderResources, "order_post_request_ok")
        val createOrderResponseExpected = read(orderResources, "order_get_request_ok")

        val postResponse = post(orderResources, createOrderRequest)
        postResponse.first `should be equal to` HTTP_CREATED

        val getResponse = get(postResponse.second, OrderProjection::class)
        getResponse.first `should be equal to` HTTP_OK
        JSONAssert.assertEquals(
                createOrderResponseExpected, toJson(getResponse.second),
                CustomComparator(
                        JSONCompareMode.STRICT,
                        Customization("id", RegularExpressionValueMatcher(UUID_REGEX)),
                        Customization("items[0].id", RegularExpressionValueMatcher(UUID_REGEX)),
                        Customization("items[1].id", RegularExpressionValueMatcher(UUID_REGEX)),
                        Customization("items[2].id", RegularExpressionValueMatcher(UUID_REGEX)),
                        Customization("items[3].id", RegularExpressionValueMatcher(UUID_REGEX)),
                        Customization("items[4].id", RegularExpressionValueMatcher(UUID_REGEX))
                )
        )
    }
}