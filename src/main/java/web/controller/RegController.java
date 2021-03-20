package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("")
public class RegController {

    private UserService userService;

    public RegController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {return "login";}

    @GetMapping(value = "/login/add")
    public String showRegistration(Model model) {
        String checkAdminRole = null;
        User user = new User();
        model.addAttribute("checkAdminRole", checkAdminRole);
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping(value = "/login/add")
    public String addUser(@ModelAttribute("checkAdminRole") String checkAdminRole, @ModelAttribute @Valid User user,
                          BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "registration";
        }
        if(userService.getUserByLogin(user.getUsername()) != null){
            model.addAttribute("loginError", true);
            return "registration";
        }
        Set<Role> roles = new HashSet<>();
        roles.add(userService.getRoleById(1L));
        if(!checkAdminRole.equals("")) {
            roles.add(userService.getRoleById(2L));
        }
        user.setRoles(roles);
        userService.addUser(user);
        return "redirect:/login";
    }
}
