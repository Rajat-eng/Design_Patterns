package Patterns.Facade.Class;

public class UserAuthentication {
    public void login(String username, String password) {
        System.out.println("User logged in");
    }
    public void logout() {
        System.out.println("User logged out");
    }
}
