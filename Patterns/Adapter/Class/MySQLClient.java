public class MySQLClient {
    // this is simulating a MySQL client that has its own methods for connecting, disconnecting, and executing queries
    public void connect() {
        System.out.println("MySQL connected");
    }

    public void disconnect() {
        System.out.println("MySQL disconnected");
    }

    public void executeQuery(String query) {
        System.out.println("Executing query on MySQL: " + query);
    }
}
