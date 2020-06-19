package com.cross.events

import com.cross.commons.anyString
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.RoutingContext
import org.junit.jupiter.api.Test;
import com.nhaarman.mockitokotlin2.*

class EntityNotFountEventHandlerTest {

    @Test
    fun `should validate behavior to domain invalid handler`() {
        val context : RoutingContext = mock()
        val response : HttpServerResponse = mock()
        `whenever`(context.response()).thenReturn(response)
        val entityNotFoundEventHandler = EntityNotFoundEventHandler(context)
        entityNotFoundEventHandler.eventHandler(EntityNotFoundEvent(message = "Mock message"))
        verify(context, times(1)).response()
        verify(response, times(1)).end(anyString())
        verify(response, times(1)).putHeader(anyString(),anyString())
        verify(response).statusCode = 400
    }

}