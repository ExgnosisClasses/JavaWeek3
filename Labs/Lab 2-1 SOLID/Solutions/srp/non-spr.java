package srp;

public class Runner {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        userManager.addUser("alice", "password123");
        userManager.generateReport("alice");

    }
}

class UserManager {
    public void addUser(String username, String password) {
        // Logic to add user to the database
        System.out.println("User '" + username + "' added to the database.");
    }

    public void generateReport(String username) {
        // Logic to generate a report for the user
        System.out.println("Report generated for user: " + username);
    }
}
