package com.cross.events

import com.cross.infra.Log
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import io.vertx.ext.web.RoutingContext
import javax.enterprise.context.RequestScoped
import javax.enterprise.event.Observes
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@RequestScoped
class EntityNotFoundEventHandler (private val context: RoutingContext) {

    private val log = Log(EntityNotFoundEventHandler::class.java)

    fun eventHandler(@Observes entityNotFoundEvent: EntityNotFoundEvent) {
        val mapper = ObjectMapper()
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        val notifications = mapper.writeValueAsString(entityNotFoundEvent)
        log.info("Not found the entity $notifications")
        val response = context.response()
        response?.apply {
            statusCode = Response.Status.BAD_REQUEST.statusCode
            putHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON)
            end(notifications)
        }
    }

}