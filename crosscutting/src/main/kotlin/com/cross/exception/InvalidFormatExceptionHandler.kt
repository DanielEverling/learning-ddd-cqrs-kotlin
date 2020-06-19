package com.cross.exception

import com.cross.domain.Notification
import com.cross.infra.Log
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class InvalidFormatExceptionHandler : ExceptionMapper<InvalidFormatException> {

    private val log = Log(InvalidFormatExceptionHandler::class.java)

    override fun toResponse(exception: InvalidFormatException?): Response {
        log.error("Received exception from request with error $exception")

        val notifications = ArrayList<Notification>()
        exception?.let {
            exception.path.forEach {
                notifications.add(Notification(field = it.fieldName, notification = "Format is not valid."))
            }
        }
        return Response.status(Status.BAD_REQUEST).entity(notifications).build();
    }
}