package eu.creaite.todorest.db;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import eu.creaite.todorest.db.beans.MongoTodo;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class TodoDAO {
    private final CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    private final MongoClientSettings settings = MongoClientSettings.builder()
            .codecRegistry(pojoCodecRegistry)
            .build();
    private final com.mongodb.client.MongoClient mongoClient = MongoClients.create(settings);
    private final MongoDatabase mDB = mongoClient.getDatabase("todos-db");
    private final MongoCollection<MongoTodo> mongoTodos = mDB.getCollection("todos", MongoTodo.class);

    public boolean pingDB(){
        try {
            Document doc = mDB.runCommand(new BasicDBObject("ping", "1"));
            if (doc.get("ok").equals(1.0)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void insertOneTodo(MongoTodo mongoTodo){
        this.mongoTodos.insertOne(mongoTodo);
    }

    public void deleteAllTodos() {
        this.mongoTodos.deleteMany(new BasicDBObject());
    }

    public List<MongoTodo> listAllTodos() {
        List<MongoTodo> mongoTodos = new ArrayList<MongoTodo>();
        FindIterable<MongoTodo> iterableMongoTodos = this.mongoTodos.find();

        for (MongoTodo todo : iterableMongoTodos){
            mongoTodos.add(todo);
        }
        return mongoTodos;
    }

    public MongoTodo getOneTodo(String id){
        return this.mongoTodos.find(eq("_id", id)).first();
    }

    public void deleteTodo(String id){
        this.mongoTodos.deleteOne(eq("_id", id));
    }

    public void updateTodo(MongoTodo todo){
        this.mongoTodos.replaceOne(eq("_id", todo.getId()), todo);
    }

}
