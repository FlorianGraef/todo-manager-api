# TodosREST

Todos-rest is a RESTful API built using the Dropwizard Java framework to manage storing and retrieving of Todo items in/from a MongoDB data store.

## Functionality

The aim was to create a simple API that manages a list of TODO items of the following structure:

<pre>
{
  id
  name
  description
  tasks: [todo-item]
}
</pre>

Note: the todo-item represents a new todo item so that nesting of Todos is possible.

The API implements the folowing endpoints:

| http method | Endpoint | Behaviour |
| --- | --- | --- |
| GET | /todos | Returns a list of all TODOs |
| POST | /todos | Expects a TODO (without id) and returns a TODO with ID |
| GET | /todos/{id} | Returns a TODO | 
| PUT | /todos/{id} | Overwrites an existing TODO |
| DELETE | /todos/{id} | Deletes a TODO |

Requirements
---

To run the application a MongoDB available on localhost is required with a database name of "todos-db" and a collection "todos" 
(This could be improved by making the names configurable or creating the database and collection on initialisation of the container.)

1. A mongodb instance can easily be installed and started through docker:
`docker run -d -p 27017-27019:27017-27019 --name mongodb mongo`

2. Enter the container:
`docker exec -it mongodb bash`

3. Start the mongoDB console:
`mongo`

4. create the db:
`use todos-db`

5. create the collection:
`db.createCollection("todos")`

How to start the TodosREST application
---

When the mongoDb instance is up and running there are two options to start the API. To build and start it locally with maven: 

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/todos-rest-1.0.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8083/healthcheck`

The provided dockerfile allows containerization of the built application:
`docker run --rm  -d --network host --name todo-rest-api todo-api`

Now the API should be available under `http://localhost:8082/todos`.

A initialised service will not contain any Todos yet so we can post one to it:

`curl -H "Content-Type: application/json" --data '{ "name": "TODO REST service", "description": "Write a REST API using Dropwizard implementing a couple of methods to store and retrieve TODO lists.", "tasks": [{"name":"test subtask"}]}' -X POST http://localhost:8082/todos`

The API will in response return the stored TODO item including the id.
We can subsequently run `http://localhost:8082/todos/` or `http://localhost:8082/todos/{id}` using the previously returned id to retrieve just this particular TODO item.
Similarly the PUT and DELETE methods can be used to replace or delete a Todo item with a given id.


Health Check
---

To see your applications health enter url `http://localhost:8083/healthcheck`

Potential improvements:

1. Dockerfile/docker-compose file to configure MongoDB user and password.
2. Use docker-compose to build and start both mongoDb and the todos-rest-api in an orchestrated way (requires pushing the API into a container registry though).
3. Further decouple the controller from the DAO by creating a DAO-interface which gets implemented by the MongoDAO.
4. Add logging meyond the Dropwizard standard logging.
5. Explore use of dropwizards metrics features for montioring. 
