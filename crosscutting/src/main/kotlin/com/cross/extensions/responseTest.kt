package com.cross.extensions

import com.cross.domain.Notification
import com.cross.resource.BaseLocation
import com.cross.resource.Resouces
import java.util.Optional
import java.util.UUID
import javax.ws.rs.core.Response

private fun notFound(field: String = "", message: String): Response =
        Response.status(Response.Status.NOT_FOUND.statusCode).
        entity(Notification(field= field, notification = message))
                .build()

private fun ok(response: Any): Response = Response.ok(response).build()

fun <T : Any> mountResponseToGet(projection: Optional<T>, message: String): Response {
        return when(projection.isPresent) {
                true -> ok(projection.get())
                else -> notFound(field = "id", message = message)
        }
}

fun created(resouces: Resouces, id: UUID) : Response =
        Response.created(BaseLocation.location(resouces, id)).build()