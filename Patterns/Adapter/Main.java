package Patterns.Adapter;

import Patterns.Adapter.Class.PostgresAdapter;
import Patterns.Adapter.Class.PostgresClient;


public class Main {
    public static void main(String[] args) {
        PostgresClient postgresClient = new PostgresClient();
        PostgresAdapter postgresAdapter = new PostgresAdapter(postgresClient);
        postgresAdapter.connect();
        postgresAdapter.executeQuery("SELECT * FROM users");

        MySQLClient mySQLClient = new MySQLClient();
        MySQLAdapter mySQLAdapter = new MySQLAdapter(mySQLClient);
        mySQLAdapter.connect();
        mySQLAdapter.executeQuery("SELECT * FROM products");
    }
}
