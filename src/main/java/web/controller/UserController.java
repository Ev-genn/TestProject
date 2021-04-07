package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.Role;
import web.model.User;
import web.service.UserService;
import java.security.Principal;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String getUserPage(Principal principal, Model model){
        model.addAttribute("authUser", userService.getUserByLogin(principal.getName()));
        return "user";
    }
}
