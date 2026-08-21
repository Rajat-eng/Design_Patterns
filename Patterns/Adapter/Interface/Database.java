package Patterns.Adapter.Interface;

public interface Database {
    void connect();
    void disconnect();
    void executeQuery(String query);
}