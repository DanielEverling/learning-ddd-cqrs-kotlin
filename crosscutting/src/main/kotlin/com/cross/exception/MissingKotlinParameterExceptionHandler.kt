package com.cross.exception

import com.cross.domain.Notification
import com.cross.infra.Log
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class MissingKotlinParameterExceptionHandler : ExceptionMapper<MissingKotlinParameterException> {

    private val log = Log(MissingKotlinParameterExceptionHandler::class.java)

    override fun toResponse(exception: MissingKotlinParameterException?): Response {
        log.error("Received exception from request with error $exception")

        val notifications = ArrayList<Notification>()
        exception?.let {
            exception.path.forEach {
                notifications.add(Notification(field = it.fieldName, notification = "Field is required."))
            }
        }
        return Response.status(Status.BAD_REQUEST).entity(notifications).build();
    }
}