package com.baeldung.dropwizard.introduction.resource;

import com.baeldung.dropwizard.introduction.domain.ToDo;
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
import java.util.Optional;

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
    public List<ToDo> getToDos() {
        return toDoRepository.findAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public ToDo getById(@PathParam("id") final int id) {
        return toDoRepository
          .findById(id);
          
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
    public ToDo update(@PathParam("id") final long id, ToDo updatedToDo) {
        ToDo toDoToUpdate = toDoRepository.findById(id);
        if (toDoToUpdate != null) {
            toDoToUpdate.setTitle(updatedToDo.getTitle());
            toDoRepository.update(toDoToUpdate);
        }
        return toDoToUpdate;
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public void delete(@PathParam("id") final long id) {
        toDoRepository.deleteById(id);
    }
}
