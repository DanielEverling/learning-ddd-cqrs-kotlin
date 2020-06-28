package com.learning.ddd.api

import com.cross.extensions.created
import com.cross.extensions.mountResponseToGet
import com.cross.resource.Resouces
import com.learning.ddd.application.command.CreateOrderCommand
import com.learning.ddd.application.command.CreateOrderCommandHandler
import com.learning.ddd.application.query.OrderQueryRepository
import org.jboss.resteasy.annotations.jaxrs.PathParam
import java.util.UUID
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class OrderResource (private val orderCommandHandler: CreateOrderCommandHandler, private val orderQueryRepository: OrderQueryRepository) {

    @POST
    fun create(command: CreateOrderCommand): Response {
        orderCommandHandler.handler(command)
        return created(Resouces.ORDER_RESOURCE_FIND_BY_ID, command.id)
    }

    @GET
    @Path("/id/{id}")
    fun findById(@PathParam id: UUID) =
            mountResponseToGet(projection = orderQueryRepository.findById(id), message = "Order with $id not found")

}