package com.learning.ddd.api

import com.cross.extensions.created
import com.cross.extensions.mountResponseToGet
import com.cross.resource.Resouces
import com.learning.ddd.application.command.CreateCategoryCommand
import com.learning.ddd.application.command.CreateCategoryCommandHandler
import com.learning.ddd.application.query.CategoryQueryRepository
import org.jboss.resteasy.annotations.jaxrs.PathParam
import java.util.UUID
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/category")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class CategoryResource (val categoryCommandHandler: CreateCategoryCommandHandler, val categoryQueryRepository: CategoryQueryRepository) {

    @POST
    fun create(command: CreateCategoryCommand): Response {
        categoryCommandHandler.handler(command)
        return created(Resouces.CATEGORY_RESOURCE_FIND_BY_ID, command.id)
    }

    @GET
    @Path("/id/{id}")
    fun findById(@PathParam id: UUID) =
            mountResponseToGet(projection = categoryQueryRepository.findById(id), message = "Category with $id not found")

}