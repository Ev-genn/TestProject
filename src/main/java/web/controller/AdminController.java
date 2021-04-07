package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.SecurityService;
import web.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private SecurityService securityService;

    public AdminController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping(value = "")
    public String listUsers(Model model, Principal principal){
        User newUser = new User();
        model.addAttribute("authUser", userService.getUserByLogin(principal.getName()));
        model.addAttribute("allRole", securityService.getListRoles());
        model.addAttribute("newUser", newUser);
        model.addAttribute("listUsers", userService.getListUser());
        return "admin";
    }

    @PostMapping(value = "/add")
    public String addUser(@ModelAttribute User user){

        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = {"edit"})
    public String updateUser(@ModelAttribute("user") User user) {
        boolean byCoding =  false;
        User oldUser = userService.getUserById(user.getId());
        if(!oldUser.getPassword().equals(user.getPassword())){
            byCoding = true;
        }
        userService.updateUser(user, byCoding);
        return "redirect:/admin";
    }

    @PostMapping(value = {"/{id}/delete"})
    public String deleteUser(@PathVariable long id) {
        userService.remove(id);
        return "redirect:/admin";
    }
}