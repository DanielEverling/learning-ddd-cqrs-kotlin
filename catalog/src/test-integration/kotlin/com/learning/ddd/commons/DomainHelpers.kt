package com.learning.ddd.commons

import com.cross.resource.Resouces
import com.totest.commons.Gateway
import com.totest.commons.HTTP_CREATED
import com.totest.commons.ReadFile
import org.amshove.kluent.`should be equal to`

fun getId(location: String) =  location.split("/")[5]

object DomainHelpers {

    fun createCategory(): String {
        val path = "/${Resouces.CATEGORY_RESOURCE.url}"
        val createCompanyRequest = ReadFile.read(path, "category_post_request_ok")
        val createCompanyResponse = Gateway.post(path, createCompanyRequest)
        createCompanyResponse.first `should be equal to` HTTP_CREATED
        return getId(createCompanyResponse.second)
    }

}
