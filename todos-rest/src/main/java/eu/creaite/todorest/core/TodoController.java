package eu.creaite.todorest.core;

import eu.creaite.todorest.api.beans.Todo;
import eu.creaite.todorest.db.TodoDAO;
import eu.creaite.todorest.db.beans.MongoTodo;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TodoController {
    private final TodoDAO dao = new TodoDAO();

    public Todo postTodo(Todo todo) {
        // check new Todo doesn't have an id
        if (todo.getId() != null) {
            System.out.println("Provided Todo has got an ID. A new Todo will be auto assigned an id.");
        }
        MongoTodo dbTodo = toMongoTodo(todo);

        dbTodo.setId(String.valueOf(UUID.randomUUID()));
        dao.insertOneTodo(dbTodo);
        todo.setId(dbTodo.getId());

        return todo;
    }

    private MongoTodo toMongoTodo(Todo todo){
        MongoTodo dbTodo = new MongoTodo();
        if (todo.getId() != null){
            dbTodo.setId(todo.getId());
        }
        if (todo.getName() != null){
            dbTodo.setName(todo.getName());
        }
        if (todo.getDescription() != null){
            dbTodo.setDescription(todo.getDescription());
        }

        if (todo.getTasks() != null) {
            dbTodo.setTasks(todo.getTasks().stream()
                    .map(task -> toMongoTodo(task))
                    .collect(Collectors.toList())
            );
        }
        return dbTodo;
    }

    private Todo toTodo(MongoTodo mongoTodo){
        Todo targetTodo = new Todo();
        if (mongoTodo.getId() != null ){
            targetTodo.setId(mongoTodo.getId());
        }
        if (mongoTodo.getName() != null){
            targetTodo.setName(mongoTodo.getName());
        }
        if (mongoTodo.getDescription() != null){
            targetTodo.setDescription(mongoTodo.getDescription());
        }
        if (mongoTodo.getTasks() != null && mongoTodo.getTasks().isEmpty() == false){
            targetTodo.setTasks(mongoTodo.getTasks().stream()
                    .map(task -> toTodo(task))
                    .collect(Collectors.toList())
            );
        }
        return targetTodo;
    }

    public List<Todo> listAllTodos() {
        return dao.listAllTodos().stream()
                .map(todo -> toTodo(todo))
                .collect(Collectors.toList());
    }

    public Todo getOneTodo(String id) {
        MongoTodo todo = dao.getOneTodo(id);
        if (todo == null){
            throw new WebApplicationException(String.format("The Todo with id '%s' was not found in the Database.", id), Response.Status.NOT_FOUND);
        }
        return toTodo(todo);
    }

    public void deleteTodo(String id) {
        dao.deleteTodo(id);
    }

    public Todo updateTodo(String id, Todo todo) {
        if(todo != null ){
            if (todo.getId() != null){
                throw new WebApplicationException(String.format("The provided Todo already contains an id. Please use a Todo without id", id), Response.Status.BAD_REQUEST);
            }
            todo.setId(id);
            dao.updateTodo(toMongoTodo(todo));
            return todo;
        }
        throw new WebApplicationException(String.format("The Todo with id '%s' was not found in the Database. Check the id is correct or Consider the insertion of this todo", id), Response.Status.NOT_FOUND);
    }
}
