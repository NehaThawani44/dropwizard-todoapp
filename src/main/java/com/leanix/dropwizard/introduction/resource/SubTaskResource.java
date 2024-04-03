package com.leanix.dropwizard.introduction.resource;

import com.leanix.dropwizard.introduction.domain.SubTask;
import com.leanix.dropwizard.introduction.repository.SubTaskRepository;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/subtasks")
@Produces(MediaType.APPLICATION_JSON)
public class SubTaskResource {


    private final SubTaskRepository subTaskRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(SubTaskResource.class);


    public SubTaskResource(final SubTaskRepository subTaskRepository) {

        this.subTaskRepository = subTaskRepository;
    }


    @GET
    @UnitOfWork
    public Response getSubTasks() {
        List<SubTask> subTasks = subTaskRepository.findAll();
        if (subTasks.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(subTasks).build();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Response getById(@PathParam("id") final long id) {
        SubTask subTask = subTaskRepository.findById(id);
        if(subTask == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(subTask).build();
    }

    @POST
    @Timed
    @UnitOfWork
    public Response add(SubTask subTask, @Context UriInfo uriInfo) {
        SubTask newSubTask = subTaskRepository.insert(subTask);
        URI location = uriInfo.getAbsolutePathBuilder().path(Long.toString(newSubTask.getId())).build();
        return Response.created(location).entity(newSubTask).build();
    }


 /*   @PUT
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Response update(@PathParam("id") final long id, SubTask updatedSubTask) {
        SubTask subTaskToUpdate = subTaskRepository.findById(id);
        if(subTaskToUpdate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        subTaskToUpdate.setTitle(updatedSubTask.getTitle());
        subTaskRepository.update(subTaskToUpdate);
        return Response.ok(subTaskToUpdate).build();
    }*/

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response delete(@PathParam("id") final long id) {
        boolean deleted = subTaskRepository.deleteById(id);
        if(!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }


}

