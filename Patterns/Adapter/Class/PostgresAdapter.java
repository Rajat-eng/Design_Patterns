package Patterns.Adapter.Class;

import Patterns.Adapter.Interface.Database;
import Patterns.Adapter.Class.PostgresClient;

public class PostgresAdapter implements Database {
    private PostgresClient postgresDatabase;

    public PostgresAdapter(PostgresClient postgresDatabase) {
        this.postgresDatabase = postgresDatabase;
    }

    @Override
    public void connect() {
        postgresDatabase.connect();
    }

    @Override
    public void disconnect() {
        postgresDatabase.disconnect();
    }

    @Override
    public void executeQuery(String query) {
        postgresDatabase.executeQuery(query);
    }
    
}
