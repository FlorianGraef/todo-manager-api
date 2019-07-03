package eu.creaite.todorest.resources;

import eu.creaite.todorest.db.TodoDAO;
import eu.creaite.todorest.db.beans.MongoTodo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

class TodosResourceTest {

    private final TodosResource todosResource = new TodosResource();
    private final TodoDAO dao = new TodoDAO();

    @AfterEach
    void tearDown() {
        dao.deleteAllTodos();
    }

    @Test
    void listTodos() {
        int n= 5;
        IntStream.range(0, n).forEach(
                nbr -> postTodo()
        );
        int noTodos = todosResource.listTodos().size();
        assert(noTodos == n);
    }

    @Test
    void postTodo() {
        MongoTodo testPostTodo = new MongoTodo();
        testPostTodo.setName("Check-up");
        testPostTodo.setDescription("Check everything is running on the Laptop!");

        MongoTodo returnedTodo = todosResource.postTodo(testPostTodo);
        assert(returnedTodo != null);
        assert(returnedTodo.getId() != null && ! returnedTodo.getId().isEmpty());
    }

    @Test
    void getSpecificTodo() {
        MongoTodo testPostTodo = new MongoTodo();
        testPostTodo.setName("Check-up");
        testPostTodo.setDescription("Check everything is running on the Laptop!");
        testPostTodo = todosResource.postTodo(testPostTodo);

        MongoTodo testee = todosResource.getSpecificTodo(testPostTodo.getId());
        assert(testee.getId().equals(testPostTodo.getId()));
    }

    @Test
    void deleteTodo(){
        MongoTodo testPostTodo = new MongoTodo();
        testPostTodo.setName("Check-up");
        testPostTodo.setDescription("Check everything is running on the Laptop!");
        testPostTodo = todosResource.postTodo(testPostTodo);

        todosResource.deleteTodo(testPostTodo.getId());
        MongoTodo testeeTodo = todosResource.getSpecificTodo(testPostTodo.getId());
        assert(testeeTodo ==null);
    }

    @Test
    void replaceTodo(){
        MongoTodo testPostTodo = new MongoTodo();
        testPostTodo.setName("Check-up");
        testPostTodo.setDescription("Check everything is running on the Laptop!");
        testPostTodo = todosResource.postTodo(testPostTodo);

        String id = testPostTodo.getId();
        testPostTodo.setName("Changed Name");
        testPostTodo = todosResource.replaceTodo(id, testPostTodo);
        assert("Changed Name".equals(testPostTodo.getName()));
    }
}