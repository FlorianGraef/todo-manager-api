package eu.creaite.todorest.resources;

import com.codahale.metrics.annotation.Timed;
import eu.creaite.todorest.api.beans.Todo;
import eu.creaite.todorest.core.TodoController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/todos")
public class TodosResource {

    private final TodoController controller = new TodoController();

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public List<Todo> listTodos() {
        return controller.listAllTodos();
    }

    @POST
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Todo postTodo(Todo todo ) {
        return controller.postTodo(todo);
    }

    @GET
    @Timed
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Todo getSpecificTodo(@PathParam("id") String id) {
        return controller.getOneTodo(id);
    }

    @PUT
    @Timed
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Todo replaceTodo(@PathParam("id") String id, Todo todo) {
        controller.updateTodo(id, todo);
        return todo;
    }

    @DELETE
    @Timed
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteTodo(@PathParam("id") String id){
        controller.deleteTodo(id);
    }

}
