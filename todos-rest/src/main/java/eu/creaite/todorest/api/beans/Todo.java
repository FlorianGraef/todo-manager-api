package eu.creaite.todorest.api.beans;


import java.util.List;

public class Todo {
    private String id;
    private String name;
    private String description;
    private List<Todo> tasks;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Todo> getTasks() {
        return tasks;
    }

    public void setTasks(List<Todo> tasks) {
        this.tasks = tasks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
