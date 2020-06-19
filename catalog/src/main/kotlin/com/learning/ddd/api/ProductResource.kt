package com.learning.ddd.api

import com.cross.extensions.created
import com.cross.extensions.mountResponseToGet
import com.cross.resource.Resouces.PRODUCT_RESOURCE_FIND_BY_ID
import com.learning.ddd.application.command.CreateProductCommand
import com.learning.ddd.application.command.CreateProductCommandHandler
import com.learning.ddd.application.query.ProductQueryRepository
import org.jboss.resteasy.annotations.jaxrs.PathParam
import java.util.UUID
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ProductResource (val productCommandHandler: CreateProductCommandHandler, val productQueryRepository: ProductQueryRepository) {

    @POST
    fun create(command: CreateProductCommand): Response {
        productCommandHandler.handler(command)
        return created(PRODUCT_RESOURCE_FIND_BY_ID, command.id)
    }

    @GET
    @Path("/id/{id}")
    fun findById(@PathParam id: UUID) =
            mountResponseToGet(projection = productQueryRepository.findById(id), message = "Product with $id not found")

}