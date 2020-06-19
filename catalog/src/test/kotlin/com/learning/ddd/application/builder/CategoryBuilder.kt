package com.learning.ddd.application.builder


import com.learning.ddd.application.domain.Category

internal class CategoryBuilder {

    companion object {
        fun build() : Category {
            return Category.build { name = "A category valid" }.entity()
        }
    }
}