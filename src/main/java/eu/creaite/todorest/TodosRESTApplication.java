package eu.creaite.todorest;

import eu.creaite.todorest.health.DBHealthCheck;
import eu.creaite.todorest.resources.TodosResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TodosRESTApplication extends Application<TodosRESTConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TodosRESTApplication().run(args);
    }

    @Override
    public String getName() {
        return "TodosREST";
    }

    @Override
    public void initialize(final Bootstrap<TodosRESTConfiguration> bootstrap) {
    }

    @Override
    public void run(final TodosRESTConfiguration configuration,
                    final Environment environment) {
        final TodosResource resource = new TodosResource();
        final DBHealthCheck healthCheck = new DBHealthCheck();
        environment.healthChecks().register("DB_Check", healthCheck);
        environment.jersey().register(resource);
    }

}
