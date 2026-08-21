public class PostgtresClient {
    public void connect() {
        System.out.println("Postgres connected");
    }

    public void disconnect() {
        System.out.println("Postgres disconnected");
    }

    public void executeQuery(String query) {
        System.out.println("Executing query on Postgres: " + query);
    }
}
