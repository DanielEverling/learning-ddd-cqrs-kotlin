package com.cross.extensions

import com.cross.domain.Notification
import com.cross.resource.Resouces
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import java.util.Optional
import java.util.UUID
import javax.ws.rs.core.Response

internal class ResponseTest {

        @Test
        fun `should return response 200 with body`() {
                val response = mountResponseToGet(Optional.of("a value"), "")
                Response.Status.OK.statusCode `should be equal to` response.status
                "a value" `should be equal to` response.entity.toString()
        }

        @Test
        fun `should return response 404 with body`() {
                val response = mountResponseToGet(Optional.empty(), "Message with error")
                Response.Status.NOT_FOUND.statusCode `should be equal to` response.status
                Notification(field = "id", notification = "Message with error") `should be equal to` response.entity
        }

        @Test
        fun `should return response 201 with body`() {
                val id = UUID.randomUUID()
                val category = Resouces.CATEGORY_RESOURCE
                val response = created(category, id)
                Response.Status.CREATED.statusCode `should be equal to` response.status
                "${category.url}/${id}" `should be equal to` response.getHeaderString("location")
                null `should be equal to` response.entity
        }

}