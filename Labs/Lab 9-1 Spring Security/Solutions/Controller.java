package lab9.security;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint.";
    }

    @GetMapping("/private")
    public String privateEndpoint() {
        return "This is a private endpoint requiring authentication.";
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "This is an admin endpoint requiring ADMIN role.";
    }
}
