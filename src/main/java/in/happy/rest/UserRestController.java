package in.happy.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    // Only admins can access this endpoint
    @GetMapping(value = "/admin")
    public String admin() {
        return "<h3>Welcome Admin</h3>";  // Fixed closing tag for <h3>
    }

    // Both admins and users can access this endpoint
    @GetMapping(value = "/user")
    public String user() {
        return "<h3>Welcome User</h3>";  // Fixed closing tag for <h3>
    }

    // Anyone can access this endpoint
    @GetMapping(value = "/welcome")
    public String welcome() {
        return "<h3>Welcome</h3>";  // Fixed closing tag for <h3>
    }
}
