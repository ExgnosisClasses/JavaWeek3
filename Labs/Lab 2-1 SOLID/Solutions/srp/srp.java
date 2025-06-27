package srp;

public class Runner {
    public static void main(String[] args) {
        UserService userService = new UserService();
        UserReportService reportService = new UserReportService();

        userService.addUser("alice", "password123");
        reportService.generateReport("alice");
    }
}


class UserReportService {

    public void generateReport(String username) {
        System.out.println("Report generated for user: " + username);
    }
}


class UserService {

    public void addUser(String username, String password) {
        System.out.println("User '" + username + "' added to the database.");
    }
}