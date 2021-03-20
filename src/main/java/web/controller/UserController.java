package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String getUserPage(Principal principal, Model model){
        User user = userService.getUserByLogin(principal.getName());
        Set<Role> roles = user.getRoles();
        if(roles.size() == 2){
            model.addAttribute("admin", true);
        }
        model.addAttribute("user", user);
        return "userPage";
    }
}
