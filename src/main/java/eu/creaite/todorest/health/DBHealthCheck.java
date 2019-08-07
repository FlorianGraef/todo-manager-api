package eu.creaite.todorest.health;

import com.codahale.metrics.health.HealthCheck;
import eu.creaite.todorest.db.TodoDAO;

public class DBHealthCheck  extends HealthCheck {
    public DBHealthCheck() {
    }

    @Override
    protected Result check() {
        TodoDAO dao = new TodoDAO();

        return dao.pingDB() ? Result.healthy() : Result.unhealthy("Database Ping unsuccessful. Check DB connection.");
    }


}
