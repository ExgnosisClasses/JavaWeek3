package lab7;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;


@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private MessageService service;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Spring Boot app says: " + service.getMessage());
    }
}

@Component
class MessageService {
    public String getMessage() {
        return "Hello from Spring Boot";
    }
}

