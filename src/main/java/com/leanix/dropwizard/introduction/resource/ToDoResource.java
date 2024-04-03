package com.leanix.dropwizard.introduction.resource;

import com.leanix.dropwizard.introduction.domain.ToDo;
import com.leanix.dropwizard.introduction.repository.ToDoRepository;

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

@Path("/todos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ToDoResource {
    private final int defaultSize;
    private ToDoRepository toDoRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ToDoResource.class);

    public ToDoResource(final int defaultSize, final ToDoRepository toDoRepository) {
        this.defaultSize = defaultSize;
        this.toDoRepository = toDoRepository;

    }

    public ToDoResource(ToDoRepository repository, int defaultSize) {
        this.defaultSize = defaultSize;
    }

    @GET
    @Path("/all")
    @UnitOfWork
    public Response getToDos() {
        List<ToDo> todos = toDoRepository.findAll();
        if (todos.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(todos).build();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Response getById(@PathParam("id") final int id) {
        ToDo todo = toDoRepository.findById(id);
        if (todo == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(todo).build();
    }

    @POST
    @Timed
    @UnitOfWork
    public Response add(ToDo toDo, @Context UriInfo uriInfo) {

        toDo.getSubtasks().forEach(subTask -> {
            toDo.addSubTask(subTask);
        });

        ToDo persistedToDo = toDoRepository.insert(toDo);
        URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(persistedToDo.getId())).build();
        return Response.created(location).entity(persistedToDo).build();
    }

    @PUT
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Response update(@PathParam("id") final long id, ToDo updatedToDo) {
        ToDo toDoToUpdate = toDoRepository.findById(id);
        if (toDoToUpdate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        toDoToUpdate.setTitle(updatedToDo.getTitle());
        toDoRepository.update(toDoToUpdate);
        return Response.ok(toDoToUpdate).build();
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response delete(@PathParam("id") final long id) {
        boolean deleted = toDoRepository.deleteById(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build(); // Not Found if the ToDo to delete doesn't exist
        }
        return Response.noContent().build(); // Successfully deleted the ToDo
    }
}
