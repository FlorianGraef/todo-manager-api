package eu.creaite.todorest.resources;

import eu.creaite.todorest.api.beans.Todo;
import eu.creaite.todorest.core.TodoController;
import eu.creaite.todorest.db.TodoDAO;
import eu.creaite.todorest.db.beans.MongoTodo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TodosResourceTest {

    private final TodosResource todosResource = new TodosResource();
    private final TodoDAO dao = new TodoDAO();
    private final TodoController controller = new TodoController();

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
        assertEquals(n, noTodos);
    }

    @Test
    void postTodo() {
        Todo testPostTodo = new Todo();

        testPostTodo.setName("Check-up");
        testPostTodo.setDescription("Check everything is running on the Laptop!");

        Todo returnedTodo = todosResource.postTodo(testPostTodo);
        assert(returnedTodo != null);
        assert(returnedTodo.getId() != null && ! returnedTodo.getId().isEmpty());
    }

    @Test
    void getSpecificTodo() {
        Todo testPostTodo = new Todo();
        testPostTodo.setName("Check-up");
        testPostTodo.setDescription("Check everything is running on the Laptop!");
        testPostTodo = todosResource.postTodo(testPostTodo);

        Todo testee = todosResource.getSpecificTodo(testPostTodo.getId());
        assert(testee.getId().equals(testPostTodo.getId()));
    }

    @Test
    void deleteTodo(){
        Todo testPostTodo = new Todo();
        testPostTodo.setName("Check-up");
        testPostTodo.setDescription("Check everything is running on the Laptop!");
        testPostTodo = todosResource.postTodo(testPostTodo);

        todosResource.deleteTodo(testPostTodo.getId());
        MongoTodo testeeTodo = dao.getOneTodo(testPostTodo.getId());
        assert(testeeTodo ==null);
    }

    @Test
    void replaceTodo(){
        Todo testPostTodo = new Todo();
        testPostTodo.setName("Check-up");
        testPostTodo.setDescription("Check everything is running on the Laptop!");
        testPostTodo = todosResource.postTodo(testPostTodo);

        String id = testPostTodo.getId();
        testPostTodo.setName("Changed Name");
        testPostTodo.setId(null);
        testPostTodo = todosResource.replaceTodo(id, testPostTodo);
        assert("Changed Name".equals(testPostTodo.getName()));
    }
}