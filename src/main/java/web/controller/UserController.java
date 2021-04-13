package web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.User;
import web.service.UserService;
import java.security.Principal;


@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "user/principal")
    public User getPrincipal(Principal principal){
        return userService.getUserByLogin(principal.getName());
    }
}
