package Patterns.Adapter.Class;

import Patterns.Adapter.Interface.Database;
import Patterns.Adapter.Class.MySQLClient;

public class MySQLAdapter implements Database {
    // adapter class that implements the Database interface and adapts the MySQLClient to work with it
    private MySQLClient mySQLDatabase;

    public MySQLAdapter(MySQLClient mySQLDatabase) {
        this.mySQLDatabase = mySQLDatabase;
    }

    @Override
    public void connect() {
        mySQLDatabase.connect();
    }

    @Override
    public void disconnect() {
        mySQLDatabase.disconnect();
    }

    @Override
    public void executeQuery(String query) {
        mySQLDatabase.executeQuery(query);
    }
}