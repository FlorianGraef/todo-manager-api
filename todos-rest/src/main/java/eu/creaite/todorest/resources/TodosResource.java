package eu.creaite.todorest.resources;

import com.codahale.metrics.annotation.Timed;
import eu.creaite.todorest.db.TodoDAO;
import eu.creaite.todorest.db.beans.MongoTodo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Path("/todos")
public class TodosResource {

    private final TodoDAO dao = new TodoDAO();

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public List<MongoTodo> listTodos() {
        return dao.listAllTodos();
    }

    @POST
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public MongoTodo postTodo(MongoTodo todo ) {
        todo.setId(UUID.randomUUID().toString());
        dao.insertOneTodo(todo);
        return todo;
    }

    @GET
    @Timed
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public MongoTodo getSpecificTodo(@PathParam("id") String id) {
        return dao.getOneTodo(id);
    }

    @PUT
    @Timed
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public MongoTodo replaceTodo(@PathParam("id") String id, MongoTodo todo) {
        dao.updateTodo(todo);
        return todo;
    }

    @DELETE
    @Timed
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteTodo(@PathParam("id") String id){
        dao.deleteTodo(id);
    }

}
