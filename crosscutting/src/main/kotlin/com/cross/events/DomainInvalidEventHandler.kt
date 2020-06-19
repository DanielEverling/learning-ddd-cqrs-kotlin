package com.cross.events

import com.cross.infra.Log
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import io.vertx.ext.web.RoutingContext
import javax.enterprise.context.RequestScoped
import javax.enterprise.event.Observes
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

const val CONTENT_TYPE = "Content-Type"

@RequestScoped
class DomainInvalidEventHandler (private val context: RoutingContext) {

    private val log = Log(DomainInvalidEventHandler::class.java)

    fun eventHandler(@Observes domainInvalidEvent: DomainInvalidEvent) {
        val mapper = ObjectMapper()
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        val notifications = mapper.writeValueAsString(domainInvalidEvent.notifications)
        log.info("There were the following errors creating the entity $notifications")
        val response = context.response()
        response?.apply {
            statusCode = Response.Status.BAD_REQUEST.statusCode
            putHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON)
            end(notifications)
        }
    }


}