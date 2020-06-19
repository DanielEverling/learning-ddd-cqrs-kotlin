package com.cross.resource

import java.net.URI
import java.util.UUID

enum class Resouces(val url: String) {
    CATEGORY_RESOURCE("category"),
    CATEGORY_RESOURCE_FIND_BY_ID("${CATEGORY_RESOURCE.url}/id"),
    PRODUCT_RESOURCE("product"),
    PRODUCT_RESOURCE_FIND_BY_ID("${PRODUCT_RESOURCE.url}/id"),
}

class BaseLocation {

    companion object {
        private fun location(resource: Resouces, id: String) = URI("${resource.url}/${id}")
        fun location(resource: Resouces, id: UUID) = location(resource, id.toString())
    }
}
