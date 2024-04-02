package com.baeldung.dropwizard.introduction.resource;

import com.baeldung.dropwizard.introduction.domain.SubTask;
import com.baeldung.dropwizard.introduction.domain.ToDo;
import com.baeldung.dropwizard.introduction.repository.SubTaskRepository;
import com.baeldung.dropwizard.introduction.repository.ToDoRepository;
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



    private  SubTaskRepository subTaskRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(SubTaskResource.class);


    public SubTaskResource( final SubTaskRepository subTaskRepository) {

        this.subTaskRepository = subTaskRepository;
    }




    @GET
    @UnitOfWork
    public List<SubTask> getSubTasks() {
        return subTaskRepository.findAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public SubTask getById(@PathParam("id") final int id) {
        return subTaskRepository.findById(id);

    }

    @POST
    @Timed
    @UnitOfWork
    public Response add(SubTask subTask, @Context UriInfo uriInfo) {
        SubTask newSubTask = subTaskRepository.insert(subTask);
        URI location = uriInfo.getAbsolutePathBuilder().path(Long.toString(newSubTask.getId())).build();
        return Response.created(location).entity(newSubTask).build();
    }

    @PUT
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public SubTask update(@PathParam("id") final long id, SubTask updatedSubTask) {
        SubTask subTaskToUpdate = subTaskRepository.findById(id);
        if (subTaskToUpdate != null) {
            // Assuming ToDo has setters for fields that can be updated
            subTaskToUpdate.setTitle(updatedSubTask.getTitle());
            // Add other fields that can be updated here
            subTaskRepository.update(subTaskToUpdate);
        }
        return subTaskToUpdate;

    }



    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public void delete(@PathParam("id") final long id) {
        subTaskRepository.deleteById(id);
        }
    }

