package com.learning.ddd.commons

import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

fun String.shouldBeStrict(actualResponse: String) {
    JSONAssert.assertEquals(this, actualResponse, JSONCompareMode.STRICT)
}

infix fun String?.`should be strict`(actualResponse: String?) {
    JSONAssert.assertEquals(this, actualResponse, JSONCompareMode.STRICT)
}
