package eu.creaite.todorest.db.beans;

import java.util.List;

public final class MongoTodo {

    private String id;
    private String name;
    private String description;
    private List<MongoTodo> tasks;

    public MongoTodo() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<MongoTodo> getTasks() {
        return tasks;
    }

    public void setTasks(List<MongoTodo> tasks) {
        this.tasks = tasks;
    }
}
